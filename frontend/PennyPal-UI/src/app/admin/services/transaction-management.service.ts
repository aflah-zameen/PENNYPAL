import { Injectable } from "@angular/core"
import { BehaviorSubject, type Observable, map, combineLatest } from "rxjs"
import type { Transaction, TransactionFilters, PaginationInfo, TransactionStatus } from "../models/transaction-management.model"

@Injectable({
  providedIn: "root",
})
export class TransactionService {
  private transactionsSubject = new BehaviorSubject<Transaction[]>([])
  private filtersSubject = new BehaviorSubject<TransactionFilters>({
    types: [],
    statuses: [],
    dateRange: { from: null, to: null },
    amountRange: { min: null, max: null },
    userEmail: "",
  })
  private paginationSubject = new BehaviorSubject<PaginationInfo>({
    page: 1,
    pageSize: 10,
    total: 0,
    totalPages: 0,
  })

  transactions$ = this.transactionsSubject.asObservable()
  filters$ = this.filtersSubject.asObservable()
  pagination$ = this.paginationSubject.asObservable()

  constructor() {
    this.initializeMockData()
  }

  private initializeMockData() {
    const mockTransactions: Transaction[] = [
      {
        id: "TXN-001",
        type: "Income",
        fromUser: {
          id: "1",
          name: "John Doe",
          email: "john@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        amount: 2500.0,
        fees: 25.0,
        status: "Success",
        createdAt: "2024-01-15T10:30:00Z",
        updatedAt: "2024-01-15T10:30:00Z",
        notes: "Salary payment",
        retryAttempts: 0,
      },
      {
        id: "TXN-002",
        type: "Transfer",
        fromUser: {
          id: "1",
          name: "John Doe",
          email: "john@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        toUser: {
          id: "2",
          name: "Jane Smith",
          email: "jane@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        amount: 500.0,
        fees: 5.0,
        status: "Pending",
        createdAt: "2024-01-14T15:45:00Z",
        updatedAt: "2024-01-14T15:45:00Z",
        notes: "Monthly rent payment",
        retryAttempts: 1,
      },
      {
        id: "TXN-003",
        type: "Expense",
        fromUser: {
          id: "3",
          name: "Bob Wilson",
          email: "bob@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        amount: 150.0,
        status: "Failed",
        createdAt: "2024-01-13T09:20:00Z",
        updatedAt: "2024-01-13T09:25:00Z",
        notes: "Grocery shopping",
        retryAttempts: 3,
        flagReason: "Suspicious activity detected",
      },
      {
        id: "TXN-004",
        type: "Wallet Recharge",
        fromUser: {
          id: "4",
          name: "Alice Brown",
          email: "alice@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        amount: 1000.0,
        fees: 10.0,
        status: "Success",
        createdAt: "2024-01-12T14:10:00Z",
        updatedAt: "2024-01-12T14:10:00Z",
        notes: "Wallet top-up",
        retryAttempts: 0,
      },
      {
        id: "TXN-005",
        type: "Lending",
        fromUser: {
          id: "5",
          name: "Charlie Davis",
          email: "charlie@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        toUser: {
          id: "6",
          name: "Diana Evans",
          email: "diana@example.com",
          avatar: "/placeholder.svg?height=40&width=40",
        },
        amount: 5000.0,
        fees: 50.0,
        status: "Reversed",
        createdAt: "2024-01-11T11:30:00Z",
        updatedAt: "2024-01-11T16:45:00Z",
        notes: "Personal loan",
        retryAttempts: 0,
        flagReason: "Fraudulent transaction",
      },
    ]

    this.transactionsSubject.next(mockTransactions)
    this.updatePagination(mockTransactions.length)
  }

  getFilteredTransactions(): Observable<Transaction[]> {
    return combineLatest([this.transactions$, this.filters$]).pipe(
      map(([transactions, filters]) => {
        return transactions.filter((transaction) => {
          // Type filter
          if (filters.types.length > 0 && !filters.types.includes(transaction.type)) {
            return false
          }

          // Status filter
          if (filters.statuses.length > 0 && !filters.statuses.includes(transaction.status)) {
            return false
          }

          // Date range filter
          if (filters.dateRange.from && new Date(transaction.createdAt) < new Date(filters.dateRange.from)) {
            return false
          }
          if (filters.dateRange.to && new Date(transaction.createdAt) > new Date(filters.dateRange.to)) {
            return false
          }

          // Amount range filter
          if (filters.amountRange.min !== null && transaction.amount < filters.amountRange.min) {
            return false
          }
          if (filters.amountRange.max !== null && transaction.amount > filters.amountRange.max) {
            return false
          }

          // User email filter
          if (
            filters.userEmail &&
            !transaction.fromUser.email.toLowerCase().includes(filters.userEmail.toLowerCase()) &&
            (!transaction.toUser || !transaction.toUser.email.toLowerCase().includes(filters.userEmail.toLowerCase()))
          ) {
            return false
          }

          return true
        })
      }),
    )
  }

  getFlaggedTransactions(): Observable<Transaction[]> {
    return this.transactions$.pipe(map((transactions) => transactions.filter((t) => t.flagReason)))
  }

  updateFilters(filters: Partial<TransactionFilters>) {
    const currentFilters = this.filtersSubject.value
    this.filtersSubject.next({ ...currentFilters, ...filters })
  }

  updatePagination(total: number) {
    const current = this.paginationSubject.value
    this.paginationSubject.next({
      ...current,
      total,
      totalPages: Math.ceil(total / current.pageSize),
    })
  }

  reverseTransaction(transactionId: string): Observable<boolean> {
    const transactions = this.transactionsSubject.value
    const updatedTransactions = transactions.map((t) =>
      t.id === transactionId ? { ...t, status: "Reversed" as TransactionStatus } : t,
    )
    this.transactionsSubject.next(updatedTransactions)
    return new BehaviorSubject(true).asObservable()
  }

  retryTransaction(transactionId: string): Observable<boolean> {
    const transactions = this.transactionsSubject.value
    const updatedTransactions = transactions.map((t) =>
      t.id === transactionId
        ? {
            ...t,
            status: "Pending" as TransactionStatus,
            retryAttempts: (t.retryAttempts || 0) + 1,
          }
        : t,
    )
    this.transactionsSubject.next(updatedTransactions)
    return new BehaviorSubject(true).asObservable()
  }

  markAsSuspicious(transactionId: string): Observable<boolean> {
    const transactions = this.transactionsSubject.value
    const updatedTransactions = transactions.map((t) =>
      t.id === transactionId
        ? {
            ...t,
            flagReason: "Marked as suspicious by admin",
          }
        : t,
    )
    this.transactionsSubject.next(updatedTransactions)
    return new BehaviorSubject(true).asObservable()
  }

  addAdminNotes(transactionId: string, notes: string): Observable<boolean> {
    const transactions = this.transactionsSubject.value
    const updatedTransactions = transactions.map((t) => (t.id === transactionId ? { ...t, adminNotes: notes } : t))
    this.transactionsSubject.next(updatedTransactions)
    return new BehaviorSubject(true).asObservable()
  }

  formatCurrency(amount: number): string {
    return new Intl.NumberFormat("en-US", {
      style: "currency",
      currency: "USD",
    }).format(amount)
  }

  formatDateTime(dateString: string): string {
    return new Intl.DateTimeFormat("en-US", {
      year: "numeric",
      month: "short",
      day: "numeric",
      hour: "2-digit",
      minute: "2-digit",
    }).format(new Date(dateString))
  }
}
