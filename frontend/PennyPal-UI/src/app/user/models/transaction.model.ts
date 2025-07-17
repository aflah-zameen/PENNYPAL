export interface Transaction{
    id : number;
    title :string;
    amount : number;
    transactionDate : Date;
    category : {
        name : string,
        icon : string,
        color : string
    }

}