import {NgModule} from '@angular/core';
import {IonicPageModule} from 'ionic-angular';
import {TranslateModule} from '@ngx-translate/core';
import {TaskListPage} from './task-list';
import {CUSTOM_ELEMENTS_SCHEMA} from '@angular/core';

@NgModule({
  declarations: [TaskListPage],
  imports: [IonicPageModule.forChild(TaskListPage), TranslateModule.forChild()],
  exports: [TaskListPage],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
})
export class TaskListPageModule {}
