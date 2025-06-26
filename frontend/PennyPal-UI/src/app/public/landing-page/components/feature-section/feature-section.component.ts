import { Component, Input } from '@angular/core';
import { FeaturesGridSectionComponent } from "./features-grid-section/features-grid-section.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-feature-section',
  imports: [FeaturesGridSectionComponent,CommonModule],
  templateUrl: './feature-section.component.html',
  styleUrl: './feature-section.component.css'
})
export class FeatureSectionComponent {

}
