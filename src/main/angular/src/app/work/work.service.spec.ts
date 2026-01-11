import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpParams } from '@angular/common/http';
import { WorkService } from './work.service';
import { NotificationService } from '../shared/services/notification.service';
import { HttpParamsBuilderService } from '../shared/services/http-params-builder.service';

describe('WorkService', () => {
  let service: WorkService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;
  let paramsBuilder: jasmine.SpyObj<HttpParamsBuilderService>;

  const mockWork = {
    id: 1,
    name: 'Install flooring',
    description: 'Install hardwood flooring in living room',
    status: 'PLANNED'
  };

  const mockWorks = [mockWork, { id: 2, name: 'Paint walls', status: 'IN_PROGRESS' }];
  const mockWorkStatuses = ['PLANNED', 'IN_PROGRESS', 'COMPLETED', 'CANCELLED'];

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);
    const paramsBuilderSpy = jasmine.createSpyObj('HttpParamsBuilderService', ['buildHttpParams']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        WorkService,
        { provide: NotificationService, useValue: notificationSpy },
        { provide: HttpParamsBuilderService, useValue: paramsBuilderSpy }
      ]
    });

    service = TestBed.inject(WorkService);
    httpMock = TestBed.inject(HttpTestingController);
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
    paramsBuilder = TestBed.inject(HttpParamsBuilderService) as jasmine.SpyObj<HttpParamsBuilderService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all works', () => {
    service.getAllWorks().subscribe(works => {
      expect(works).toEqual(mockWorks);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/works/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockWorks);
  });

  it('should add work and show success notification', () => {
    service.addWork(mockWork).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/works/add');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockWork);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work added successfully!');
  });

  it('should delete work and show success notification', () => {
    service.deleteWork(mockWork).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/works/delete/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work deleted successfully!');
  });

  it('should update work and show success notification', () => {
    service.updateWork(mockWork).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/works/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockWork);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Work updated successfully!');
  });

  it('should get enum work status', () => {
    service.getEnumWorkStatus().subscribe(statuses => {
      expect(statuses).toEqual(mockWorkStatuses);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/works/get-enum-work-state');
    expect(req.request.method).toBe('GET');
    req.flush(mockWorkStatuses);
  });

  it('should filter works with params', () => {
    const filter = { status: 'PLANNED', roomId: 1 };
    const mockParams = new HttpParams().set('status', 'PLANNED').set('roomId', '1');
    paramsBuilder.buildHttpParams.and.returnValue(mockParams);

    service.filterWork(filter).subscribe();

    expect(paramsBuilder.buildHttpParams).toHaveBeenCalledWith(filter);

    const req = httpMock.expectOne(request =>
      request.url === 'http://localhost:8080/api/works/filter' &&
      request.params.get('status') === 'PLANNED' &&
      request.params.get('roomId') === '1'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockWorks);
  });
});
