export interface Transaction{
    id : string;
    title :string;
    amount : number;
    transactionDate : string;
    transactionType : string; 
    description : string;
    isFromRecurring : boolean;
    recurringTransactionId ?: string;
    transferToUserId ?: string;
    transferFromUserId  ?: string;
    category : {
        id : string,
        name : string,
        icon : string,
        color : string
    }
    card : {
        gradient : string,
        id : string;
        cardNumber : string;
        cardType : string;   
        name : string;
        lastFour : string;
    }
    createdAt : Date
}

export interface TransactionRequest{
    title : string;
    amount : number|null;
    transactionDate : string;
    transactionType : string;
    description : string;
    transferToUserId ?: string;
    transferFromUserId  ?: string;
    categoryId : string|null;
    paymentMethod : string;
    cardId ?: string|null;
}

export interface TransactionSummaryResponseDTO{
    totalTransactionSummary:{
        totalAmount : number;
        progressValue : number;
    },
    pendingTransactionSummary:{
        totalAmount : number;
        pendingTransactions : number;
    },
    activeRecurringTransaction:{
        count : number;
    }
}

export interface RecurringTransactionResponse{
    recurringId : string;
    card : {
        cardId : string;
        cardNumber : string;
        cardType : string;   
    };
    category:{
         name : string,
        icon : string,
        color : string
    };
    transactionType : TransactionType;
    title : string;
    description : string;
    amount : number;
    frequency : string;
    startDate : string;
    endDate : string;
    lastGeneratedDate : string;
    active : boolean;
    createdAt : string;
}

export interface RecurringTransactionSummary{
    recurringTransactions: RecurringTransactionResponse[];
    count : number;
    totalAmount : number;
}   

export interface RecurringTransactionRequest {
    amount: number|null;
    title: string;
    transactionType: TransactionType;
    cardId : string|null;
    categoryId: string|null;
    frequency: string;
    startDate: string;
    endDate: string;
    description?: string;
}

export type TransactionType = 'INCOME' | 'EXPENSE';

export interface PendingTransaction{
    transactionId: string;
    transactionDate: string;
    amount: number;
    title: string;
    description?: string;
    category: {
        name: string;
        icon: string;
        color: string;
    };
    card: {
        cardId: string;
        cardNumber: string;
        cardType: string;   
    };
}

export interface PendingTransactionTotalSummary{
    totalAmount: number;
    count: number;
    pendingTransactions: PendingTransaction[];
}

export interface PaymentMethod{
    id: string;
    name: string;
    cardNumber: string;
}
