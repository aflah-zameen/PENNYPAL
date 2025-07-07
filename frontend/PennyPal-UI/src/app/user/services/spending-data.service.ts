import { Injectable } from "@angular/core"
import type {
  Transaction,
  SpendingCategory,
  BankCard,
  SpendingSummary,
  CategorySpending,
} from "../models/spend-activity"

@Injectable({
  providedIn: "root",
})
export class SpendingDataService {
  private readonly STORAGE_KEY = "spending_data"

  private categories: SpendingCategory[] = [
    { id: "entertainment", name: "Entertainment", emoji: "🎬", color: "#8B5CF6" },
    { id: "food", name: "Food & Dining", emoji: "🍕", color: "#EF4444" },
    { id: "shopping", name: "Shopping", emoji: "🛍️", color: "#F59E0B" },
    { id: "transportation", name: "Transportation", emoji: "🚗", color: "#3B82F6" },
    { id: "bills", name: "Bills & Utilities", emoji: "⚡", color: "#10B981" },
    { id: "healthcare", name: "Healthcare", emoji: "🏥", color: "#EC4899" },
    { id: "education", name: "Education", emoji: "📚", color: "#6366F1" },
    { id: "travel", name: "Travel", emoji: "✈️", color: "#14B8A6" },
    { id: "fitness", name: "Fitness", emoji: "💪", color: "#F97316" },
    { id: "other", name: "Other", emoji: "📦", color: "#6B7280" },
  ]

  private bankCards: BankCard[] = [
    { id: "card1", name: "Chase Sapphire", type: "credit", lastFour: "4532", color: "#1E40AF" },
    { id: "card2", name: "Bank of America", type: "debit", lastFour: "8901", color: "#DC2626" },
    { id: "card3", name: "Capital One", type: "credit", lastFour: "2345", color: "#059669" },
    { id: "card4", name: "Wells Fargo", type: "debit", lastFour: "6789", color: "#7C3AED" },
  ]

  generateSampleTransactions(): Transaction[] {
    const transactions: Transaction[] = []
    const merchants = [
      "Netflix",
      "Spotify",
      "Amazon",
      "Starbucks",
      "Uber",
      "Target",
      "Walmart",
      "McDonald's",
      "Shell Gas Station",
      "CVS Pharmacy",
      "Best Buy",
      "Home Depot",
      "Costco",
      "Whole Foods",
      "Apple Store",
      "Nike",
      "Zara",
      "H&M",
      "Sephora",
      "Barnes & Noble",
      "Planet Fitness",
      "Delta Airlines",
      "Airbnb",
      "Booking.com",
      "Uber Eats",
      "DoorDash",
      "Grubhub",
    ]

    for (let i = 1; i <= 150; i++) {
      const category = this.categories[Math.floor(Math.random() * this.categories.length)]
      const card = this.bankCards[Math.floor(Math.random() * this.bankCards.length)]
      const merchant = merchants[Math.floor(Math.random() * merchants.length)]

      // Generate realistic amounts based on category
      let amount = 0
      switch (category.id) {
        case "entertainment":
          amount = Math.random() * 50 + 10
          break
        case "food":
          amount = Math.random() * 80 + 15
          break
        case "shopping":
          amount = Math.random() * 200 + 25
          break
        case "transportation":
          amount = Math.random() * 100 + 20
          break
        case "bills":
          amount = Math.random() * 300 + 50
          break
        case "healthcare":
          amount = Math.random() * 150 + 30
          break
        case "education":
          amount = Math.random() * 500 + 100
          break
        case "travel":
          amount = Math.random() * 800 + 200
          break
        case "fitness":
          amount = Math.random() * 100 + 25
          break
        default:
          amount = Math.random() * 100 + 10
      }

      // Generate date within last 30 days
      const date = new Date()
      date.setDate(date.getDate() - Math.floor(Math.random() * 30))

      transactions.push({
        id: i,
        description: `${merchant} - ${category.name}`,
        amount: Math.round(amount * 100) / 100,
        date: date.toISOString().split("T")[0],
        category,
        type: Math.random() > 0.95 ? "credit" : "debit",
        bankCard: card,
        merchant,
      })
    }

    return transactions.sort((a, b) => new Date(b.date).getTime() - new Date(a.date).getTime())
  }

  getTransactions(): Transaction[] {
    const stored = localStorage.getItem(this.STORAGE_KEY)
    if (stored) {
      return JSON.parse(stored)
    }

    const transactions = this.generateSampleTransactions()
    this.saveTransactions(transactions)
    return transactions
  }

  saveTransactions(transactions: Transaction[]): void {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(transactions))
  }

  getCategories(): SpendingCategory[] {
    return this.categories
  }

  getBankCards(): BankCard[] {
    return this.bankCards
  }

  calculateSpendingSummary(transactions: Transaction[]): SpendingSummary {
    const currentMonth = new Date().getMonth()
    const currentYear = new Date().getFullYear()

    // Current month transactions
    const currentMonthTransactions = transactions.filter((t) => {
      const date = new Date(t.date)
      return date.getMonth() === currentMonth && date.getFullYear() === currentYear && t.type === "debit"
    })

    // Previous month transactions
    const prevMonth = currentMonth === 0 ? 11 : currentMonth - 1
    const prevYear = currentMonth === 0 ? currentYear - 1 : currentYear
    const previousMonthTransactions = transactions.filter((t) => {
      const date = new Date(t.date)
      return date.getMonth() === prevMonth && date.getFullYear() === prevYear && t.type === "debit"
    })

    const totalSpend = currentMonthTransactions.reduce((sum, t) => sum + t.amount, 0)
    const previousSpend = previousMonthTransactions.reduce((sum, t) => sum + t.amount, 0)

    // Calculate category spending
    const categorySpending = this.calculateCategorySpending(currentMonthTransactions)
    const topCategory = categorySpending.reduce(
      (max, cat) => (cat.amount > max.amount ? cat : max),
      categorySpending[0],
    )

    // Calculate most used card
    const cardUsage = new Map<string, { card: BankCard; amount: number; count: number }>()
    currentMonthTransactions.forEach((t) => {
      if (t.type === "debit") {
        const existing = cardUsage.get(t.bankCard.id)
        if (existing) {
          existing.amount += t.amount
          existing.count++
        } else {
          cardUsage.set(t.bankCard.id, { card: t.bankCard, amount: t.amount, count: 1 })
        }
      }
    })

    const mostUsedCard = Array.from(cardUsage.values()).reduce(
      (max, card) => (card.amount > max.amount ? card : max),
      Array.from(cardUsage.values())[0],
    )

    // Calculate monthly comparison
    const percentage = previousSpend > 0 ? ((totalSpend - previousSpend) / previousSpend) * 100 : 0
    const trend = totalSpend > previousSpend ? "increase" : "decrease"

    return {
      totalSpend,
      topCategory,
      mostUsedCard: { ...mostUsedCard.card, amount: mostUsedCard.amount },
      monthlyComparison: {
        currentMonth: totalSpend,
        previousMonth: previousSpend,
        percentage: Math.abs(percentage),
        trend,
      },
    }
  }

  calculateCategorySpending(transactions: Transaction[]): CategorySpending[] {
    const debitTransactions = transactions.filter((t) => t.type === "debit")
    const totalSpend = debitTransactions.reduce((sum, t) => sum + t.amount, 0)

    const categoryMap = new Map<string, { amount: number; count: number }>()

    debitTransactions.forEach((t) => {
      const existing = categoryMap.get(t.category.id)
      if (existing) {
        existing.amount += t.amount
        existing.count++
      } else {
        categoryMap.set(t.category.id, { amount: t.amount, count: 1 })
      }
    })

    return this.categories
      .map((category) => {
        const data = categoryMap.get(category.id) || { amount: 0, count: 0 }
        return {
          category,
          amount: data.amount,
          percentage: totalSpend > 0 ? (data.amount / totalSpend) * 100 : 0,
          transactionCount: data.count,
        }
      })
      .filter((cs) => cs.amount > 0)
      .sort((a, b) => b.amount - a.amount)
  }
}
