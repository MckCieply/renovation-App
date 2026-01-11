import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { DashboardService } from './dashboard.service';

describe('DashboardService', () => {
  let service: DashboardService;
  let httpMock: HttpTestingController;

  const mockCostBreakdown = {
    totalCost: 50000,
    spentAmount: 25000,
    categories: [
      { name: 'Materials', amount: 15000 },
      { name: 'Labor', amount: 10000 }
    ]
  };

  const mockRoomHealth = [
    { roomId: 1, roomName: 'Living Room', health: 85, issues: [] },
    { roomId: 2, roomName: 'Kitchen', health: 70, issues: ['Plumbing'] }
  ];

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [DashboardService]
    });

    service = TestBed.inject(DashboardService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get cost breakdown', () => {
    service.getCostBreakdown().subscribe(data => {
      expect(data).toEqual(mockCostBreakdown);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/analytics/costs');
    expect(req.request.method).toBe('GET');
    req.flush(mockCostBreakdown);
  });

  it('should get room health', () => {
    service.getRoomHealth().subscribe(data => {
      expect(data).toEqual(mockRoomHealth);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/analytics/roomsHealth');
    expect(req.request.method).toBe('GET');
    req.flush(mockRoomHealth);
  });
});
