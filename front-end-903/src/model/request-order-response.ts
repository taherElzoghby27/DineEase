import {Product} from './product';

export class RequestOrderResponse {
  id: number;
  accountId: number;
  totalPrice: number;
  totalNumber: number;
  createdBy: string;
  status: string;
  code: string;
  products: Product[];
}
