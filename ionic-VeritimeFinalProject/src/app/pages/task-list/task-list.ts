import {Component} from '@angular/core';
import {NavController, NavParams} from 'ionic-angular';
import {TranslateService} from '@ngx-translate/core';
import {Task} from '../../../model/task/task.model';
import {TaskItem} from '../task/task-item';
import {TaskServiceProvider} from '../../providers/task-service/task-service';
import {SyncService} from '../../providers/sync-sevice/sync-service';
import {GlobalProvider} from '../../providers/global-provider/global-provider';
import {TaskType} from '../../../model/shared/task-type';
import {LocationService} from '../../providers/location-service/location-service';
import {throwError} from 'rxjs';

import moment from 'moment';
import {Moment} from 'moment';

@Component({
  selector: 'page-task-list',
  templateUrl: 'task-list.html',
})
export class TaskListPage {
  public tasks: Task[] = [];
  public taskToSend: Task;
  public taskProvider: TaskServiceProvider;
  public data_hora_agendada_format;

  private taskType: string;
  public currentLocation: number[] = [];
  constructor(
    public navParams: NavParams,
    public navCtrl: NavController,
    public translateService: TranslateService,
    public syncService: SyncService,
    public globalProvider: GlobalProvider,
    public locationService: LocationService
  ) {}

  ionViewWillEnter() {
    this.translateService.setDefaultLang('pt');
    this.taskType = this.navParams.get('taskType');
    this.fillList();
    this.distanceCtrl();
    this.formatDate();

    if (this.tasks === undefined || this.tasks.length == 0) {
      //TODO
      //FAZER TOAST
    }
  }

  private fillList(): void {
    this.tasks = this.globalProvider.getTasks().filter(task => task.status_atendimento == this.taskType);
  }

  public openTask(taskID: string): void {
    this.taskToSend = this.tasks.find(t => t.idatendimento.toString() == taskID);

    this.navCtrl.push(TaskItem, this.taskToSend);
  }
  private navigatorGeolocationErrorHandler(error: Error) {
    throwError(error);
  }

  public formatDate(): void {
    this.tasks.forEach(task => {
      task.data_hora_formatada = moment(task.data_hora_agendada).format('DD/MM hh:mm');
      this.globalProvider.updateTask(task);
    });
  }
  public setDistance(): void {
    this.tasks.forEach(task => {
      task.distance = this.locationService
        .calcDistance(
          [this.currentLocation[0], this.currentLocation[1]],
          [parseFloat(task.latitude), parseFloat(task.longitude)]
        )
        .toFixed(2);
      this.globalProvider.updateTask(task);
    });
  }

  private handleCoordsDistance(resp): void {
    this.currentLocation.push(resp.coords.latitude);
    this.currentLocation.push(resp.coords.longitude);
    this.setDistance();
  }

  private distanceCtrl(): void {
    if (navigator && navigator.geolocation) {
      this.locationService.getNavigatorCurrentLocation(
        this.handleCoordsDistance.bind(this),
        this.navigatorGeolocationErrorHandler.bind(this)
      );
    } else {
      this.locationService
        .getDeviceCurrentLocation()
        .then(resp => {
          this.handleCoordsDistance(resp);
        })
        .catch(err => throwError(err));
    }
  }
}
