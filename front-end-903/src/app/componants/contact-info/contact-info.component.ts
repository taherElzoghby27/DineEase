import {Component, OnInit} from '@angular/core';
import {ContactInfoService} from '../../../service/contact-info.service';
import {ContactInfo} from '../../../model/contact-info';
import {AuthService} from '../../../service/auth.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {
  contacts: ContactInfo[] = [];
  messageErrorAr = '';
  errorMessageEn = '';


  constructor(private contactInfoService: ContactInfoService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => this.allContactsInfo());
  }



  toggleComments(contact: any): void {
    contact.showComments = !contact.showComments;
  }

  addComment(contact: any): void {
    if (contact.newComment?.trim()) {
      contact.comments.push(contact.newComment.trim());
      contact.newComment = '';
    }
  }



  allContactsInfo(): void {
    this.contactInfoService.allContactInfo().subscribe(
      response => {
        this.contacts = response;
      },
      errors => {
        this.messageErrorAr = errors.error.bundleMessage.message_ar;
        this.errorMessageEn = errors.error.bundleMessage.message_en;
      }
    );
  }


}
