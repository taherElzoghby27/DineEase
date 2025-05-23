import {Injectable} from '@angular/core';
import {Category} from '../model/category';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  url = 'http://localhost:6060/categories/all-categories';

  constructor(private http: HttpClient) {
  }

  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.url).pipe(
      map(response => response)
    );
  }
}
