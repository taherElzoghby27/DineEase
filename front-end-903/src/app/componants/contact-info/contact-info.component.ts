import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-contact-info',
  templateUrl: './contact-info.component.html',
  styleUrls: ['./contact-info.component.css']
})
export class ContactInfoComponent implements OnInit {
  contacts = [
    {
      subject: 'General Inquiry',
      message: 'What are your opening hours?',
      comments: ['We are open 9 AM - 10 PM.', 'Also open on holidays.'],
      newComment: '',
      showComments: false
    },
    {
      subject: 'Reservation',
      message: 'Can I book a table for 6 people?',
      comments: ['Yes, we can accommodate that.', 'Please call ahead.'],
      newComment: '',
      showComments: false
    }
  ];


  constructor() {
  }

  ngOnInit(): void {
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


}
