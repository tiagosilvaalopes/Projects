import {Injectable} from '@angular/core';
import {User} from '../../../model/user/user.model';
import {Task} from '../../../model/task/task.model';
import {BooleanOperator} from '../../../model/shared/boolean-operator';
import {TaskType} from '../../../model/shared/task-type';
import moment from 'moment';

@Injectable()
export class GlobalProvider {
  public user: User;
  public tokenPush: string;
  public tasks: Task[] = [];
  public VERSION: number = 283;

  constructor() {}

  public getVersion(): number {
    return this.VERSION;
  }

  public setUser(user: User): void {
    this.user = user;
  }

  public getUser(): User {
    return this.user;
  }

  public setToken(token: string): void {
    this.tokenPush = token;
  }

  public getToken(): string {
    return this.tokenPush;
  }

  public setTasks(tasks: Task[]) {
    this.tasks = tasks;
  }

  public getTasks(): Task[] {
    return this.tasks;
  }

  public updateTask(receivedTask: Task): void {
    let index = this.tasks.indexOf(this.tasks.find(task => task.idatendimento == receivedTask.idatendimento));
    this.tasks[index] = receivedTask;
  }

  public checkInTask(taskID: number, hora_checkin: string, presencial: BooleanOperator): void {
    var task = this.tasks.find(task => task.idatendimento == taskID);
    task.status_atendimento = TaskType.ON_GOING;
    task.status = TaskType.ON_GOING;
    task.data_hora_checkin = moment(hora_checkin, 'YYYY-MM-DD HH:mm:ss');
    task.presencial = presencial;
    this.updateTask(task);
  }
  public checkOutTask(taskID: number, hora_checkOut: string, status: TaskType): void {
    var task = this.tasks.find(task => task.idatendimento == taskID);
    task.status_atendimento = TaskType.DONE;
    task.status = TaskType.DONE;
    task.data_hora_checkin = moment(hora_checkOut, 'YYYY-MM-DD HH:mm:ss');
    this.updateTask(task);
  }
}
