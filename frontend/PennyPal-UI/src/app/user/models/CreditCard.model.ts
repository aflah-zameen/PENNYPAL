export interface CreditCard {
  id: string
  name: string
  number: string
  holder: string
  expiry: string
  type: "visa" | "mastercard"
  gradient: string
  balance: number
  income: number
  expense: number
  active : boolean
}

export interface SpendData {
  label: string[];     
  amount: number[];
}

export interface Transaction {
  id: string
  name: string
  category: string
  date: string
  amount: number
  type: "income" | "expense" | "transfer"
}

const gradientPresets = [
  "bg-gradient-to-br from-purple-500 via-pink-500 to-orange-400",
  "bg-gradient-to-br from-blue-500 via-purple-500 to-pink-400",
  "bg-gradient-to-br from-green-500 via-teal-400 to-blue-500",
  "bg-gradient-to-br from-yellow-400 via-orange-500 to-red-500",
  "bg-gradient-to-br from-indigo-500 via-purple-500 to-pink-500",
  "bg-gradient-to-br from-cyan-500 via-sky-500 to-indigo-500",
  "bg-gradient-to-br from-fuchsia-500 via-purple-400 to-rose-400",
  "bg-gradient-to-br from-lime-400 via-green-500 to-emerald-500",
];

export interface CreditCardForm {
  number: string;
  name : string;
  holder: string;
  expiry: string;
  type: "visa" | "mastercard"|null;
  balance: number | null;
  gradient : string;
  pin : string;
  notes?: string;
}