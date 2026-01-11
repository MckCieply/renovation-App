import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { WorkTypeService } from './work-type.service';
import { NotificationService } from '../shared/services/notification.service';

describe('WorkTypeService', () => {
  let service: WorkTypeService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;

  const mockWorkType = { id: 1, name: 'Plumbing' };
  const mockWorkTypes = [
    { id: 1, name: 'Plumbing' },
    { id: 2, name: 'Electrical' }
  ];

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        WorkTypeService,
        { provide: NotificationService, useValue: notificationSpy }
      ]
    });

    service = TestBed.inject(WorkTypeService);
    httpMock = TestBed.inject(HttpTestingController);
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch all work types', () => {
    service.getAllTypes().subscribe(types => {
      expect(types).toEqual(mockWorkTypes);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/work-types/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockWorkTypes);
  });

  it('should add a work type', () => {
    service.addType(mockWorkType).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/work-types/add');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockWorkType);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work type added successfully!');
  });

  it('should update a work type', () => {
    service.updateType(mockWorkType).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/work-types/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockWorkType);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work type updated successfully!');
  });

  it('should delete a work type', () => {
    service.deleteType(mockWorkType).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/work-types/delete/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work type deleted successfully!');
  });

  it('should fetch minimal work types', () => {
    service.getMinimal().subscribe(types => {
      expect(types).toEqual(mockWorkTypes);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/work-types/minimal');
    expect(req.request.method).toBe('GET');
    req.flush(mockWorkTypes);
  });
});
