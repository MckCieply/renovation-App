import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  hrefCore = "https://cdn.jsdelivr.net/npm/@angular/material/prebuilt-themes/"
  constructor() {
  }

  setStyle(key: string, href: string) {
    this.getLink(key).setAttribute( 'href', `${this.hrefCore}${href}`);
  }

  getLink(key: string) {
    return this.getExistingLink(key) || this.createLink(key);
  }

  getExistingLink(key: string) {
    return document.head.querySelector(`link[rel="stylesheet"].${key}`);
  }

  createLink(key: string) {
    const linkEl = document.createElement('link');
    linkEl.setAttribute('rel', 'stylesheet');
    linkEl.classList.add(key);
    document.head.appendChild(linkEl);
    return linkEl;
  }

}

