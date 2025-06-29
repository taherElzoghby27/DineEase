import {Component, OnInit} from '@angular/core';
import {AuthService} from '../service/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  constructor(private authService: AuthService, private router: Router) {
  }

  isCardDetails = false;

  isLogin(): boolean {
    return this.authService.isLogin();
  }

  ngOnInit(): void {
    this.router.events.subscribe(() => {
      const url = this.router.url;
      if (url.includes('/cardDetails')) {
        this.isCardDetails = true;
      } else {
        this.isCardDetails = false;
      }
    });
  }
}
