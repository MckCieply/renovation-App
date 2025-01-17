import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  hrefCore = "https://cdn.jsdelivr.net/npm/@angular/material/prebuilt-themes/"
  constructor() {
  }

  /**
   * Set the stylesheet with the specified key.
   */
  setStyle(key: string, href: string) {
    this.getLinkElementForKey(key).setAttribute( 'href', `${this.hrefCore}${href}`);
  }

  /**
   * Remove the stylesheet with the specified key.
   */
  removeStyle(key: string) {
    const existingLinkElement = this.getExistingLinkElementByKey(key);
    if (existingLinkElement) {
      document.head.removeChild(existingLinkElement);
    }
  }


  getLinkElementForKey(key: string) {
    return this.getExistingLinkElementByKey(key) || this.createLinkElementWithKey(key);
  }

  getExistingLinkElementByKey(key: string) {
    return document.head.querySelector(`link[rel="stylesheet"].${key}`);
  }

  createLinkElementWithKey(key: string) {
    const linkEl = document.createElement('link');
    linkEl.setAttribute('rel', 'stylesheet');
    linkEl.classList.add(key);
    document.head.appendChild(linkEl);
    return linkEl;
  }

}

