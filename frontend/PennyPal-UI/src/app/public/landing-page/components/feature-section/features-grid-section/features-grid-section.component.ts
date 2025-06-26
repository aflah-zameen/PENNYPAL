import { Component } from '@angular/core';
import { FeatureCardSectionComponent } from "../feature-card-section/feature-card-section.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-features-grid-section',
  imports: [FeatureCardSectionComponent,CommonModule],
  templateUrl: './features-grid-section.component.html',
  styleUrl: './features-grid-section.component.css'
})
export class FeaturesGridSectionComponent {
  features: any[] = [
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/3b05d50dd00fe347ecc6804186050151bc6c88a8',
      title: 'Expense Tracking',
      description: 'Monitor and categorize your daily spending with ease.'
    },
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/e6d2703ef16e712ff87210fd1e25f14f0775e561',
      title: 'Goal Setting',
      description: 'Set savings goals and track your journey toward financial freedom.'
    },
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/d7df4d5cfc5cbb05109c71ee7f18821dcebffde8',
      title: 'Loan Tracking',
      description: 'Record money you lend and get reminders to follow up.'
    },
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/e36e467726658e466ed722dca87ab3c94c57b15f',
      title: 'Smart Alerts',
      description: 'Record money you lend and get reminders to follow up.'
    },
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/35e1d31e5e30c725195c6f1d88c41948cbeced27',
      title: 'Secure & Private',
      description: 'Your data is protected with encryption and secure login methods like OTP and PIN.'
    },
    {
      imageUrl: 'https://cdn.builder.io/api/v1/image/assets/TEMP/5d3e04271b7b9612ad255a23a363f6349ea142d7',
      title: 'Visual Insights',
      description: 'Understand spending patterns with simple, beautiful graphs.'
    }
  ];
}
