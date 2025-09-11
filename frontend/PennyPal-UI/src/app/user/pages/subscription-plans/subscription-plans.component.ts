import { Component, OnInit } from '@angular/core';
import { PurchaseEvent, SubscriptionPlan } from '../../models/subscription-plan-model';
import { Subject, takeUntil } from 'rxjs';
import { SubscriptionService } from '../../services/subscription-service';
import { CommonModule } from '@angular/common';
import { SubscriptionPlansHeaderComponent } from "../../components/subscription/subscription-plans-header/subscription-plans-header.component";
import { SubscriptionPlansGridComponent } from "../../components/subscription/subscription-plans-grid/subscription-plans-grid.component";
import { StripeService } from '../../services/payment/stripe-service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Route, Router, RouterLinkActive } from '@angular/router';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'app-subscription-plans',
  imports: [CommonModule, SubscriptionPlansHeaderComponent, SubscriptionPlansGridComponent],
  templateUrl: './subscription-plans.component.html',
  styleUrl: './subscription-plans.component.css'
})
export class SubscriptionPlansComponent implements OnInit{
  subscriptionPlans : SubscriptionPlan[] = []
  isLoading = true
  purchasingPlanId: string | null = null

  private destroy$ = new Subject<void>()

  constructor(protected subscriptionService: SubscriptionService,
    private paymentService : StripeService,
    private toastr : ToastrService,
    private route : ActivatedRoute,
    private router : Router,
    private spinner : NgxSpinnerService
  ) {}

  ngOnInit(): void {
    this.spinner.show();
    this.subscriptionService.getSubscriptionPlans().subscribe(data => {
      this.subscriptionPlans = data;     
      this.spinner.hide(); 
    })
    this.route.queryParams.subscribe(params => {
      const sessionId = params['session_id'];
      const planId = params['plan_id']
      if (sessionId) {
        this.spinner.show();
        this.subscriptionService.purchasePlan(planId,sessionId).subscribe({
          next: () => {
            this.spinner.hide();
            this.toastr.success('Purchase successful!');
            this.router.navigate(['/user']);
          },
          error: (err) => {
            this.spinner.hide();
            console.error('Activation failed:', err);
            this.toastr.error('Payment succeeded but activation failed. Contact support.');
          }
        });
      }
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next()
    this.destroy$.complete()
  }
  onPlanPurchase(event: PurchaseEvent): void {
    this.purchasingPlanId = event.planId
    this.spinner.show();
    this.paymentService.getCheckoutSession(event.planId,event.plan.amount).subscribe({
    next: async (res) => {
      // Step 2: Redirect to Stripe Checkout
      await this.paymentService.redirectToCheckout(res.sessionId);
    },
    error: (err) => {
      // Step 3: Handle errors gracefully
      this.spinner.hide();
      console.error('Failed to initiate Stripe Checkout:', err);
      this.toastr.error('Unable to start payment. Please try again.');
    }
    });
  }

}
