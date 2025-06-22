import {Component} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../../../service/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  constructor(private router: Router, private activatedRoute: ActivatedRoute, private authService: AuthService) {
  }

  search(key: string): void {
    this.activatedRoute.paramMap.subscribe(params => {
      const hasId = this.activatedRoute.firstChild.snapshot.paramMap.has('id');
      if (hasId) {
        const id = this.activatedRoute.firstChild.snapshot.paramMap.get('id');
        this.router.navigateByUrl(`/category/${id}/search/${key}`);
      } else {
        this.router.navigateByUrl(`/search/${key}`);
      }
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigateByUrl(`/login`);
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  isLogin(): boolean {
    return this.authService.isLogin();
  }
}
