
import java.util.*;

// Task class to represent individual tasks
class Task {
	private static int idCounter = 1;
	private int id;
	private String description;
	private int priority; // 1 = High, 2 = Medium, 3 = Low

	public Task(String description, int priority) {
		this.id = idCounter++;
		this.description = description;
		this.priority = priority;
	}

	public int getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public int getPriority() {
		return priority;
	}

	public String getPriorityLabel() {
		switch (priority) {
		case 1:
			return "HIGH";
		case 2:
			return "MEDIUM";
		case 3:
			return "LOW";
		default:
			return "UNKNOWN";
		}
	}

	@Override
	public String toString() {
		return String.format("Task #%d [%s] - %s", id, getPriorityLabel(), description);
	}
}

// Main Smart Task Manager System
public class SmartTaskManager {
	private Task[] taskArray; // Array to store all tasks
	private Queue<Task> taskQueue; // Queue for normal task processing
	private Stack<Task> undoStack; // Stack for undo functionality
	private int taskCount;
	private static final int MAX_TASKS = 100;

	public SmartTaskManager() {
		taskArray = new Task[MAX_TASKS];
		taskQueue = new LinkedList<>();
		undoStack = new Stack<>();
		taskCount = 0;
	}

	// Add a new task
	public void addTask(String description, int priority) {
		if (taskCount >= MAX_TASKS) {
			System.out.println("Task limit reached! Cannot add more tasks.");
			return;
		}

		if (priority < 1 || priority > 3) {
			System.out.println("Invalid priority! Use 1 (High), 2 (Medium), or 3 (Low).");
			return;
		}

		Task newTask = new Task(description, priority);
		taskArray[taskCount] = newTask;
		taskCount++;

		// Enqueue the task
		enqueueTask(newTask);

		System.out.println("Task added successfully: " + newTask);
	}

	// Enqueue task to the queue
	private void enqueueTask(Task task) {
		taskQueue.offer(task);
		System.out.println("Task enqueued to processing queue");
	}

	// Dequeue and process the next task
	public void processNextTask() {
		if (taskQueue.isEmpty()) {
			System.out.println("No tasks in queue to process!");
			return;
		}

		Task processedTask = taskQueue.poll();
		undoStack.push(processedTask);

		System.out.println("Processed: " + processedTask);
		System.out.println("Task dequeued from processing queue");
	}

	// Undo the last processed task
	public void undoLastTask() {
		if (undoStack.isEmpty()) {
			System.out.println("No tasks to undo!");
			return;
		}

		Task lastTask = undoStack.pop();
		taskQueue.offer(lastTask); // Re-add to queue

		System.out.println("Undone: " + lastTask);
		System.out.println("Task moved back to queue");
	}

	// Display all tasks (from array)
	public void displayAllTasks() {
		System.out.println("\nALL TASKS IN SYSTEM:");
		System.out.println("========================");

		if (taskCount == 0) {
			System.out.println("No tasks available.");
			return;
		}

		for (int i = 0; i < taskCount; i++) {
			System.out.println("   " + taskArray[i]);
		}
		System.out.println("========================");
		System.out.println("Total tasks: " + taskCount);
	}

	// Display pending tasks (in queue)
	public void displayPendingTasks() {
		System.out.println("\nPENDING TASKS (In Queue):");
		System.out.println("============================");

		if (taskQueue.isEmpty()) {
			System.out.println("No pending tasks.");
			return;
		}

		int position = 1;
		for (Task task : taskQueue) {
			System.out.println("   " + position + ". " + task);
			position++;
		}
		System.out.println("============================");
		System.out.println("Total pending: " + taskQueue.size());
	}

	// Display processed tasks (in undo stack)
	public void displayProcessedTasks() {
		System.out.println("\nPROCESSED TASKS (Can be undone):");
		System.out.println("=====================================");

		if (undoStack.isEmpty()) {
			System.out.println("No processed tasks.");
			return;
		}

		Stack<Task> tempStack = new Stack<>();
		tempStack.addAll(undoStack);

		int position = 1;
		while (!tempStack.isEmpty()) {
			System.out.println("   " + position + ". " + tempStack.pop());
			position++;
		}
		System.out.println("=====================================");
		System.out.println("Total processed: " + undoStack.size());
	}

	// Display statistics
	public void displayStats() {
		System.out.println("\nTASK MANAGER STATISTICS:");
		System.out.println("===========================");
		System.out.println("Total tasks created: " + taskCount);
		System.out.println("Pending tasks: " + taskQueue.size());
		System.out.println("Processed tasks: " + undoStack.size());
		System.out.println("===========================");
	}

	// Main method with interactive menu
	public static void main(String[] args) {
		SmartTaskManager manager = new SmartTaskManager();
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("**********************");
		System.out.println(" Smart Task Manager");
		System.out.println("**********************");
		
		while (true) {
			System.out.println("\n MENU:");
			System.out.println("1. Add Task");
			System.out.println("2. Process Next Task");
			System.out.println("3. Undo Last Processed Task");
			System.out.println("4. View All Tasks");
			System.out.println("5. View Pending Tasks");
			System.out.println("6. View Processed Tasks");
			System.out.println("7. View Statistics");
			System.out.println("8. Exit");
			System.out.print("\nEnter your choice: ");

			int choice = scanner.nextInt();
			scanner.nextLine(); // Consume newline

			switch (choice) {
			case 1:
				System.out.print("Enter task description: ");
				String description = scanner.nextLine();
				System.out.print("Enter priority (1=High, 2=Medium, 3=Low): ");
				int priority = scanner.nextInt();
				manager.addTask(description, priority);
				break;

			case 2:
				manager.processNextTask();
				break;

			case 3:
				manager.undoLastTask();
				break;

			case 4:
				manager.displayAllTasks();
				break;

			case 5:
				manager.displayPendingTasks();
				break;

			case 6:
				manager.displayProcessedTasks();
				break;

			case 7:
				manager.displayStats();
				break;

			case 8:
				System.out.println("\nThank you for using Smart Task Manager!");
				scanner.close();
				return;

			default:
				System.out.println("Invalid choice! Please try again.");
			}
		}
	}
}