# Data Processing Pipeline

A Java application that reads a CSV file of financial transactions, processes them using the Stream API, and generates formatted summary reports. Built to practise functional programming in Java — Streams, Lambdas, and method references.

---

## What It Does

- Reads transaction data from a CSV file
- Processes data using Java Stream pipelines
- Generates four formatted reports:
  - Financial Summary (credits, debits, net balance)
  - Spending Breakdown by Category
  - Top N Expenses
  - Monthly Breakdown

---

## Concepts Covered

| Concept | Where Used |
|---|---|
| Stream API | All processing methods in `TransactionProcessor` |
| Lambda Expressions | Filtering, mapping, and grouping transactions |
| Method References | `Transaction::getAmount`, `Transaction::getCategory` |
| `Collectors.groupingBy` | Grouping by category and by month |
| `Collectors.summingDouble` | Summing amounts per category |
| `Optional` | `getLargestTransaction()` return type |
| `mapToDouble().sum()` | Summing transaction amounts |
| Enum | `TransactionType` (CREDIT / DEBIT) |
| `java.time.LocalDate` | Parsing and comparing transaction dates |
| File I/O | Reading CSV with `Files.readAllLines` |
| `String.format` | Aligned, formatted console output |

---

## Project Structure

```
src/
└── pipeline/
    ├── model/
    │   ├── Transaction.java
    │   └── TransactionType.java
    ├── processor/
    │   └── TransactionProcessor.java
    ├── report/
    │   └── ReportGenerator.java
    └── Main.java

resources/
    └── transactions.csv
```

---

## Architecture

```
Main.java               →  wires processor and report generator
ReportGenerator         →  formats and prints reports
TransactionProcessor    →  all Stream processing logic
Transaction             →  immutable data model
transactions.csv        →  raw input data
```

---

## Stream Methods in TransactionProcessor

| Method | Stream Operations Used |
|---|---|
| `getDebitTransactions()` | `filter`, `collect` |
| `getTotalByType()` | `filter`, `mapToDouble`, `sum` |
| `groupByCategory()` | `groupingBy` |
| `getTopExpenses(n)` | `filter`, `sorted`, `limit`, `toList` |
| `getTotalSpendByCategory()` | `groupingBy`, `summingDouble` |
| `getLargestTransaction()` | `max`, `Optional` |
| `groupByMonth()` | `groupingBy` with `LocalDate::getMonth` |

---

## Sample Output

```
====================
  FINANCIAL SUMMARY
====================
Total Credits:  Rs. 23000.00
Total Debits:   Rs. 5150.00
Net Balance:    Rs. 17850.00
Total Transactions: 20

====================
   SPENDING BY CATEGORY
====================
FOOD            Rs.  1420.00 (6 transactions)
RENT            Rs.  2400.00 (2 transactions)
TRANSPORT       Rs.   420.00 (4 transactions)
ENTERTAINMENT   Rs.   910.00 (3 transactions)
SALARY          Rs. 23000.00 (5 transactions)

====================
   TOP 5 EXPENSES
====================
1. Monthly rent           Rs.  1200.00 [RENT]
2. Monthly rent           Rs.  1200.00 [RENT]
3. Concert tickets        Rs.   500.00 [ENTERTAINMENT]
4. Weekly groceries       Rs.   450.00 [FOOD]
5. Gaming subscription    Rs.   350.00 [ENTERTAINMENT]

====================
   MONTHLY BREAKDOWN
====================
JANUARY:
  Credits: Rs. 16000.00
  Debits:  Rs. 2425.00

FEBRUARY:
  Credits: Rs. 7000.00
  Debits:  Rs. 2725.00
```

---

## How to Run

1. Clone the repository
2. Open in IntelliJ IDEA
3. Ensure JDK 17+ is configured
4. Verify `resources/transactions.csv` is at the project root
5. Set Working Directory to `$ProjectFileDir$` in Run Configurations
6. Run `Main.java`

---

## Prerequisites

- JDK 17+
- IntelliJ IDEA (recommended)
- `resources/transactions.csv` at project root level
