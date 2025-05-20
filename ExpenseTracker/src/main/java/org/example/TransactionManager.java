package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionManager {
    private List<Transaction> transactions= new ArrayList<>();

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
        System.out.println("Transaction Added.");
    }
    public void loadDataFromFile(String filename) throws IOException{
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        br.readLine();

        while ((line = br.readLine()) != null) {
            String[] parts = line.split(",");
            LocalDate date = LocalDate.parse(parts[0]);
            String type = parts[1];
            String category = parts[2];
            double amount = Double.parseDouble(parts[3]);

            transactions.add(new Transaction(date, type, category, amount));
        }
        br.close();

        System.out.println("Loaded " + transactions.size() + " transactions from file.");
    }
    public void saveToFile(String filename) throws IOException{
        File file = new File(filename);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs(); // Create dirs if not exist
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write("date,type,category,amount\n");
        for (Transaction transaction : transactions) {
            bw.write(transaction.toString() + "\n");
        }
        bw.close();

        System.out.println("File saved to: " + file.getAbsolutePath());
    }
    public void printMonthlySummary(int year, int month){
        YearMonth ym = YearMonth.of(year, month);
        double incomeTotal = 0, expenseTotal = 0;
        Map<String, Double> categoryMap = new HashMap<>();

        for (Transaction t : transactions) {
            if (YearMonth.from(t.getDate()).equals(ym)) {
                if (t.getTypes().equalsIgnoreCase("Income")) {
                    incomeTotal += t.getAmount();
                } else {
                    expenseTotal += t.getAmount();
                    categoryMap.put(t.getCategories(),
                            categoryMap.getOrDefault(t.getCategories(), 0.0) + t.getAmount());
                }
            }
        }
        System.out.println("\n---***--- Monthly Summary ---***---");
        System.out.println("Total Income: ₹" + incomeTotal);
        System.out.println("Total Expense: ₹" + expenseTotal);
        System.out.println("Net Savings: ₹" + (incomeTotal - expenseTotal));

        System.out.println("\nExpense by Category:");
        for (Map.Entry<String, Double> entry : categoryMap.entrySet()) {
            System.out.printf("%-10s: ₹%.2f\n", entry.getKey(), entry.getValue());
        }
    }
}
