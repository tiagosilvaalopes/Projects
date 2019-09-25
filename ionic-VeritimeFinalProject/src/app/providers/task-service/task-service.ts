import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {SERVER_API_URL} from '../../app.constants';
import {Task} from '../../../model/task/task.model';
import {TaskType} from '../../../model/shared/task-type';
import {GlobalProvider} from '../../providers/global-provider/global-provider';
import {SyncService} from '../sync-sevice/sync-service';
import moment from 'moment';
import {BooleanOperator} from '../../../model/shared/boolean-operator';
@Injectable()
export class TaskServiceProvider {
  public tasks: Task[] = [];
  constructor(public http: HttpClient, public globalProvider: GlobalProvider, public syncService: SyncService) {
    this.tasks = globalProvider.getTasks();
  }

  public getOnGoing(): Task[] {
    return this.tasks.filter(task => task.status == TaskType.ON_GOING);
  }
  public getWaiting(): Task[] {
    return this.tasks.filter(task => task.status == TaskType.WAITING);
  }
  public getDue(): Task[] {
    return this.tasks.filter(task => task.status == TaskType.DUE);
  }
  public getDone(): Task[] {
    return this.tasks.filter(task => task.status == TaskType.DONE);
  }
}
