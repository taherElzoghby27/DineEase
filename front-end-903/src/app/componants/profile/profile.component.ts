import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor() {
  }

  profile: any;
  defaultPhoto = 'https://randomuser.me/api/portraits/men/32.jpg';
  editing = false;
  profileComplete = false;

  ngOnInit(): void {
    // On signup only username and optional photo are provided
    this.profile = {
      username: 'JohnDoe',
      photoUrl: '', // Optional
      age: null,
      phone: '',
      address: ''
    };
  }

  saveProfile(): void {
    if (this.profile.age && this.profile.phone && this.profile.address) {
      this.profileComplete = true;
      this.editing = false;
      alert('Profile saved!');
    } else {
      alert('Please fill in all fields.');
    }
  }

}
