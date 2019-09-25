export interface IUser {
  name?: string;
  businessName?: string;
  contacto?: string;
}

export class Client {
  public name?: string;
  public businessName?: string;
  public contacto?: string;

  constructor(name?: string, businessName?: string, contacto?: string) {
    this.name = 'Teste nome Cliente';
    this.contacto = '969908160';
  }
  public getName(): string {
    return this.name;
  }

  public getNumber(): string {
    return this.contacto;
  }
}
