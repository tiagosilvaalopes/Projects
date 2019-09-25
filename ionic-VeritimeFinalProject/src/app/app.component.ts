import {Component, ViewChild, TestabilityRegistry} from '@angular/core';
import {Nav, Platform} from 'ionic-angular';
import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';

import {LoginPage} from './pages/login/login';
import {HomePage} from './pages/home/home';
import {TaskListPage} from './pages/task-list/task-list';
import {TranslateService} from '@ngx-translate/core';
import {Task} from '../model/task/task.model';

@Component({
  templateUrl: 'app.html',
})
export class MyApp {
  @ViewChild(Nav) nav: Nav;

  rootPage: any = LoginPage;
  pages: Array<{title: string; component: any}>;
  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen,
    private translate: TranslateService
  ) {
    this.initializeApp();

    // used for an example of ngFor and navigation
    this.pages = [{title: 'login', component: LoginPage}, {title: 'Home', component: HomePage}];
  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });

    this.translate.setDefaultLang('pt');
  }

  openPage(page) {
    // Reset the content nav to have just this page
    // we wouldn't want the back button to show in this scenario
    this.nav.setRoot(page.component);
  }
}
