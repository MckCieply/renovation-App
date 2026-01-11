import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';
import { NotificationService } from '../shared/services/notification.service';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;
  let router: jasmine.SpyObj<Router>;
  let notificationService: jasmine.SpyObj<NotificationService>;

  const mockCredentials = { username: 'testuser', password: 'testpass' };
  const mockRegisterData = { username: 'newuser', email: 'test@test.com', password: 'newpass' };
  const mockRoles = [{ id: 1, name: 'USER' }, { id: 2, name: 'ADMIN' }];

  beforeEach(() => {
    const routerSpy = jasmine.createSpyObj('Router', ['navigate']);
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        AuthService,
        { provide: Router, useValue: routerSpy },
        { provide: NotificationService, useValue: notificationSpy }
      ]
    });

    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;

    // Clear localStorage before each test
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
    localStorage.clear();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send login request', () => {
    service.login(mockCredentials).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/auth/login');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockCredentials);
    req.flush({});
  });

  it('should send register request and show success notification', () => {
    service.register(mockRegisterData).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/auth/register');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockRegisterData);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Account created successfully!');
  });

  it('should fetch roles', () => {
    service.roles().subscribe(roles => {
      expect(roles).toEqual(mockRoles);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/auth/roles');
    expect(req.request.method).toBe('GET');
    req.flush(mockRoles);
  });

  it('should set and get token', () => {
    const token = 'test-jwt-token';

    service.setToken(token);

    expect(service.getToken()).toBe(token);
    expect(localStorage.getItem('renovationApp.token')).toBe(token);
  });

  it('should clear token', () => {
    const token = 'test-jwt-token';
    service.setToken(token);

    service.clearToken();

    expect(service.getToken()).toBeNull();
    expect(localStorage.getItem('renovationApp.token')).toBeNull();
  });

  it('should check if authenticated when token exists', () => {
    service.setToken('test-token');

    expect(service.isAuthenticated()).toBe(true);
  });

  it('should check if not authenticated when no token', () => {
    service.clearToken();

    expect(service.isAuthenticated()).toBe(false);
  });

  it('should set and get username', () => {
    const username = 'testuser';

    service.setUsername(username);

    expect(service.getUsername()).toBe(username);
    expect(localStorage.getItem('username')).toBe(username);
  });

  it('should logout and navigate to login', () => {
    service.setToken('test-token');
    service.setUsername('testuser');

    service.logout();

    expect(service.getToken()).toBeNull();
    expect(router.navigate).toHaveBeenCalledWith(['Auth/Login']);
  });

  it('should handle auth success', () => {
    const username = 'testuser';

    service.authSuccess(username);

    expect(router.navigate).toHaveBeenCalledWith(['/Dashboard']);
    expect(service.getUsername()).toBe(username);
  });

  it('should get token from localStorage if not in memory', () => {
    const token = 'stored-token';
    localStorage.setItem('renovationApp.token', token);

    expect(service.getToken()).toBe(token);
  });
});
