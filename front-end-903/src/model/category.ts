export class Category {
  id?: number;
  name: string;
  logo: string;
  flag: string;

  constructor(name: string, logo: string, flag: string, id?: number) {
    this.id = id;
    this.name = name;
    this.logo = logo;
    this.flag = flag;
  }
}
