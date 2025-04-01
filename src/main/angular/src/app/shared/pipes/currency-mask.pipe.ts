import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'currencyMask'
})
export class CurrencyMaskPipe implements PipeTransform {


  /**
   * Transforms a number into a formatted currency string.
   *
   * @param value - The number to be transformed.
   * @param showChange - Whether to show the change (decimal part) in the formatted string. Default is false.
   * @param currencySymbol - The currency symbol to use. Default is 'zł'.
   * @param symbolPosition - The position of the currency symbol ('before' or 'after'). Default is 'after'.
   * @returns The formatted currency string.
   */
  transform(value: number, showChange: boolean = false, currencySymbol: string = 'zł', symbolPosition: 'before' | 'after' = 'after'): string {
    if (value == null) {
      return '';
    }

    const formattedValue = this.formatChange(value, showChange);

    return this.formatCurrency(formattedValue, currencySymbol, symbolPosition);
  }

  /**
   * Formats the value based on whether to show the change.
   *
   * @param value - The number to be formatted.
   * @param showChange - Whether to show the change (decimal part) in the formatted string.
   * @returns The formatted value as a string.
   */
  private formatChange(value: number, showChange: boolean): string {
    return showChange
      ? value.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$& ')
      : Math.floor(value).toString().replace(/\d(?=(\d{3})+$)/g, '$& ');
  }

  /**
   * Formats the currency string with the currency symbol in the specified position.
   *
   * @param value - The formatted value as a string.
   * @param currencySymbol - The currency symbol to use.
   * @param symbolPosition - The position of the currency symbol ('before' or 'after').
   * @returns The formatted currency string.
   */
  private formatCurrency(value: string, currencySymbol: string, symbolPosition: 'before' | 'after'): string {
    return symbolPosition === 'before'
      ? currencySymbol + value
      : value + ' ' + currencySymbol;
  }

}
