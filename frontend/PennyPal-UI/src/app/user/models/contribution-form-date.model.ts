  export interface ContributionFormData {
    goalId : string
    cardId:  string,
    notes: string,
    amount: number|null,
  }

  export interface ContributionResponse{
    contributionId : string;
    amount:number;
    date : Date;
    notes : string;
    coins : number
  }