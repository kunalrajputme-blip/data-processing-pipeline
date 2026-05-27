package pipeline;

import pipeline.processor.TransactionProcessor;
import pipeline.report.ReportGenerator;

public class Main {
	public static void main(String[] args) {
		TransactionProcessor processor = new TransactionProcessor("resources/transactions.csv");
		ReportGenerator report = new ReportGenerator(processor);
		report.printSummaryReport();
		report.printCategoryBreakDown();
		report.printTopExpenses(5);
		report.printMonthlyBreakdown();
	}
}
