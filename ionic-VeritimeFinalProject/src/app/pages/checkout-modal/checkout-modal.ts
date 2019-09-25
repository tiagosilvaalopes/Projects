import {Component} from '@angular/core';
import {IonicPage, NavController, NavParams, ViewController, ToastController} from 'ionic-angular';
import {LocationService} from '../../providers/location-service/location-service';
import {throwError} from 'rxjs';
import moment from 'moment';
import {Task} from '../../../model/task/task.model';
import {GlobalProvider} from '../../providers/global-provider/global-provider';
import {HttpClient} from '@angular/common/http';
import {SERVER_API_URL} from '../../app.constants';
import {TaskType} from '../../../model/shared/task-type';
import {TranslateService} from '@ngx-translate/core';
import {NgForm} from '@angular/forms';

@IonicPage()
@Component({
  selector: 'page-checkout-modal',
  templateUrl: 'checkout-modal.html',
})
export class CheckoutModalPage {
  public currentLocation: number[] = [];
  public task: Task;
  public obs: string;
  public taskFinalStatus: string;

  constructor(
    public navCtrl: NavController,
    public viewCtrl: ViewController,
    public navParams: NavParams,
    public locationService: LocationService,
    public globalProvider: GlobalProvider,
    public http: HttpClient,
    public translate: TranslateService,
    public toastCtrl: ToastController
  ) {}

  ionViewDidLoad() {
    this.translate.setDefaultLang('pt');
    this.task = this.navParams.data;
  }

  private navigatorGeolocationErrorHandler(error: Error) {
    throwError(error);
  }

  private handleCoordsCheckout(resp): void {
    this.currentLocation.push(resp.coords.latitude);
    this.currentLocation.push(resp.coords.longitude);
    this.getCheckout();
  }

  public checkout(form: NgForm): void {
    if (!form.controls['obs']) {
      this.obs = '';
    } else {
      this.obs = form.controls['obs'].value;
    }

    this.setInputValue(this.obs, form.controls['taskFinalStatus'].value);
    if (navigator && navigator.geolocation) {
      this.locationService.getNavigatorCurrentLocation(
        this.handleCoordsCheckout.bind(this),
        this.navigatorGeolocationErrorHandler.bind(this)
      );
    } else {
      this.locationService
        .getDeviceCurrentLocation()
        .then(resp => {
          this.handleCoordsCheckout(resp);
        })
        .catch(err => throwError(err));
    }
  }

  private getCheckout(): void {
    let status = this.task.status_atendimento;
    let paramsArray = [
      {
        acao: 'checkout',
        params: {
          idusuario_atendimento: this.task.idusuario_atendimento.toString(),
          status: status,
          latitude: this.currentLocation[0],
          longitude: this.currentLocation[1],
          data_hora: moment().format('YYYY-MM-DD HH:mm:ss'),
        },
      },
    ];
    let headers = {
      'Content-Type': 'application/x-www-form-urlencoded',
      Accept: '*/*',
      Authorization: 'Bearer ' + this.globalProvider.getToken(),
    };

    let body = new URLSearchParams();
    body.set('fila', JSON.stringify(paramsArray));

    this.http.post(SERVER_API_URL + '/fila?', body.toString(), {headers: headers}).subscribe(res => {
      console.log(res);
      this.globalProvider.checkOutTask(this.task.idatendimento, moment().format('YYYY-MM-DD HH:mm:ss'), TaskType.DONE);
      this.successCheckoutToast();
      this.closeModal();
    });
  }

  public setInputValue(obs: string, finalState: string) {
    this.task.observacao = obs;

    if (finalState == 'finalizado') {
      this.task.status_atendimento = TaskType.DONE;
    } else if (finalState == 'naoTerminado') {
      this.task.status_atendimento = TaskType.NOT_DONE;
    } else if (finalState == '') {
      this.task.status_atendimento = TaskType.DONE;
    }

    this.globalProvider.updateTask(this.task);
  }
  public closeModal(): void {
    this.viewCtrl.dismiss();
  }

  public successCheckoutToast(): void {
    let message: string;
    this.translate.get('CHECKOUT_SUCCESS').subscribe(res => (message = res));
    let toast = this.toastCtrl.create({
      message: message,
      duration: 3000,
      position: 'bottom',
    });

    toast.present();
  }
}
