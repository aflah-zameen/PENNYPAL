export interface IncomeSummary {
    totalIncomeSummary : TotalIncomeSummary,
    pendingIncomeSummary : PendingIncomeSummary,
    activeRecurringIncome : ActiveRecurringIncome
}

export interface TotalIncomeSummary{
    totalAmount : number | null,
    progressValue : number | null
}

export interface PendingIncomeSummary{
    totalAmount : number | null;
    pendingIncomes : number | null;
}

export interface ActiveRecurringIncome{
    count : number | null;
}

export interface AllPendingIncomesSummary{
    totalAmount : number;
    totalCount : number;
    pendingIncomeList  : PendingIncomesModel[]
}

export interface PendingIncomesModel{
    incomeId : number;
    title :string;
    amount : number;
    incomeDate : string;
    category : {
        name : string,
        icon : string,
        color : string
    }
}
