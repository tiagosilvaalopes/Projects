import {Geolocation, Geoposition} from '@ionic-native/geolocation/ngx';
import {Injectable} from '@angular/core';

@Injectable()
export class LocationService {
  constructor(public geolocation: Geolocation) {}
  private options = {
    timeout: 5000,
    enableHighAccuracy: false,
  };

  public getNavigatorCurrentLocation(successCallback, errorHandler): void {
    navigator.geolocation.getCurrentPosition(successCallback, errorHandler, this.options);
  }

  public getDeviceCurrentLocation(): Promise<Geoposition> {
    return this.geolocation.getCurrentPosition(this.options);
  }

  public calcDistance(coordsUser: number[], coordsClient: number[]): number {
    var R = 6371; // Radius of the earth in km
    var dLat = this.deg2rad(coordsClient[0] - coordsUser[0]);
    var dLon = this.deg2rad(coordsClient[1] - coordsUser[1]);
    var a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
      Math.cos(this.deg2rad(coordsUser[0])) *
        Math.cos(this.deg2rad(coordsClient[0])) *
        Math.sin(dLon / 2) *
        Math.sin(dLon / 2);
    var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    var d = R * c; // Distance in km
    return d;
  }

  private deg2rad(deg): number {
    return deg * (Math.PI / 180);
  }
}
