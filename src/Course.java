import java.io.Serial;
import java.io.Serializable;

public class Course implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    //fields
    private int id;
    private String name;
    private double avgScore;
    private int numStudents;

    //Constructor
    public Course(String name, int id, double avgScore, int numStudents) {
        setName(name);
        setId(id);
        setAvgScore(avgScore);
        setNumStudents(numStudents);
    }

    //Setters
    private void setId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
    }

    public void setName(String name) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Name cannot be empty.");
        this.name = name;
    }

    public void setAvgScore(double avgScore) {
        if (avgScore < 0 || avgScore > 100)
            throw new IllegalArgumentException("Average score must be 0–100.");
        this.avgScore = avgScore;
    }

    public void setNumStudents(int numStudents) {
        if (numStudents < 0)
            throw new IllegalArgumentException("Number of students cannot be negative.");
        this.numStudents = numStudents;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public int getNumStudents() {
        return numStudents;
    }

    //toString() Output
    @Override
    public String toString() {
        return String.format("%-5s | Avg: %6.2f | Students: %3d | ID: %d",
                name, avgScore, numStudents, id);
        }
}




