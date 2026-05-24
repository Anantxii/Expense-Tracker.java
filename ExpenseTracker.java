
import java.io.*;
import java.util.*;

class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    int id;
    String name;
    double amount;

    Expense(int id, String name, double amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }
}

public class ExpenseTracker {

    static final String FILE_NAME = "expenses.dat";

    // Load expenses from file
    static ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> list = new ArrayList<>();

        try {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                return list;
            }

            ObjectInputStream ois =
                    new ObjectInputStream(new FileInputStream(file));

            list = (ArrayList<Expense>) ois.readObject();

            ois.close();

        } catch (Exception e) {
            System.out.println("Error loading data: " + e.getMessage());
        }

        return list;
    }

    // Save expenses to file
    static void saveExpenses(ArrayList<Expense> list) {

        try {
            ObjectOutputStream oos =
                    new ObjectOutputStream(new FileOutputStream(FILE_NAME));

            oos.writeObject(list);

            oos.close();

        } catch (Exception e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Add Expense
    static void addExpense(Scanner sc, ArrayList<Expense> list) {

        System.out.print("\nEnter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        list.add(new Expense(id, name, amount));

        saveExpenses(list);

        System.out.println("Expense Added Successfully!");
    }

    // View Expenses
    static void viewExpenses(ArrayList<Expense> list) {

        System.out.println("\n--- Expense List ---");

        if (list.isEmpty()) {
            System.out.println("No expenses found!");
            return;
        }

        System.out.printf("%-10s %-20s %-10s\n",
                "ID", "Name", "Amount");

        for (Expense e : list) {
            System.out.printf("%-10d %-20s %-10.2f\n",
                    e.id, e.name, e.amount);
        }
    }

    // Search Expense
    static void searchExpense(Scanner sc, ArrayList<Expense> list) {

        System.out.print("\nEnter ID to search: ");
        int id = sc.nextInt();

        boolean found = false;

        for (Expense e : list) {

            if (e.id == id) {

                System.out.println("\nExpense Found:");
                System.out.printf("%-10d %-20s %-10.2f\n",
                        e.id, e.name, e.amount);

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Expense Not Found!");
        }
    }

    // Delete Expense
    static void deleteExpense(Scanner sc, ArrayList<Expense> list) {

        System.out.print("\nEnter ID to delete: ");
        int id = sc.nextInt();

        boolean removed = list.removeIf(e -> e.id == id);

        if (removed) {
            saveExpenses(list);
            System.out.println("Expense Deleted Successfully!");
        } else {
            System.out.println("Expense Not Found!");
        }
    }

    // Total Expense
    static void totalExpense(ArrayList<Expense> list) {

        double total = 0;

        for (Expense e : list) {
            total += e.amount;
        }

        System.out.println("\nTotal Expense: " + total);
    }

    // Main Method
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Expense> list = loadExpenses();

        int choice;

        do {

            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Search Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Total Expense");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addExpense(sc, list);
                    break;

                case 2:
                    viewExpenses(list);
                    break;

                case 3:
                    searchExpense(sc, list);
                    break;

                case 4:
                    deleteExpense(sc, list);
                    break;

                case 5:
                    totalExpense(list);
                    break;

                case 6:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid Choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}


