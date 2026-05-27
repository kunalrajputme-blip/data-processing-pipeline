package pipeline.report;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import pipeline.model.Transaction;
import pipeline.model.TransactionType;
import pipeline.processor.TransactionProcessor;

public class ReportGenerator {
	private final TransactionProcessor processor;

	public ReportGenerator(TransactionProcessor processor) {
		this.processor = processor;
	}

	public void printSummaryReport() {
		double credit = processor.getTotalByType(TransactionType.CREDIT);
		double debit = processor.getTotalByType(TransactionType.DEBIT);
		double balance = credit - debit;
		System.out.println("====================\n  FINANCIAL SUMMARY\n====================");
		System.out.println("Total Credits:  "  + String.format("Rs. %.2f", credit));
		System.out.println("Total Debits:  "  + String.format("Rs. %.2f", debit));
		System.out.println("Net Balance:  "  + String.format("Rs. %.2f", balance));
		System.out.println("Total Transactions:  " + processor.TransactionsCount());
	}

	public void printCategoryBreakDown() {
		System.out.println("\n\n====================\n   SPENDING BY CATEGORY   \n====================");
		Map<String, List<Transaction>> grouped = processor.groupByCategory();
		Map<String, Double> totals = processor.getTotalSpendByCategory();
		for (Map.Entry<String, List<Transaction>> entry : grouped.entrySet()) {
			String category = entry.getKey();
			int count = entry.getValue().size();
			double total = totals.get(category);
			System.out.println(String.format("%-15s Rs. %8.2f (%d transactions)", category, total, count));
		}

	}

	public void printTopExpenses(int size) {
		System.out.println("\n\n====================\n   TOP EXPENSES   \n====================");
		List<Transaction> topExpenses = processor.getTopExpenses(size);
		for (int i = 0; i < topExpenses.size(); i++) {
			Transaction t = topExpenses.get(i);
			System.out.println(String.format("%d. %-25s Rs. %8.2f [%s]", i + 1, t.getDescription(), t.getAmount(),
					t.getCategory()));
		}

	}

	public void printMonthlyBreakdown() {
		System.out.println("\n\n====================\n   MONTHLY BREAKDOWN   \n====================");
		Map<Month, List<Transaction>> byMonth = processor.groupByMonth();
		for (Map.Entry<Month, List<Transaction>> entry : byMonth.entrySet()) {
			Month month = entry.getKey();
			List<Transaction> monthTransactions = entry.getValue();
			double credits = monthTransactions.stream().filter((t) -> t.getType() == TransactionType.CREDIT)
					.mapToDouble(Transaction::getAmount).sum();

			double debits = monthTransactions.stream().filter((t) -> t.getType() == TransactionType.DEBIT)
					.mapToDouble(Transaction::getAmount).sum();

			String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toUpperCase();

			System.out.println(String.format("%s:\nCredits: Rs. %.2f\nDebits: Rs. %.2f\n", monthName, credits, debits));

		}
	}
}
