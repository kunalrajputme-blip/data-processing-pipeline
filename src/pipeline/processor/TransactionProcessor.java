package pipeline.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import pipeline.model.Transaction;
import pipeline.model.TransactionType;

public class TransactionProcessor {
	private final List<Transaction> transactions;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public TransactionProcessor(String filePath) {
		this.transactions = loadFromCsv(filePath);
	}

	public List<Transaction> loadFromCsv(String filePath) {
		try {
			return Files.readAllLines(Path.of(filePath)).stream().skip(1).map((line) -> {
				String[] parts = line.split(",");
				int id = Integer.parseInt(parts[0]);
				LocalDate date = LocalDate.parse(parts[1], formatter);
				double amount = Double.parseDouble(parts[2]);
				String category = parts[3];
				String description = parts[4];
				TransactionType type = TransactionType.valueOf(parts[5].toUpperCase());
				return new Transaction(id, date, amount, category, description, type);
			}).collect(Collectors.toList());
		} catch (IOException e) {
			throw new RuntimeException("Error reading CSV file: " + filePath, e);
		}

	}
	
	public List<Transaction> getDebitTransactions(){
		return transactions.stream().filter((t) -> t.getType() == TransactionType.DEBIT).collect(Collectors.toList());
	}
	
	public double getTotalByType(TransactionType type) {
		return transactions.stream().filter((t) -> t.getType() == type).map((t) -> t.getAmount()).reduce(0.0, Double::sum);
	}
	
	public Map<String, List<Transaction>> groupByCategory() {
		return transactions.stream().collect(Collectors.groupingBy(Transaction::getCategory));
	}
	
	public List<Transaction> getTopExpenses(int n) {
		return transactions.stream().filter((t) -> t.getType() == TransactionType.DEBIT).
				sorted(Comparator.comparing(Transaction::getAmount).reversed()).limit(n).toList(); // Shortcut for collect(Collectors.toList())
	}
	
	public Map<String, Double> getTotalSpendByCategory(){
		return transactions.stream().collect(Collectors.
				groupingBy(Transaction::getCategory, Collectors.summingDouble(Transaction::getAmount)));
	}
	
	public Optional<Transaction> getLargestTransaction() {
		return transactions.stream().max(Comparator.comparing(Transaction::getAmount));
	}
	
	public int TransactionsCount() {
		return transactions.size();
	}
	
	public Map<Month, List<Transaction>> groupByMonth() {
		return transactions.stream().collect(Collectors.groupingBy((t) -> t.getDate().getMonth()));
	}
}
