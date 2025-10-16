import { Component, ElementRef, Input, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FeaturesGridSectionComponent } from "./features-grid-section/features-grid-section.component";
import { CommonModule } from '@angular/common';
type Feature ={
  icon : string,
  title : string,
  desc :string
}
@Component({
  selector: 'app-feature-section',
  imports: [CommonModule],
  templateUrl: './feature-section.component.html',
  styleUrl: './feature-section.component.css'
})
export class FeatureSectionComponent {
  @ViewChild('section', { static: true }) section!: ElementRef<HTMLElement>;
  
  isAnimated = false;
  private observer!: IntersectionObserver;

  features: Feature[] = [
    { icon: "💳", title: "Expense Tracking", desc: "Monitor and categorize your daily spending with ease." },
    { icon: "🎯", title: "Goal Setting", desc: "Set savings goals and track your journey toward financial freedom." },
    { icon: "🤝", title: "Loan Tracking", desc: "Record money you lend and get reminders to follow up." },
    { icon: "🔔", title: "Smart Alerts", desc: "Get intelligent reminders for bills, loans, and budgets." },
    { icon: "🔒", title: "Secure & Private", desc: "Protected with encryption, secure login (OTP & PIN)." },
    { icon: "📊", title: "Visual Insights", desc: "Understand spending patterns with beautiful graphs." },
  ];

  ngOnInit(): void {
    // Set up the Intersection Observer
    this.observer = new IntersectionObserver(entries => {
      entries.forEach(entry => {
        if (entry.isIntersecting) {
          this.isAnimated = true; // Trigger the CSS animation
          this.observer.unobserve(entry.target); // Stop observing once animated
        }
      });
    }, {
      rootMargin: '0px',
      threshold: 0.2 // Trigger when 20% of the section is visible
    });

    // Start observing the section element
    if (this.section) {
      this.observer.observe(this.section.nativeElement);
    }
  }

  ngOnDestroy(): void {
    // Clean up the observer when the component is destroyed
    if (this.observer) {
      this.observer.disconnect();
    }
  }
}
