package pipeline.model;

import java.time.LocalDate;

public class Transaction {
	private int id;
	private LocalDate date;
	private double amount;
	private String category;
	private String description;
	private TransactionType type;
	
	public Transaction(int id, LocalDate date, double amount, String category, String description,
			TransactionType type) {
		this.id = id;
		this.date = date;
		this.amount = amount;
		this.category = category;
		this.description = description;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public String getCategory() {
		return category;
	}

	public String getDescription() {
		return description;
	}

	public TransactionType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", date=" + date + ", amount=" + amount + ", category=" + category
				+ ", description=" + description + ", type=" + type + "]";
	}
	
	
	
	
}
