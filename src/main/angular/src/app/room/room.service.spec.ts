import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpParams } from '@angular/common/http';
import { RoomService } from './room.service';
import { NotificationService } from '../shared/services/notification.service';
import { HttpParamsBuilderService } from '../shared/services/http-params-builder.service';

describe('RoomService', () => {
  let service: RoomService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;
  let paramsBuilder: jasmine.SpyObj<HttpParamsBuilderService>;

  const mockRoom = { id: 1, name: 'Living Room', area: 25.5 };
  const mockRooms = [
    { id: 1, name: 'Living Room', area: 25.5 },
    { id: 2, name: 'Kitchen', area: 15.0 }
  ];

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);
    const paramsBuilderSpy = jasmine.createSpyObj('HttpParamsBuilderService', ['buildHttpParams']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        RoomService,
        { provide: NotificationService, useValue: notificationSpy },
        { provide: HttpParamsBuilderService, useValue: paramsBuilderSpy }
      ]
    });

    service = TestBed.inject(RoomService);
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

  it('should get all rooms', () => {
    service.getAllRooms().subscribe(rooms => {
      expect(rooms).toEqual(mockRooms);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/all');
    expect(req.request.method).toBe('GET');
    req.flush(mockRooms);
  });

  it('should add room and show success notification', () => {
    service.addRoom(mockRoom).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/add');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(mockRoom);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Room added successfully!');
  });

  it('should delete room and show success notification', () => {
    service.deleteRoom(mockRoom).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/delete/1');
    expect(req.request.method).toBe('DELETE');
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Room deleted successfully!');
  });

  it('should update room and show success notification', () => {
    service.updateRoom(mockRoom).subscribe();

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(mockRoom);
    req.flush({});

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Room updated successfully!');
  });

  it('should filter rooms with params', () => {
    const filter = { name: 'Living', minArea: 20 };
    const mockParams = new HttpParams().set('name', 'Living').set('minArea', '20');
    paramsBuilder.buildHttpParams.and.returnValue(mockParams);

    service.filterRooms(filter).subscribe();

    expect(paramsBuilder.buildHttpParams).toHaveBeenCalledWith(filter);

    const req = httpMock.expectOne(request =>
      request.url === 'http://localhost:8080/api/rooms/filter' &&
      request.params.get('name') === 'Living' &&
      request.params.get('minArea') === '20'
    );
    expect(req.request.method).toBe('GET');
    req.flush(mockRooms);
  });

  it('should get minimal rooms', () => {
    const minimalRooms = [{ id: 1, name: 'Living Room' }, { id: 2, name: 'Kitchen' }];

    service.getMinimal().subscribe(rooms => {
      expect(rooms).toEqual(minimalRooms);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/minimal');
    expect(req.request.method).toBe('GET');
    req.flush(minimalRooms);
  });

  it('should check if room can be deleted', () => {
    const roomId = 1;
    const mockResponse = { canDelete: true, message: 'Room can be safely deleted' };

    service.canDeleteRoom(roomId).subscribe(result => {
      expect(result).toEqual(mockResponse);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/rooms/1/can-delete');
    expect(req.request.method).toBe('GET');
    req.flush(mockResponse);
  });
});
