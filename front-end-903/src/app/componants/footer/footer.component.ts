import {Component} from '@angular/core';
import {AuthService} from '../../../service/auth.service';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent {

  constructor(private authService: AuthService) {
  }

  isLogin(): boolean {
    return this.authService.isLogin();
  }

}
