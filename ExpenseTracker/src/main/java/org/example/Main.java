package org.example;

import org.example.Transaction;
import org.example.TransactionManager;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TransactionManager manager = new TransactionManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Load existing transactions? (y/n)\n> ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("Enter filename: ");
            String file = scanner.nextLine();
            try {
                manager.loadDataFromFile(file);
            } catch (Exception e) {
                System.out.println("Failed to load file: " + e.getMessage());
            }
        }

        while (true) {
            System.out.println("\nSelect Option:");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expense");
            System.out.println("3. View Monthly Summary");
            System.out.println("4. Save to File");
            System.out.println("5. Exit");
            System.out.print("Choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                case 2:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    LocalDate date = LocalDate.parse(scanner.nextLine());

                    String categoryPrompt = choice == 1 ?
                            "Enter category (Salary/Business): " :
                            "Enter category (Food/Rent/Travel): ";

                    System.out.print(categoryPrompt);
                    String category = scanner.nextLine();

                    System.out.print("Enter amount: ");
                    double amount = Double.parseDouble(scanner.nextLine());

                    String type = choice == 1 ? "Income" : "Expense";
                    manager.addTransaction(new Transaction(date, type, category, amount));
                    break;

                case 3:
                    System.out.print("Enter year and month (YYYY MM): ");
                    String[] ym = scanner.nextLine().split(" ");
                    manager.printMonthlySummary(Integer.parseInt(ym[0]), Integer.parseInt(ym[1]));
                    break;

                case 4:
                    System.out.print("Enter file to save to: ");
                    try {
                        manager.saveToFile(scanner.nextLine());
                    } catch (Exception e) {
                        System.out.println("Error saving: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Exiting. Have a great day!");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
