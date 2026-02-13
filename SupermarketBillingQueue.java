
import java.util.*;   // Import utility classes (Queue, Stack, Scanner, etc.)

// Customer class = Represents each customer
class Customer {

    private static int idCounter = 1;   // Static= Shared counter for unique IDs

    private int customerId;
    private String name;
    private String[] items;
    private int itemCount;

    // Constructor = Runs when new customer is created
    public Customer(String name, String[] items) {

        this.customerId = idCounter++;   // Auto-generate unique ID
        this.name = name;
        this.items = items;
        this.itemCount = items.length;   // Store number of items
    }

    // Getter methods = Provide controlled access to private data
    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String[] getItems() {
        return items;
    }

    public int getItemCount() {
        return itemCount;
    }

    // Defines how customer prints
    @Override
    public String toString() {
        return "Customer #" + customerId + " - " + name;
    }
}

// Main Billing System Class
public class SupermarketBillingQueue {

    private Queue<Customer> billingQueue;  // Queue = Manages customers (FIFO)
    private Stack<String> itemStack;       // Stack = Reverses item order (LIFO)

    // Constructor = Initializes data structures
    public SupermarketBillingQueue() {

        billingQueue = new LinkedList<>();  // LinkedList used as Queue
        itemStack = new Stack<>();          // Stack for item reversal
    }

    // Add Customer Method
    public void addCustomer(String name, String[] items) {

        Customer customer = new Customer(name, items);  // Create new customer object

        billingQueue.offer(customer);  // Add customer to queue (FIFO)

        System.out.println("Customer added: " + customer);
    }

    // Process Billing Method
    public void processBilling() {

        // Check if queue is empty
        if (billingQueue.isEmpty()) {
            System.out.println("No customers in queue!");
            return;
        }

        Customer customer = billingQueue.poll();  // Remove first customer (FIFO)

        System.out.println("\nProcessing billing for: " + customer);

        // Push items into stack (for reversal)
        for (String item : customer.getItems()) {
            itemStack.push(item);   // Stack = LIFO principle
        }

        System.out.println("Items scanned (reversed order):");

        // Pop items from stack = Reverse display
        while (!itemStack.isEmpty()) {
            System.out.println("- " + itemStack.pop());
        }

        System.out.println("Billing completed!");
    }

    //  Show Pending Customers
    public void showPendingCustomers() {

        if (billingQueue.isEmpty()) {
            System.out.println("No pending customers.");
            return;
        }

        System.out.println("\nPending Customers:");

        int position = 1;

        // Enhanced for-loop - Traverse queue safely
        for (Customer c : billingQueue) {
            System.out.println(position + ". " + c);
            position++;
        }
    }

    // ✅ Main Method → Program Entry Point
    public static void main(String[] args) {

        SupermarketBillingQueue system = new SupermarketBillingQueue();  // Create system object
        Scanner sc = new Scanner(System.in);  // Scanner = User input

        while (true) {

            // Display menu
            System.out.println("\n--- SUPERMARKET BILLING SYSTEM ---");
            System.out.println("1. Add Customer");
            System.out.println("2. Process Billing");
            System.out.println("3. Show Pending Customers");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");

            int choice = sc.nextInt();  // Read user choice
            sc.nextLine();              // Consume newline (important!)

            switch (choice) {

                case 1:
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter number of items: ");
                    int n = sc.nextInt();
                    sc.nextLine();  // Consume newline

                    String[] items = new String[n];  // Array - Store items

                    // Input items
                    for (int i = 0; i < n; i++) {
                        System.out.print("Enter item " + (i + 1) + ": ");
                        items[i] = sc.nextLine();
                    }

                    system.addCustomer(name, items);  // Call addCustomer method
                    break;

                case 2:
                    system.processBilling();  // Process next customer
                    break;

                case 3:
                    system.showPendingCustomers();  // Display queue
                    break;

                case 4:
                    System.out.println("Thank you!");
                    sc.close();   // Close scanner (good practice)
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
