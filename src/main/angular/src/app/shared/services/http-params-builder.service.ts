import { Injectable } from '@angular/core';
import {HttpParams} from "@angular/common/http";

/**
 * Service for building HttpParams from a given filter object.
 * This service helps in creating query parameters for HTTP requests by filtering
 * out null, undefined or unset values, ensuring that only valid parameters are sent.
 */
@Injectable({
  providedIn: 'root'
})
export class HttpParamsBuilderService {

  buildHttpParams(filter: any): HttpParams {
    let params = new HttpParams();

    Object.keys(filter).forEach((key) => {
      const value = filter[key];
      if (value !== null && value !== undefined && value !== '') {
        params = params.set(key, value);
      }
    });

    return params;
  }
}
