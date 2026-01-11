import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AdminService } from './admin.service';

describe('AdminService', () => {
  let service: AdminService;
  let httpMock: HttpTestingController;

  const mockUsers = [
    { id: 1, username: 'user1', email: 'user1@test.com', roles: ['USER'] },
    { id: 2, username: 'admin1', email: 'admin1@test.com', roles: ['USER', 'ADMIN'] }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AdminService]
    });

    service = TestBed.inject(AdminService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all users', () => {
    service.getAllUsers().subscribe(users => {
      expect(users).toEqual(mockUsers);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/user/get-all');
    expect(req.request.method).toBe('GET');
    req.flush(mockUsers);
  });

  it('should update user admin status', () => {
    const user = mockUsers[0];
    const isAdmin = true;
    const expectedBody = { user, isAdmin };

    service.isAdmin(user, isAdmin).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/user/update-roles');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(expectedBody);
    req.flush({});
  });

  it('should remove admin status from user', () => {
    const user = mockUsers[1];
    const isAdmin = false;
    const expectedBody = { user, isAdmin };

    service.isAdmin(user, isAdmin).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/user/update-roles');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(expectedBody);
    req.flush({});
  });
});
