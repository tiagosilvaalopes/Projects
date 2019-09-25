import {NgModule} from '@angular/core';
import {IonicPageModule} from 'ionic-angular';
import {CheckoutModalPage} from './checkout-modal';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';
import {TranslateModule} from '@ngx-translate/core';
@NgModule({
  declarations: [CheckoutModalPage],
  imports: [IonicPageModule.forChild(CheckoutModalPage), TranslateModule.forChild()],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class CheckoutModalPageModule {}
