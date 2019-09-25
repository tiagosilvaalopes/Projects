import {Component} from '@angular/core';
import {IonicPage, NavController, NavParams, ViewController} from 'ionic-angular';
import {TranslateService} from '@ngx-translate/core';
import {TaskListPage} from '../task-list/task-list';
import {TaskServiceProvider} from '../../providers/task-service/task-service';
import {Task} from '../../../model/task/task.model';
import {TaskType} from '../../../model/shared/task-type';
import {User} from '../../../model/user/user.model';
import {UserService} from '../../providers/user-service/user-service';
import {GlobalProvider} from '../../providers/global-provider/global-provider';
import {SyncService} from '../../providers/sync-sevice/sync-service';
import {Observable, Subscription} from 'rxjs/Rx';

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {
  public tasks: Task[] = [];
  public user: User;
  public taskType = TaskType;
  public timer: Subscription;
  constructor(
    private navCtrl: NavController,
    private viewCtrl: ViewController,
    private translate: TranslateService,
    protected userService: UserService,
    protected taskProvider: TaskServiceProvider,
    protected globalProvider: GlobalProvider,
    protected syncService: SyncService
  ) {}

  public language: string = this.translate.currentLang;

  ionViewWillEnter() {
    this.user = this.globalProvider.getUser();
    this.viewCtrl.showBackButton(false);
    this.loadAll();
    this.timer = Observable.interval(120000).subscribe(tick => this.loadAll());
  }

  ionViewWillLeave() {
    this.timer.unsubscribe();
  }

  public addTask() {
    //chamada na altura de clicar no btn de adicionar tarefa
  }

  public switchLanguage(): void {
    this.translate.use(this.language);
  }

  public listOnGoing(): void {
    this.navCtrl.push(TaskListPage, {taskType: TaskType.ON_GOING});
  }

  public listWaiting(): void {
    this.navCtrl.push(TaskListPage, {taskType: TaskType.WAITING});
  }

  public listDue(): void {
    this.navCtrl.push(TaskListPage, {taskType: TaskType.DUE});
  }

  public listDone(): void {
    this.navCtrl.push(TaskListPage, {taskType: TaskType.DONE});
  }

  protected loadAll(): void {
    this.syncService.syncWithServer().subscribe(result => {
      this.tasks = result['atendimentos'];
      this.globalProvider.setTasks(this.tasks);
    });
  }

  public getTaskByStatus(taskType: string): Task[] {
    return this.tasks.filter(t => taskType === t.status_atendimento);
  }
}
