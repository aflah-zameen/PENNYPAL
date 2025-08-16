import { Component, OnInit } from '@angular/core';
import { CreditCard, CreditCardForm, SpendData, Transaction } from '../../models/CreditCard.model';
import { CardExpenseOverview, ExpenseOverviewComponent, filterValue } from '../../components/expense-overview/expense-overview.component';
import { CreditCardComponent } from "../../components/credit-card/credit-card.component";
import { StatCardComponent } from "../../components/stat-card/stat-card.component";
import { SpendChartComponent } from "../../components/spend-chart/spend-chart.component";
import { TransactionsTableComponent } from "../../components/transactions-table/transactions-table.component";
import { CommonModule } from '@angular/common';
import { AddCardComponentModal} from "../../modals/add-card/add-card.component";
import { AddCardComponentSection } from "../../components/add-card/add-card.component";
import { Observable, of, tap } from 'rxjs';
import { UserCardService } from '../../services/user-card.service';
import { SetCardPinComponent } from "../../modals/set-card-pin/set-card-pin.component";

@Component({
  selector: 'app-card-management',
  imports: [CreditCardComponent, AddCardComponentSection, StatCardComponent, SpendChartComponent, ExpenseOverviewComponent, TransactionsTableComponent, CommonModule, AddCardComponentModal, SetCardPinComponent],
  templateUrl: './card-management.component.html',
  styleUrl: './card-management.component.css'
})
export class CardManagementComponent implements OnInit {
   newCardDetails: Omit<CreditCardForm,'pin'>|null = null; 
   selectedCard: CreditCard | null = null;
   isAddCardModalOpen: boolean = false;
   isSetPinModalOpen: boolean = false;
   creditCards$! : Observable<CreditCard[]|null> ;
   spendData$!: Observable<SpendData | null>;
   expenseCategories$ !: Observable<CardExpenseOverview[]>
   recentTransaction$ !: Observable<Transaction[]>
   recentTransactionSize : number = 6;

   constructor(private cardService: UserCardService) {
  }
  ngOnInit() {
    this.creditCards$ = this.cardService.getCardsSummary();
    // Initialize with the first card if available
    this.creditCards$.subscribe(cards => {
      if (cards && cards.length > 0) {
        this.selectedCard = cards[0];
         this.spendData$ = this.getCardSpendingOverview(this.selectedCard.id,"monthly");
         this.expenseCategories$ = this.getCardExpenseOverview(this.selectedCard.id,{range : 'monthly'});
         this.recentTransaction$ = this.getRecentCardTransaction(this.selectedCard.id);
      }
    });
  }
  // creditCards: CreditCard[] = [
  //   {
  //     id: "1",
  //     number: "3345 6754 8976 2345",
  //     holder: "Afiah Zainesh",
  //     expiry: "02/30",
  //     type: "visa",
  //     gradient: "bg-gradient-to-br from-purple-500 via-pink-500 to-orange-400",
  //     balance: 2400.8,
  //     income: 950.8,
  //     expenses: 300.2,
  //   },
  //   {
  //     id: "2",
  //     number: "3345 6754 8976 2345",
  //     holder: "Afiah Zainesh",
  //     expiry: "02/30",
  //     type: "visa",
  //     gradient: "bg-gradient-to-br from-blue-500 via-purple-500 to-pink-400",
  //     balance: 1850.5,
  //     income: 1200.0,
  //     expenses: 450.75,
  //   },
  //   {
  //     id: "3",
  //     number: "3345 6754 8976 2345",
  //     holder: "Afiah Zainesh",
  //     expiry: "02/30",
  //     type: "visa",
  //     gradient: "bg-gradient-to-br from-purple-600 via-pink-500 to-red-400",
  //     balance: 3200.25,
  //     income: 800.6,
  //     expenses: 275.3,
  //   },
  // ]

  // spendData: SpendData[] = [
  //   { month: "Jan", amount: 900 },
  //   { month: "Feb", amount: 250 },
  //   { month: "Mar", amount: 450 },
  //   { month: "Apr", amount: 800 },
  //   { month: "May", amount: 200 },
  //   { month: "Jun", amount: 300 },
  //   { month: "Jul", amount: 180 },
  //   { month: "Aug", amount: 280 },
  //   { month: "Sep", amount: 220 },
  // ]

  // transactions: Transaction[] = [
  //   { id: "1", name: "Netflix", category: "Entertainment", date: "Today\n12:00PM", amount: -50, type: "expense" },
  //   { id: "2", name: "Amazon", category: "Shopping", date: "12 march 2025\n12:00PM", amount: -150, type: "expense" },
  //   {
  //     id: "3",
  //     name: "Robert Green",
  //     category: "Payment Link",
  //     date: "10 march 2025\n12:00PM",
  //     amount: 1150,
  //     type: "income",
  //   },
  //   { id: "4", name: "Amazon", category: "Shopping", date: "12 march 2025\n12:00PM", amount: -150, type: "expense" },
  // ]

// expenseCategories: CardExpenseOverview[] = [
//   { name: "Transfers", amount: 341.23, color: "#8B5CF6", icon: "transfer", trend: -5},
//   { name: "Shopping", amount: 111.23, color: "#F59E0B", icon: "shopping", trend: 3},
//   { name: "Groceries", amount: 141.23, color: "#F97316", icon: "groceries", trend: -2 },
//   { name: "Health", amount: 631.23, color: "#10B981", icon: "health", trend: 8},
//   { name: "Entertainment", amount: 121.23, color: "#EF4444", icon: "entertainment", trend: -1 },
//   { name: "Others", amount: 91.23, color: "#06B6D4", icon: "others", trend: 0 },
// ];


  onCardSelected(card: CreditCard) {
    this.selectedCard = card
  }

  onCardDetailsUploaded(card: Omit<CreditCardForm, 'pin'>) {
       this.isAddCardModalOpen = false;
       this.newCardDetails = card;
       this.isSetPinModalOpen = true;
    }
  onSetPinCompleted(pin: string) {
    if (!this.newCardDetails) return; 
    const newCard : CreditCardForm = {...this.newCardDetails, pin: pin};
    this.cardService.addCard(newCard).subscribe(card => {
      this.creditCards$ = this.creditCards$.pipe(
        tap(cards => {
          if (cards) {
            this.selectedCard = card;
          }
        })
      );
      this.isSetPinModalOpen = false;
      this.newCardDetails = null; // Reset after adding card
    });
  }

  getCardSpendingOverview(cardId : string,range : string) : Observable<SpendData>{
      return this.cardService.getCardSpendingOverview(cardId,range);
  }

  getCardExpenseOverview(cardId : string, filterValue : filterValue ):Observable<CardExpenseOverview[]>{
      return this.cardService.getCardExpenseOverview(cardId,filterValue);
  }

  onRangeChanged(range : string){
    if(this.selectedCard){
      this.spendData$ = this.getCardSpendingOverview(this.selectedCard.id,range);
    }
  }

  onExpenseRangeChanged(filterValue : filterValue){    
    if(this.selectedCard){
      this.expenseCategories$ = this.getCardExpenseOverview(this.selectedCard.id,filterValue);
    }
  }

  getRecentCardTransaction(cardId : string):Observable<Transaction[]>{
      if(this.selectedCard){
        return this.cardService.getCardRecentTransactions(cardId,this.recentTransactionSize);
      }
      return of([]);
  }
}
