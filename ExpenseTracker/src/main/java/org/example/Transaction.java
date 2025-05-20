package org.example;
import java.time.LocalDate;
public class Transaction {
    private LocalDate date;
    private String type;
    private String category;
    private double amount;

    public Transaction(LocalDate date, String type, String category,double amount){
        this.date = date;
        this.type = type;
        this.category = category;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTypes() {
        return type;
    }

    public String getCategories() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
    @Override
    public String toString(){
        return date + " ," + type + " ," + category + " ," + amount;
    }
}
