import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { BudgetService } from './budget.service';
import { NotificationService } from '../shared/services/notification.service';

describe('BudgetService', () => {
  let service: BudgetService;
  let httpMock: HttpTestingController;
  let notificationService: jasmine.SpyObj<NotificationService>;

  const mockBudget = {
    budgetLimit: 50000,
    budgetSpent: 25000,
    budgetAllocated: 30000
  };

  const mockBudgetValidation = {
    budgetLimit: 50000,
    totalRoomBudgets: 30000,
    totalEstimatedCosts: 35000,
    totalPaidCosts: 25000,
    availableBudget: 15000,
    overallocated: false,
    warningMessage: null
  };

  beforeEach(() => {
    const notificationSpy = jasmine.createSpyObj('NotificationService', ['showSuccess']);

    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        BudgetService,
        { provide: NotificationService, useValue: notificationSpy }
      ]
    });

    service = TestBed.inject(BudgetService);
    httpMock = TestBed.inject(HttpTestingController);
    notificationService = TestBed.inject(NotificationService) as jasmine.SpyObj<NotificationService>;
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get budget', () => {
    service.getBudget().subscribe(budget => {
      expect(budget).toEqual(mockBudget);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/budget/budget');
    expect(req.request.method).toBe('GET');
    req.flush(mockBudget);
  });

  it('should update budget and show success notification', () => {
    const updatedBudget = { ...mockBudget, budgetLimit: 60000 };

    service.updateBudget(updatedBudget).subscribe(result => {
      expect(result).toEqual(mockBudgetValidation);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/budget/update');
    expect(req.request.method).toBe('PUT');
    expect(req.request.body).toEqual(updatedBudget);
    req.flush(mockBudgetValidation);

    expect(notificationService.showSuccess).toHaveBeenCalledWith('Budget updated successfully!');
  });

  it('should validate budget', () => {
    service.validateBudget().subscribe(validation => {
      expect(validation).toEqual(mockBudgetValidation);
    });

    const req = httpMock.expectOne('http://localhost:8080/api/budget/validate');
    expect(req.request.method).toBe('GET');
    req.flush(mockBudgetValidation);
  });
});
