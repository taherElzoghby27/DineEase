import { TestBed } from '@angular/core/testing';

import { AuthInterceptors } from './auth-interceptors.service';

describe('AuthService', () => {
  let service: AuthInterceptors;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AuthInterceptors);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
