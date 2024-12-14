import org.json.JSONObject;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private static int nextId = 1; // Simple static counter for unique IDs
    private final int id;
    private String name;
    private String description;
    private LocalDate date; // Use LocalDate for date
    private LocalTime time; // Use LocalTime for time

    // Constructor with date and time as strings, which are parsed into LocalDate and LocalTime
    public Task(String name, String description, String date, String time) {
        this.id = nextId++;
        this.name = name;
        this.description = description;
        this.date = parseDate(date); // Convert string to LocalDate
        this.time = parseTime(time); // Convert string to LocalTime
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    // Setters (optional)
    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Convert date string to LocalDate
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateStr, formatter);
    }

    // Convert time string to LocalTime
    private LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return LocalTime.parse(timeStr, formatter);
    }

    // Convert Task to JSONObject (JSON representation with date and time as strings)
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("name", name);
        jsonObject.put("description", description);
        jsonObject.put("date", (date != null ? date.toString() : null));
        jsonObject.put("time", (time != null ? time.toString() : null));
        return jsonObject;
    }

    // Optional: Add a toString() method for better readability in print statements
    @Override
    public String toString() {
        return "Task [id=" + id + ", name=" + name + ", description=" + description +
               ", date=" + (date != null ? date : "N/A") + ", time=" + (time != null ? time : "N/A") + "]";
    }

    // Optional: Override equals and hashCode methods for comparison and using in collections
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
