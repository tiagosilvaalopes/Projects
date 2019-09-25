import {HttpClient} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {User} from '../../../model/user/user.model';
import {Storage} from '@ionic/storage';
import {resolve} from 'path';
/*
  Generated class for the UserProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class UserService {
  getToken(): Promise<string> {
    return this.storage.get('token_push');
  }
  user: User;
  constructor(private storage: Storage) {
    console.log('Hello UserProvider Provider');
  }

  public setUser(user: User): void {
    console.log('Called user service to setUser with argument %o', user);
    this.storage.set('user', user);
  }

  getUser(): Promise<User> {
    return this.storage.get('user');
  }

  saveToken(token: string) {
    this.storage.set('token_push', token);
  }
}
