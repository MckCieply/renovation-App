import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ContractorsService } from './contractors.service';
import { NotificationService } from '../shared/services/notification.service';

describe('ContractorsService', () => {
  let service: ContractorsService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;

  const mockContractor = {
    id: 1,
    name: 'ABC Construction',
    email: 'contact@abc.com',
    phone: '+48123456789'
  };

  const mockContractors = [
    mockContractor,
    { id: 2, name: 'XYZ Renovations', email: 'info@xyz.com', phone: '+48987654321' }
  ];

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        ContractorsService,
        { provide: NotificationService, useValue: notificationSpy }
      ]
    });

    service = TestBed.inject(ContractorsService);
    httpMock = TestBed.inject(HttpTestingController);
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all contractors', () => {
    service.getAllContractors().subscribe(contractors => {
      expect(contractors).toEqual(mockContractors);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/contractors/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockContractors);
  });

  it('should add contractor and show success notification', () => {
    service.addContractor(mockContractor).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/contractors/add');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockContractor);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Contractor added successfully!');
  });

  it('should delete contractor and show success notification', () => {
    service.deleteContractor(mockContractor).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/contractors/delete/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Contractor deleted successfully!');
  });

  it('should update contractor and show success notification', () => {
    service.updateContractor(mockContractor).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/contractors/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockContractor);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Contractor updated successfully!');
  });
});
