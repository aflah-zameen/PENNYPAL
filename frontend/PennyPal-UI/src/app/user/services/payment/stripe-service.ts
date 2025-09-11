import { Injectable } from '@angular/core';
import { loadStripe, Stripe } from '@stripe/stripe-js';
import { environment } from '../../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class StripeService {
  readonly apiURL = environment.apiBaseUrl+"/api/user/plans";
  private stripePromise = loadStripe(environment.stripPublicKey); // Your public key

  constructor(private http : HttpClient){}

  async redirectToCheckout(sessionId: string): Promise<void> {
    const stripe = await this.stripePromise;
    if (!stripe) throw new Error('Stripe failed to load');
    await stripe.redirectToCheckout({ sessionId });
  }


  getCheckoutSession(planId : string,price : number){
    return this.http.post<{ sessionId: string }>(`${this.apiURL}/checkout-session`, {
    price: price,
    planId : planId
   });
  }
  
}
