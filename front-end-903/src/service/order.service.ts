import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {RequestOrder} from '../model/request-order';
import {Observable} from 'rxjs';
import {RequestOrderResponse} from '../model/request-order-response';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  baseUrl = 'http://localhost:6060/orders/';

  constructor(private http: HttpClient) {
  }


  requestOrder(requestOrder: RequestOrder): any {
    const {accountId, totalPrice, totalNumber, productsIds} = requestOrder;
    return this.http.post<any>(`${this.baseUrl}request-order`,
      {
        accountId,
        totalPrice,
        totalNumber,
        productsIds
      })
      .pipe(
        map(response => response)
      );
  }

  getAllRequestOrders(): Observable<RequestOrderResponse[]> {
    return this.http.get<RequestOrderResponse[]>(`${this.baseUrl}all-request-orders`)
      .pipe(
        map(response => response)
      );
  }
}
