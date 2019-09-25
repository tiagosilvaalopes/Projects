import {NgModule} from '@angular/core';
import {IonicPageModule} from 'ionic-angular';
import {TranslateModule} from '@ngx-translate/core';
import {TaskItem} from './task-item';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

@NgModule({
  declarations: [TaskItem],
  imports: [IonicPageModule.forChild(TaskItem), TranslateModule.forChild()],
  exports: [TaskItem],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TaskPageModule {}
