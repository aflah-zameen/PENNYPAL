import { Injectable } from "@angular/core";
import { environment } from "../../../environments/environment";
import { map, Observable, Subject } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { Contact, PaymentMethod, Transaction } from "../models/money-flow.model";
import { ApiResponse } from "../../models/ApiResponse";

@Injectable({ providedIn: 'root' })
export class ContactManagementService {
private apiURL = `${environment.apiBaseUrl}/api/private/user`
  private reloadSubject = new Subject<void>();
  reload$ = this.reloadSubject.asObservable();
  constructor(private http : HttpClient){}

  getContacts() :Observable<Contact[]> {
    return this.http.get<ApiResponse<Contact[]>>(`${this.apiURL}/contacts`,{withCredentials: true}).pipe(
      map(response => response.data)
    );
  }


  getPaymentMethods(): Observable<PaymentMethod[]> {
    return this.http.get<ApiResponse<PaymentMethod[]>>(`${this.apiURL}/payment-methods`,{withCredentials: true}).pipe(
      map(response => response.data)
    );
  }

  transferMoney(
    recipientId: string,
    amount: number,
    note: string,
    paymentMethodId: string,
    pin: string
  ): Observable<Transaction> {
    const transferRequest = {
      recipientId,
      amount,
      note,
      pin,
      paymentMethodId
    };

    console.log("Transfer Request:", transferRequest);
    

    return this.http.post<ApiResponse<Transaction>>(`${this.apiURL}/transfer`, transferRequest, { withCredentials: true }).pipe(
      map(response => response.data)
    );
  }



}