import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {
    //fields
    private final ArrayList<Course> courses = new ArrayList<>();

    public CourseManager() {
    }

    public ArrayList<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public boolean addCourse(Course course) {
        for (Course c : courses ) {
            if (c.getId() == course.getId()) {
                return false;
            }
        }
        courses.add(course);
        return true;
    }

    public boolean courseIdExists(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteCourseById(int id) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getId() == id) {
                courses.remove(i);
                return true;
            }
        }
        return false;
    }

    public Course findCourseById(int id) {
        for (Course c : courses) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public void sortByAvgScoreDescending() {
        courses.sort((c1, c2) -> Double.compare(c2.getAvgScore(), c1.getAvgScore()));
    }

    public boolean saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(courses);
            return true;
        } catch (IOException e) {
            System.out.println("Error saving courses: " + e.getMessage());
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            Object obj = in.readObject();
            if (obj instanceof List<?>) {
                courses.clear();
                courses.addAll((List<Course>) obj);
                return true;
            } else {
                System.out.println("Invalid file format.");
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading courses: " + e.getMessage());
            return false;
        }
    }

}
