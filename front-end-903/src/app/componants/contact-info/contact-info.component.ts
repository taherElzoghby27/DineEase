import {Component, OnInit} from '@angular/core';
import {ContactInfoService} from '../../../service/contact-info.service';
import {ContactInfo, FilterContactInfo} from '../../../model/contact-info';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../../service/auth.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {CommentContactInfo} from '../../../model/commentContactInfo';

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {
  contacts: ContactInfo[] = [];
  messageErrorAr = '';
  messageErrorEn = '';
  currentFilter = FilterContactInfo.ALL;
  FilterContactInfo = FilterContactInfo;
  newComment = '';
  // form
  name = '';
  email = '';
  subject = '';
  message = '';
  // error fields
  nameError = '';
  emailError = '';
  subjectError = '';
  messageError = '';
  errorBackend = false;
  errorBackendForm = false;
  messageErrorArForm = '';
  messageErrorEnForm = '';


  constructor(private contactInfoService: ContactInfoService,
              private activatedRoute: ActivatedRoute,
              private authService: AuthService,
              private snackBar: MatSnackBar,
              private router: Router) {
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe(params => this.allContactsInfo());
  }

  getCurrentUser(): string {
    return this.authService.getCurrentUser();
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }


  toggleComments(contact: any): void {
    contact.showComments = !contact.showComments;
  }

  changeFilter(status: FilterContactInfo): void {
    this.currentFilter = status;
    this.allContactsInfo();
  }

  createContactInfo(): void {
    if (!this.validateForm()) {
      return;
    }
    const contact = new ContactInfo({
      name: this.name,
      email: this.email,
      subject: this.subject,
      message: this.message
    });

    this.contactInfoService.createContactInfo(contact).subscribe(
      response => {
        this.resetErrors();
        this.errorBackendForm = false;
        this.snackBar.open('Contact Info Added Successfully', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.router.navigateByUrl('contact-info');
        this.resetFields();
      },
      errors => {
        this.errorBackendForm = true;
        this.messageErrorArForm = errors.error.bundleMessage.message_ar;
        this.messageErrorEnForm = errors.error.bundleMessage.message_en;
      }
    );
  }

  validateForm(): boolean {
    this.resetErrors();
    if (this.name === '') {
      this.nameError = 'Please enter a valid name';
      return false;
    }

    if (this.email === '') {
      this.emailError = 'Please enter a valid email';
      return false;
    }

    if (this.subject === '') {
      this.subjectError = 'Please enter a valid subject';
      return false;
    }

    if (this.message === '') {
      this.messageError = 'Please enter a valid message';
      return false;
    }
    return true;
  }

  resetErrors(): void {
    this.nameError = '';
    this.subjectError = '';
    this.emailError = '';
    this.messageError = '';
    this.messageErrorEn = '';
    this.messageErrorAr = '';
    this.messageErrorEnForm = '';
    this.messageErrorArForm = '';
  }

  resetFields(): void {
    this.email = '';
    this.name = '';
    this.subject = '';
    this.message = '';
  }

  addComment(contactInfoId: number, accountId: number): void {
    if (this.newComment !== '') {
      this.sendComment(this.newComment, contactInfoId, accountId);
    }
  }

  sendComment(comment: string, contactId: number, accountId: number): void {
    let commentContactInfo: CommentContactInfo;
    if (this.isAdmin()) {
      commentContactInfo = new CommentContactInfo(
        {
          value: comment,
          contactInfoId: contactId,
          receiverId: accountId,
        }
      );
    } else {
      commentContactInfo = new CommentContactInfo(
        {
          value: comment,
          contactInfoId: contactId,
        }
      );
    }
    this.contactInfoService.createComment(commentContactInfo).subscribe(
      response => {
        this.newComment = '';
        this.snackBar.open('Comment Added Successfully', 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
        this.router.navigateByUrl('contact-info');
      },
      errors => {
        this.messageErrorAr = errors.error.bundleMessage.message_ar;
        this.messageErrorEn = errors.error.bundleMessage.message_en;
        this.snackBar.open(`${this.messageErrorAr}\n${this.messageErrorEn}`, 'Close', {
          duration: 3000, // milliseconds
          verticalPosition: 'top', // or 'bottom'
          panelClass: ['snackbar-success']
        });
      }
    );
  }


  allContactsInfo(): void {
    switch (this.currentFilter) {
      case FilterContactInfo.ALL:
        this.getAllContactInfo();
        break;
      case FilterContactInfo.REPLIED:
      case FilterContactInfo.NOT_REPLIED:
        this.getAllContactInfo(this.currentFilter);
        break;
    }
  }

  getAllContactInfo(status?: FilterContactInfo): void {
    this.contactInfoService.allContactInfo(status).subscribe(
      response => {
        this.contacts = response;
      },
      errors => {
        this.messageErrorAr = errors.error.bundleMessage.message_ar;
        this.messageErrorEn = errors.error.bundleMessage.message_en;
      }
    );
  }
}
