export class Category {
  id?: number;
  name: string;
  logo: string;
  flag: string;
  recommended: number;

  constructor(name: string, logo: string, flag: string, id?: number, recommended?: number) {
    this.id = id;
    this.name = name;
    this.logo = logo;
    this.flag = flag;
    this.recommended = recommended;
  }
}
