import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.json.JSONArray;
import org.json.JSONObject;

public class TaskManager {
    private List<Task> tasks;
    private static final String FILE_PATH = "tasks.json";
    private static File file = new File(FILE_PATH);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

        public TaskManager() {
            tasks = new ArrayList<>();
            loadTasks();
        }
    
        public void addTask(String name, String description, String date, String time) {
            Task task = new Task(name, description, date, time);
            tasks.add(task);
            saveTasks();
        }
    
        public void removeTask(int id) {
            tasks.removeIf(task -> task.getId() == id);
            saveTasks();
        }
    
        public List<Task> getTasks() {
            return tasks;
        }
    
        private void saveTasks() {
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                JSONArray jsonArray = new JSONArray();
                for (Task task : tasks) {
                    jsonArray.put(task.toJson());
                }
                writer.write(jsonArray.toString());
            } catch (IOException e) {
                System.out.println("An error occurred while saving tasks: " + e.getMessage());
            }
        }
    
        private void loadTasks() {
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    System.out.println("File not found. A new file has been created.");
                } catch (IOException e) {
                    System.out.println("An error occurred while creating the file: " + e.getMessage());
                }
            }

            try (FileReader reader = new FileReader(FILE_PATH)) {
                int charRead;
                StringBuilder content = new StringBuilder();
                while ((charRead = reader.read()) != -1) {
                    content.append((char) charRead);
                }
                JSONArray jsonArray = new JSONArray(content.toString());
                tasks = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    
                    String time = jsonObject.has("time") ? jsonObject.getString("time") : "";
                    String description = jsonObject.has("description") ? jsonObject.getString("description") : "";
                    String date = jsonObject.has("date") ? jsonObject.getString("date") : "";
    
                    tasks.add(new Task(name, description, date, time));
                }
            } catch (IOException | org.json.JSONException e) {
                if(file.length() != 0)
                    System.out.println("An error occurred while loading tasks: " + e.getMessage());
                tasks = new ArrayList<>();
            }
        }
    
        public static void main(String[] args) {
            TaskManager taskManager = new TaskManager();
            Scanner scanner = new Scanner(System.in);
            String command;
    
            do {
                System.out.println("\nMenu:");
                System.out.println("1. Add Task");
                System.out.println("2. Remove Task");
                System.out.println("3. View Tasks");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                command = scanner.nextLine();
    
                switch (command) {
                    case "1":
                        String taskName = "";
                        do {
                            System.out.println("Enter task name: ");
                            taskName = scanner.nextLine();
                            if(taskName.isEmpty())
                                System.out.println("Task name cannot be empty. Please try again.");
                            else break;
                            } while (true);
                        System.out.print("Enter task description: ");
                        String taskDescription = scanner.nextLine();
                        
                            String taskDate = null;
                            boolean validDate = false;
                            while (!validDate) {
                                System.out.print("Enter task date (yyyy-MM-dd): ");
                                taskDate = scanner.nextLine();
                                try {
                                    LocalDate.parse(taskDate, DATE_FORMAT);
                                    validDate = true;
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                                }
                            }

                            String taskTime = null;
                            boolean validTime = false;
                            while (!validTime) {
                                System.out.print("Enter task time (HH:mm): ");
                                taskTime = scanner.nextLine();
                                try {
                                    LocalTime.parse(taskTime, TIME_FORMAT);
                                    validTime = true;
                                } catch (DateTimeParseException e) {
                                    System.out.println("Invalid time format. Please enter the time in HH:mm format.");
                                }
                            }

                            System.out.println("Task date: " + taskDate);
                            System.out.println("Task time: " + taskTime);


                        taskManager.addTask(taskName, taskDescription, taskDate, taskTime.isEmpty() ? "" : taskTime);
                        break;
                    case "2":
                        System.out.print("Enter task ID to remove: ");
                        int taskIdToRemove = Integer.parseInt(scanner.nextLine());
                        if (taskIdToRemove <= 0) {
                            System.out.println("Invalid task ID. Please try again.");
                            break;
                        }
                        taskManager.removeTask(taskIdToRemove);
                        break;
                    case "3":
                        try (FileReader reader = new FileReader(FILE_PATH)) {
                            int charRead;
                            StringBuilder content = new StringBuilder();
                            while ((charRead = reader.read()) != -1) {
                                content.append((char) charRead);
                            }

                            if (content.length() == 0) {
                                System.out.println("Tasks:");
                                System.out.println("No tasks available.");
                            } else {
                                JSONArray jsonArray = new JSONArray(content.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    int id = jsonObject.getInt("id");
                                    String name = jsonObject.getString("name");
                                    String description = jsonObject.optString("description", "");
                                    String date = jsonObject.optString("date", "");
                                    String time = jsonObject.optString("time", "");

                                    System.out.println("ID: " + id + ", Name: " + name);
                                    if (!description.isEmpty()) {
                                        System.out.println("Description: " + description);
                                    }
                                    if (!date.isEmpty()) {
                                        System.out.println("Date: " + date);
                                    }
                                    if (!time.isEmpty()) {
                                        System.out.println("Time: " + time);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            System.out.println("An error occurred while reading the file: " + e.getMessage());
                        } catch (org.json.JSONException e) {
                            System.out.println("An error occurred while parsing the JSON content: " + e.getMessage());
                        }
                    break;
                case "4":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (!command.equals("4"));

        scanner.close();
    }
}
