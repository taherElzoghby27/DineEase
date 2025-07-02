import {RouterModule, Routes} from '@angular/router';
import {AppComponent} from './app.component';
import {NgModule} from '@angular/core';
import {ProductsComponent} from './componants/products/products.component';
import {HeaderComponent} from './componants/header/header.component';
import {CategoryComponent} from './componants/category/category.component';
import {CardDetailsComponent} from './componants/card-details/card-details.component';
import {CardComponent} from './componants/card/card.component';
import {BrowserModule} from '@angular/platform-browser';
import {FooterComponent} from './componants/footer/footer.component';
import {ChefsComponent} from './componants/chefs/chefs.component';
import {ContactInfoComponent} from './componants/contact-info/contact-info.component';
import {APP_BASE_HREF} from '@angular/common';
import {PanelComponent} from './componants/panel/panel.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {NgbPaginationModule} from '@ng-bootstrap/ng-bootstrap';
import {LoginComponent} from './componants/login/login.component';
import {SignUpComponent} from './componants/sign-up/sign-up.component';
import {FormsModule} from '@angular/forms';
import {AuthInterceptor} from '../service/interceptors/auth-interceptors.service';
import {LoginSignupGuard} from '../service/activitor/login-signup.guard';
import {AuthGuard} from '../service/activitor/auth.guard';
import {ProductDetailDialogComponent} from './componants/product-detail-dialog/product-detail-dialog.component';
import {NoopAnimationsModule} from '@angular/platform-browser/animations';
import {MatDialogModule} from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {OrderDialogComponent} from './componants/order-dialog/order-dialog.component';
import {AllRequestOrdersComponent} from './componants/all-request-orders/all-request-orders.component';
import {ProfileComponent} from './componants/profile/profile.component';

// http://localhost:4200/
export const routes: Routes = [
  {path: 'login', component: LoginComponent, canActivate: [LoginSignupGuard]},
  {path: 'sign-up', component: SignUpComponent, canActivate: [LoginSignupGuard]},
  // http://localhost:4200/products
  {path: 'products', component: ProductsComponent, canActivate: [AuthGuard]},
  {path: 'category/:id', component: ProductsComponent, canActivate: [AuthGuard]},
  {path: 'search/:key', component: ProductsComponent, canActivate: [AuthGuard]},
  {path: 'category/:id/search/:key', component: ProductsComponent, canActivate: [AuthGuard]},
  // http://localhost:4200/cardDetails
  {path: 'cardDetails', component: CardDetailsComponent, canActivate: [AuthGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [AuthGuard]},
  {path: 'allRequestOrders', component: AllRequestOrdersComponent, canActivate: [AuthGuard]},
  // http://localhost:4200/cardDetails
  {path: 'contact-info', component: ContactInfoComponent, canActivate: [AuthGuard]},
  // http://localhost:4200/chefs
  {path: 'chefs', component: ChefsComponent, canActivate: [AuthGuard]},
  {path: 'panel', component: PanelComponent, canActivate: [AuthGuard]},
  // http://localhost:4200/
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: '**', redirectTo: '/login', pathMatch: 'full'}
];

/*
*   // http://localhost:4200/
  {path: '', component:OrderItemsComponent}
* */
@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    HeaderComponent,
    CategoryComponent,
    CardDetailsComponent,
    CardComponent,
    FooterComponent,
    ChefsComponent,
    ContactInfoComponent,
    PanelComponent,
    LoginComponent,
    SignUpComponent,
    ProductDetailDialogComponent,
    OrderDialogComponent,
    AllRequestOrdersComponent,
    ProfileComponent
  ],
  imports: [
    RouterModule.forRoot(routes),
    BrowserModule,
    HttpClientModule,
    NgbPaginationModule,
    FormsModule,
    NoopAnimationsModule,
    MatDialogModule,
    MatButtonModule,
    MatProgressSpinnerModule,
    MatSnackBarModule
  ],
  providers: [
    {provide: APP_BASE_HREF, useValue: '/'},
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
