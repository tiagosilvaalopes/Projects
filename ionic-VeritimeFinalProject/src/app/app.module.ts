import {BrowserModule} from '@angular/platform-browser';
import {HttpClientModule, HttpClient} from '@angular/common/http';
import {HTTP} from '@ionic-native/http';
import {ErrorHandler, NgModule} from '@angular/core';
import {IonicApp, IonicErrorHandler, IonicModule} from 'ionic-angular';
//:::TRANSLATE::::
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';

//:::PAGES:::
import {MyApp} from './app.component';
import {LoginPage} from './pages/login/login';
import {HomePage} from './pages/home/home';
import {TaskListPage} from './pages/task-list/task-list';
import {TaskItem} from './pages/task/task-item';
import {CheckoutModalPage} from './pages/checkout-modal/checkout-modal';

import {StatusBar} from '@ionic-native/status-bar';
import {SplashScreen} from '@ionic-native/splash-screen';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
//:::SERVICES:::
import {LoginService} from './providers/login-service/login-service';
import {CallNumber} from '@ionic-native/call-number/ngx';
import {UserService} from './providers/user-service/user-service';
import {TaskServiceProvider} from './providers/task-service/task-service';
import {GlobalProvider} from './providers/global-provider/global-provider';
import {SyncService} from './providers/sync-sevice/sync-service';
import {LocationService} from './providers/location-service/location-service';
import {Geolocation} from '@ionic-native/geolocation/ngx';
import {NativeGeocoder} from '@ionic-native/native-geocoder/ngx';
import {LaunchNavigator} from '@ionic-native/launch-navigator/ngx';

//:::STORAGE:::
import {IonicStorageModule} from '@ionic/storage';

export function createTranslateLoader(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}

@NgModule({
  declarations: [MyApp, LoginPage, HomePage, TaskListPage, TaskItem, CheckoutModalPage],
  imports: [
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp),
    IonicStorageModule.forRoot(),
    TranslateModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: createTranslateLoader,
        deps: [HttpClient],
      },
    }),
  ],
  bootstrap: [IonicApp],
  entryComponents: [MyApp, LoginPage, HomePage, TaskListPage, TaskItem, CheckoutModalPage],
  providers: [
    Geolocation,
    NativeGeocoder,
    LaunchNavigator,
    HTTP,
    Storage,
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler},
    LoginService,
    UserService,
    TaskServiceProvider,
    GlobalProvider,
    SyncService,
    LocationService,
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class AppModule {}
