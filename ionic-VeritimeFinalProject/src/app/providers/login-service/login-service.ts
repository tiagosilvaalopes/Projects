import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {SERVER_API_URL} from '../../app.constants';
import {IUser} from '../../../model/user/user.model';

@Injectable()
export class LoginService {
  constructor(private http: HttpClient) {}

  public login(email: string, senha: string): Observable<IUser> {
    let headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: '*/*',
    };

    let body = new URLSearchParams();
    body.set('login', email);
    body.set('senha', senha);
    body.set('veritime', '1');
    body.set(
      'token_push',
      'tokenTeste'
      //'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZGVtcHJlc2EiOiIxIiwiX2lzcyI6InZlcml0aW1lLmNvbS5iciIsIl9yYW5kb20iOiIzODk2YmIyOWM2ZGMxMmVkNDZmN2MxNTA4NjJjMWZkNCJ9.7CCFnKhk0-MHbWT5lZIDsjGAeiQz2OdxZ0cYFLAb538'
    );

    return this.http.post(SERVER_API_URL + '/login', body.toString(), {headers: headers});
  }
}
