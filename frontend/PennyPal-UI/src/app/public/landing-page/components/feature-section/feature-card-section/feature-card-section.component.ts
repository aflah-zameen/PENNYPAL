import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-feature-card-section',
  imports: [],
  templateUrl: './feature-card-section.component.html',
  styleUrl: './feature-card-section.component.css'
})
export class FeatureCardSectionComponent {
  @Input() imageUrl!: string;
  @Input() title!: string;
  @Input() description!: string;
}
