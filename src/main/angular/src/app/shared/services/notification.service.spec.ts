import { TestBed } from '@angular/core/testing';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { NotificationService, NotificationOptions } from './notification.service';

describe('NotificationService', () => {
  let service: NotificationService;
  let snackBar: jasmine.SpyObj<MatSnackBar>;

  beforeEach(() => {
    const snackBarSpy = jasmine.createSpyObj('MatSnackBar', ['open']);

    TestBed.configureTestingModule({
      imports: [MatSnackBarModule],
      providers: [
        NotificationService,
        { provide: MatSnackBar, useValue: snackBarSpy }
      ]
    });

    service = TestBed.inject(NotificationService);
    snackBar = TestBed.inject(MatSnackBar) as jasmine.SpyObj<MatSnackBar>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should show success notification with default options', () => {
    const message = 'Success message';

    service.showSuccess(message);

    expect(snackBar.open).toHaveBeenCalledWith(message, '', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['success-snackbar']
    });
  });

  it('should show success notification with custom options', () => {
    const message = 'Custom success';
    const options: NotificationOptions = {
      action: 'Close',
      duration: 5000,
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      panelClass: ['custom-class']
    };

    service.showSuccess(message, options);

    expect(snackBar.open).toHaveBeenCalledWith(message, 'Close', {
      duration: 5000,
      horizontalPosition: 'right',
      verticalPosition: 'bottom',
      panelClass: ['custom-class']
    });
  });

  it('should show error notification with default options', () => {
    const message = 'Error message';

    service.showError(message);

    expect(snackBar.open).toHaveBeenCalledWith(message, '', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: ['error-snackbar']
    });
  });

  it('should show error notification with custom options', () => {
    const message = 'Custom error';
    const options: NotificationOptions = {
      action: 'Retry',
      duration: 10000,
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      panelClass: ['error-custom']
    };

    service.showError(message, options);

    expect(snackBar.open).toHaveBeenCalledWith(message, 'Retry', {
      duration: 10000,
      horizontalPosition: 'left',
      verticalPosition: 'bottom',
      panelClass: ['error-custom']
    });
  });
});
