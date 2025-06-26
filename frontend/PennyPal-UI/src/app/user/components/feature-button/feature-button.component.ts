import { Component,Input } from '@angular/core';
import { LucideAngularModule } from 'lucide-angular'

@Component({
  selector: 'app-feature-button',
  imports: [LucideAngularModule],
  templateUrl: './feature-button.component.html',
  styleUrl: './feature-button.component.css'
})
export class FeatureButtonComponent {
  @Input() icon: any;
  @Input() buttonText : string='';
}
