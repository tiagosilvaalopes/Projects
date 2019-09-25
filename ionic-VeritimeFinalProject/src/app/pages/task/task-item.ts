//CLIENTE PARA QUEM É
//DISTANCE
//PRIORIDADE
//HORAS
import {Task} from '../../../model/task/task.model';
import {NavParams, NavController, ToastController} from 'ionic-angular';
import {TranslateService} from '@ngx-translate/core';
import {Component} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {SERVER_API_URL} from '../../app.constants';
import {LocationService} from '../../providers/location-service/location-service';
import {GlobalProvider} from '../../providers/global-provider/global-provider';
import {throwError} from 'rxjs';
import moment from 'moment';
import {TaskServiceProvider} from '../../providers/task-service/task-service';
import {TaskType} from '../../../model/shared/task-type';
import {LaunchNavigator} from '@ionic-native/launch-navigator/ngx';
import {ModalController} from 'ionic-angular';
import {CheckoutModalPage} from '../checkout-modal/checkout-modal';

@Component({
  selector: 'page-task-item',
  templateUrl: 'task-item.html',
})
export class TaskItem {
  public task: Task = new Task();
  public receivedTaskID: number;
  public currentLocation: number[] = [];
  public operationOpt: string = '';
  public distance: string;
  public data_hora_agendada_format;

  constructor(
    public navParams: NavParams,
    public navCtrl: NavController,
    public modalCtrl: ModalController,
    public translateService: TranslateService,
    public http: HttpClient,
    public locationService: LocationService,
    public globalProvider: GlobalProvider,
    public taskService: TaskServiceProvider,
    public launchNavigator: LaunchNavigator,
    public toastCtrl: ToastController
  ) {}

  ionViewWillEnter() {
    this.translateService.setDefaultLang('pt');
    this.task = this.navParams.data;
    if (this.task.descricao == null || this.task.descricao == '') {
      this.task.descricao = 'NENHUM_DETALHE_INCLUIDO';
    }
    this.data_hora_agendada_format = moment(this.task.data_hora_agendada).format('DD/MM hh:mm');
  }

  /*public callNumber(numberToCall: string) {
    this.task.call(numberToCall);
  }*/

  public drive(): void {
    this.launchNavigator
      .navigate([parseFloat(this.task.latitude), parseFloat(this.task.longitude)])
      .then(success => console.log('Launched navigator'), error => console.log('Error launching navigator', error));
  }

  public isValidCheckinStatus(): boolean {
    if (this.task.status_atendimento == 'em_espera' || this.task.status_atendimento == 'em_atraso') {
      return true;
    } else {
      return false;
    }
  }

  public isValidCheckoutStatus(): boolean {
    if (this.task.status_atendimento == 'em_andamento') {
      return true;
    } else {
      return false;
    }
  }

  private handleCoordsCheckin(resp): void {
    this.currentLocation.push(resp.coords.latitude);
    this.currentLocation.push(resp.coords.longitude);
    this.getCheckin();
  }

  public getDistance(): string {
    return this.task.distance;
  }

  private navigatorGeolocationErrorHandler(error: Error) {
    throwError(error);
  }

  public checkin(): void {
    if (navigator && navigator.geolocation) {
      this.locationService.getNavigatorCurrentLocation(
        this.handleCoordsCheckin.bind(this),
        this.navigatorGeolocationErrorHandler.bind(this)
      );
    } else {
      this.locationService
        .getDeviceCurrentLocation()
        .then(resp => {
          this.handleCoordsCheckin(resp);
        })
        .catch(err => throwError(err));
    }
  }

  private getCheckin(): void {
    let paramsArray = [
      {
        acao: 'checkin',
        params: {
          idusuario_atendimento: this.task.idusuario_atendimento.toString(),
          presencial: this.task.presencial,
          latitude: this.currentLocation[0],
          longitude: this.currentLocation[1],
          data_hora: moment().format('YYYY-MM-DD HH:mm:ss'),
        },
      },
    ];

    let headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: '*, application/json, text/plain',
      Authorization: 'Bearer ' + this.globalProvider.getToken(),
    };

    let body = new URLSearchParams();
    body.set('fila', JSON.stringify(paramsArray));
    this.http.post(SERVER_API_URL + '/fila?', body.toString(), {headers: headers}).subscribe(res => {
      console.log(res);
      this.globalProvider.checkInTask(
        this.task.idatendimento,
        moment().format('YYYY-MM-DD HH:mm:ss'),
        this.task.presencial
      );

      this.successCheckinToast();
    });
  }

  public checkout(): void {
    const checkoutModal = this.modalCtrl.create(CheckoutModalPage, this.task);
    checkoutModal.present();
  }

  public successCheckinToast(): void {
    let message: string;
    this.translateService.get('CHECKIN_SUCCESS').subscribe(res => (message = res));
    let toast = this.toastCtrl.create({
      message: message,
      duration: 3000,
      position: 'bottom',
    });

    toast.present();
  }
}
