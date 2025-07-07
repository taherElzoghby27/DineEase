import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {ProductResponseVm} from '../model/product-response-vm';
import {ProductRequest} from '../model/product-request';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  baseUrl = 'http://localhost:6060/products/';

  constructor(private http: HttpClient) {
  }

  getAllProducts(page, size): Observable<ProductResponseVm> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ProductResponseVm>(`${this.baseUrl}all-products`, {params}).pipe(
      map(response => response)
    );
  }

  getProductsByCategoryId(id: string, page, size): Observable<ProductResponseVm> {
    const params = new HttpParams().set('page', page).set('size', size);
    return this.http.get<ProductResponseVm>(`${this.baseUrl}all-products/${id}`, {params}).pipe(
      map(response => response)
    );
  }

  getProductsByKey(key: string, page, size): Observable<ProductResponseVm> {
    const params = new HttpParams().set('key', key).set('page', page).set('size', size);
    return this.http.get<ProductResponseVm>(`${this.baseUrl}all-products-by-key`, {params})
      .pipe(
        map(response => response)
      );
  }

  searchByCategoryIdAndKey(id: string, key: string, page, size): Observable<ProductResponseVm> {
    const params = new HttpParams()
      .set('key', key).set('page', page).set('size', size).set('categoryId', id);
    return this.http.get<ProductResponseVm>(`${this.baseUrl}all-products-by-key-and-category-id`, {params})
      .pipe(
        map(response => response)
      );
  }

  deleteProductById(id: string): Observable<any> {
    return this.http.delete<any>(`${this.baseUrl}delete-product?id=${id}`)
      .pipe(
        map(response => response)
      );
  }

  createProduct(productRequest: ProductRequest): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}create-product`, productRequest)
      .pipe(
        map(response => response)
      );
  }
}
