import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {SERVER_API_URL} from '../../app.constants';
import {Task} from '../../../model/task/task.model';
import {Observable} from 'rxjs';
import moment from 'moment';
import {GlobalProvider} from '../global-provider/global-provider';

@Injectable()
export class SyncService {
  public tasks: Task[];
  constructor(public http: HttpClient, public globalProvider: GlobalProvider) {}

  public syncWithServer(): Observable<Task[]> {
    let headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: '*/*',
      Authorization: 'Bearer ' + this.globalProvider.getToken(),
    };

    let body = new URLSearchParams();
    body.set('sincronizado', moment().format('YYYY-MM-DD-HH:mm:ss'));
    body.set('veritime', this.globalProvider.getVersion().toString());

    return this.http.post<Task[]>(SERVER_API_URL + '/sincronizacao', body.toString(), {headers: headers});
  }
}
