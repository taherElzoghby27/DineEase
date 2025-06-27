import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {ProductDetailsResponse} from '../model/product-details-response';

@Injectable({
  providedIn: 'root'
})
export class ProductDetailsService {
  baseUrl = 'http://localhost:6060/product-details/';

  constructor(private http: HttpClient) {
  }

  getProductDetailsByProductId(id: string): Observable<ProductDetailsResponse> {
    return this.http.get<ProductDetailsResponse>(`${this.baseUrl}get-product-details?id=${id}`)
      .pipe(
        map(response => response)
      );
  }
}
