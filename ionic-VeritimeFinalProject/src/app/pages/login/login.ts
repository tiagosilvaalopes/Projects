import {Component} from '@angular/core';
import {NavController} from 'ionic-angular';
import {TranslateService} from '@ngx-translate/core';
import {NgForm} from '@angular/forms';
import {SERVER_API_URL} from '../../app.constants';
import {HomePage} from '../home/home';
import {HttpClient} from '@angular/common/http';
import {IUser} from '../../../model/user/user.model';
import {ToastController} from 'ionic-angular';
import {UserService} from '../../providers/user-service/user-service';
import {LoginService} from '../../providers/login-service/login-service';
import {GlobalProvider} from '../../providers/global-provider/global-provider';

@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {
  public user: IUser;

  constructor(
    private http: HttpClient,
    protected loginService: LoginService,
    private navCtrl: NavController,
    private translateService: TranslateService,
    private toastCtrl: ToastController,
    protected userService: UserService,
    protected globalProvider: GlobalProvider
  ) {}

  ionViewWillEnter() {
    this.translateService.setDefaultLang('pt');
  }

  public login(form: NgForm): void {
    this.loginService.login(form.controls['email'].value, form.controls['password'].value).subscribe(
      data => {
        this.user = data['usuario'];
        this.globalProvider.setToken(data['token']);

        if (this.user != null) {
          this.globalProvider.setUser(this.user);
          this.navCtrl.push(HomePage);
        } else {
          this.presentToast();
        }
      },
      error => this.presentToast()
    );
  }

  private presentToast(): void {
    let toast = this.toastCtrl.create({
      message: 'Login Error.',
      duration: 3000,
      position: 'bottom',
    });

    toast.onDidDismiss(() => {
      console.log('Dismissed toast');
    });

    toast.present();
  }
}
