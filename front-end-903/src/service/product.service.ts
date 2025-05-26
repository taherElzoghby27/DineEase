import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
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

  getProductsByKey(key: string): Observable<Product[]> {
    const params = new HttpParams().set('key', key);
    return this.http.get<Product[]>(this.baseUrl + 'all-products-by-key', {params})
      .pipe(
        map(response => response)
      );
  }
}
