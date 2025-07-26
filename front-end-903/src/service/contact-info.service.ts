import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {ContactInfo} from '../model/contact-info';
import {CommentContactInfo} from '../model/commentContactInfo';

@Injectable({
  providedIn: 'root'
})
export class ContactInfoService {
  baseUrlContactInfo = 'http://localhost:6060/contacts-info/';
  baseUrlComment = 'http://localhost:6060/comments/';

  constructor(private http: HttpClient) {
  }

  allContactInfo(filter?: string): Observable<ContactInfo[]> {
    const url = filter
      ? `${this.baseUrlContactInfo}contacts-info?filter=${filter}`
      : `${this.baseUrlContactInfo}contacts-info`;
    return this.http.get<ContactInfo[]>(url).pipe(map(response => response));
  }

  createContactInfo(contactInfo: ContactInfo): any {
    return this.http.post<any>(`${this.baseUrlContactInfo}create-contact-info`, contactInfo).pipe(
      map(
        response => response
      )
    );
  }

  createComment(comment: CommentContactInfo): any {
    return this.http.post<any>(`${this.baseUrlComment}send-comment`, comment).pipe(
      map(
        response => response
      )
    );
  }
}
