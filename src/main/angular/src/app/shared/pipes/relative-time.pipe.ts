import { Pipe, PipeTransform } from '@angular/core';
import {formatDistanceToNowStrict, parseISO} from "date-fns";

/**
 * Pipe that transforms a date (in ISO string format) into a human-readable relative time string.
 * For example, it will convert "2024-11-09T11:31:35.1407168" to "2 days ago".
 *
 * This pipe utilizes the `formatDistanceToNowStrict` function from the `date-fns` library to
 * calculate the difference between the current date and the given date, providing a relative
 * timestamp with an optional suffix (e.g., "ago").
 *
 * @example
 * <p>{{ '2024-11-09T11:31:35.1407168' | relativeTime }}</p>
 * <!-- Output: "2 days ago" -->
 *
 * @usageNotes
 * This pipe is intended to be used in Angular templates for formatting ISO string dates
 * into readable time differences relative to the current date and time.
 *
 * @public
 */
@Pipe({
  name: 'relativeTime'
})
export class RelativeTimePipe implements PipeTransform {
  transform(value: string): string {
    if (!value) return '';
    return formatDistanceToNowStrict(parseISO(value), {addSuffix: true});
  }
}
