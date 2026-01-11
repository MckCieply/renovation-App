import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserService } from './user.service';
import { NotificationService } from '../shared/services/notification.service';

describe('UserService', () => {
  let service: UserService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;

  const mockUser = { id: 1, username: 'testuser', email: 'test@test.com' };
  const mockPasswordChange = { currentPassword: 'old', newPassword: 'new' };

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        UserService,
        { provide: NotificationService, useValue: notificationSpy }
      ]
    });

    service = TestBed.inject(UserService);
    httpMock = TestBed.inject(HttpTestingController);
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get user by username', () => {
    const username = 'testuser';

    service.getUser(username).subscribe(user => {
      expect(user).toEqual(mockUser);
    });

    const req = httpMock.expectOne(`http://localhost:8080/api/user/get?username=${username}`);
    expect(req.request.method).toBe('GET');
    req.flush(mockUser);
  });

  it('should update user and show success notification', () => {
    service.updateUser(mockUser).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/user/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockUser);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('User profile updated successfully!');
  });

  it('should change password and show success notification', () => {
    service.changePassword(mockPasswordChange).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/user/change-password');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockPasswordChange);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Password changed successfully!');
  });
});
