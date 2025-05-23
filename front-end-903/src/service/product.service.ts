import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Product} from '../model/product';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  url = 'http://localhost:6060/products/all-products';

  constructor(private http: HttpClient) {
  }

  getAllProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(this.url).pipe(
      map(response => response)
    );
  }
}
