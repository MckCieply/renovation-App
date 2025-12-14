import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-custom-legend',
  templateUrl: './custom-legend.component.html',
  styleUrl: './custom-legend.component.scss'
})
export class CustomLegendComponent {
// Input 1: The data (e.g., breakdownCost array)
  @Input() data: any[] = [];

  // Input 2: The Color Scheme object from ngx-charts
  @Input() colors: any;

  // Helper to map data to a clean {label, color} array
  get legendItems() {
    if (!this.data || !this.colors) return [];

    return this.data.map((item, index) => {
      // Handle both "Simple" arrays and "Series" arrays (grouped charts)
      const label = item.name;
      // If domain is an array, pick color by index safely
      const color = this.colors.domain[index % this.colors.domain.length];
      return { label, color };
    });
  }
}
