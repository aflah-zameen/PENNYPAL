# PENNYPAL

**PennyPal** is a personal finance management platform built to help users efficiently track income, expenses, and money lending activities. It targets individuals—especially students and professionals—who face challenges in managing and monitoring their financial transactions using traditional or manual methods.

## 🚀 Key Features

* **Income & Expense Tracking:** Easily record, categorize, and monitor daily transactions.
* **Recurring Transactions:** Support for both automatic and manual recurring entries.
* **Savings Goals:** Set and track personal saving targets.
* **Money Transfers:** Send and receive money between users securely.
* **Messaging System:** In-app chat powered by WebSocket for real-time communication.
* **Notifications & Reminders:** Stay updated with alerts for due payments, income reminders, and lending follow-ups.
* **Analytics & Reports:** Gain insights into spending patterns and savings trends.
* **Offers & Rewards:** Engage users through referral programs and financial offers.

## 🧩 Architecture

The application follows the **Clean Architecture** pattern to ensure high modularity, scalability, and maintainability.

* **Domain Layer:** Contains core business logic and entities.
* **Application Layer:** Handles use cases and orchestrates workflows.
* **Infrastructure Layer:** Implements persistence, Redis caching, and external integrations.
* **Interface Layer:** Exposes REST APIs and WebSocket endpoints.

## 🛠️ Tech Stack

* **Backend:** Spring Boot, Clean Architecture
* **Database:** PostgreSQL
* **Cache:** Redis
* **Frontend:** Angular (Modern Light UI)
* **Real-time Communication:** WebSocket
* **Authentication:** JWT with HttpOnly Cookies
* **Deployment:** AWS, Docker, CI/CD pipeline

## 🎯 Goal

PennyPal aims to eliminate manual and error-prone money tracking habits by offering an intelligent and connected digital solution that makes financial management simple, insightful, and reliable.
