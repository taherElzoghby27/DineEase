import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {ContactInfo} from '../model/contact-info';

@Injectable({
  providedIn: 'root'
})
export class ContactInfoService {
  baseUrl = 'http://localhost:6060/contacts-info/';

  constructor(private http: HttpClient) {
  }

  allContactInfoForAdmin(filter?: string): Observable<ContactInfo> {
    const url = filter
      ? `${this.baseUrl}contacts-info?filter=${filter}`
      : `${this.baseUrl}contacts-info`;
    return this.http.get<ContactInfo>(url).pipe(map(response => response));
  }

  allContactInfoForUser(filter?: string): Observable<ContactInfo> {
    const url = filter
      ? `${this.baseUrl}contacts-info-for-account?filter=${filter}`
      : `${this.baseUrl}contacts-info-for-account`;
    return this.http.get<ContactInfo>(url).pipe(map(response => response));
  }

  createContactInfo(contactInfo: ContactInfo): any {
    return this.http.post<any>(`${this.baseUrl}create-contact-info`, contactInfo).pipe(
      map(
        response => response
      )
    );
  }
}
