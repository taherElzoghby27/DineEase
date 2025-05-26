import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  baseUrl = 'http://localhost:6060/products/';

  constructor(private http: HttpClient) {
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl + 'all-products').pipe(
      map(response => response)
    );
  }

  getProductsByCategoryId(id: string): Observable<Product[]> {
    return this.http.get<Product[]>(this.baseUrl + 'all-products/' + id).pipe(
      map(response => response)
    );
  }
}
