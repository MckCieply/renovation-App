import { Injectable } from '@angular/core';
import {MatSnackBar} from "@angular/material/snack-bar";

export interface NotificationOptions {
  action?: string;
  duration?: number;
  horizontalPosition?: 'start' | 'end' | 'center' | 'left' | 'right';
  verticalPosition?: 'top' | 'bottom';
  panelClass?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) { }

  showSuccess(message: string, options?: NotificationOptions){
    this.snackBar.open(message, options?.action || '', {
      duration: options?.duration || 3000,
      horizontalPosition: options?.horizontalPosition || 'center',
      verticalPosition: options?.verticalPosition || 'top',
      panelClass: options?.panelClass || ['success-snackbar']
    })
  }

  showError(message: string, options?: NotificationOptions){
    this.snackBar.open(message, options?.action || '', {
      duration: options?.duration || 3000,
      horizontalPosition: options?.horizontalPosition || 'center',
      verticalPosition: options?.verticalPosition || 'top',
      panelClass: options?.panelClass || ['error-snackbar']
    })
  }
}
