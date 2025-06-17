import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private final CourseManager manager;
    private final Scanner scanner;


    public Menu() {
        this.manager = new CourseManager();
        this.scanner = new Scanner(System.in);
    }


    public void start() {
        boolean menuActive = true;
        while (menuActive) {
            printMenu();
            System.out.println("\nChoose an option: ");
            String userInput = scanner.nextLine();

            try {
                int userChoice = Integer.parseInt(userInput);
                switch (userChoice) {
                    case 1:
                        scanViewCoursesInput();
                        break;
                    case 2:
                        scanAddCourseInput();
                        break;
                    case 3:
                        scanDeleteCourseInput();
                        break;
                    case 4:
                        scanModifyCourseInput();
                        break;
                    case 5:
                        scanPrintByIdInput();
                        break;
                    case 6:
                        scanSortCoursesInput();
                        break;
                    case 7:
                        if (scanSaveCoursesInput())
                            break;
                        break;
                    case 8:
                        if (scanLoadCoursesInput())
                            break;
                        break;
                    case 9:
                        menuActive = false;
                        break;
                    default:
                        System.out.println("Invalid option, try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");}
        }
        System.out.println("Goodbye!");
    }

    private boolean scanLoadCoursesInput() {
        System.out.println("\nYou have selected Option 8: Load Courses from File");

        String fileName = Util.promptWithCancel("\nEnter filename to load from (or 'cancel' to abort): ");
        if (fileName == null) {
            System.out.println("Load cancelled.");
            return true;
        }

        String confirm = Util.promptWithCancel("This will overwrite current courses. Continue? (yes/no): ");
        if (confirm == null || !confirm.equalsIgnoreCase("yes")) {
            System.out.println("Load cancelled.");
            return true;
        }

        boolean success = manager.loadFromFile(fileName.trim());
        if (success) {
            System.out.println("Courses loaded successfully from '" + fileName + "'.");
        } else {
            System.out.println("Failed to load courses. Please check the file name and format.");
        }
        return false;
    }

    private boolean scanSaveCoursesInput() {
        System.out.println("\nYou have selected Option 7: Save Courses to File");

        String fileName = Util.promptWithCancel("\nEnter filename to save to (or 'cancel' to abort): ");
        if (fileName == null) {
            System.out.println("Save cancelled.");
            return true;
        }

        boolean success = manager.saveToFile(fileName.trim());
        if (success) {
            System.out.println("Courses saved successfully to '" + fileName + "'.");
        } else {
            System.out.println("Failed to save courses.");
        }
        return false;
    }

    private void scanModifyCourseInput() {
        System.out.println("\nYou have selected Option 4: Modify a Course");

        String idInput = Util.promptWithCancel("Enter the ID of the course to modify (or type 'cancel' to exit): ");

        if (idInput == null) {
            System.out.println("Modification cancelled.");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idInput);
            if (id <= 0) {
                System.out.println("Error: ID must be positive.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid integer.");
            return;
        }

        Course courseToModify = manager.findCourseById(id);
        if (courseToModify == null) {
            System.out.println("No course found with ID: " + id);
            return;
        }

        System.out.println("Course found: " + courseToModify);

        System.out.println("\nWhich field would you like to modify?\n");
        System.out.println("1. Name");
        System.out.println("2. Average Score");
        System.out.println("3. Number of Students");

        String choice = Util.promptWithCancel("Enter choice (1–3) or 'cancel': ");
        if (choice == null) {
            System.out.println("Modification cancelled.");
            return;
        }

        switch (choice) {
            case "1":
                while (true) {
                    String modifiedName = Util.promptWithCancel("Enter new name for the course (or 'cancel' to exit): ");
                    if (modifiedName == null) {
                        System.out.println("Name change cancelled.");
                        break;
                    }

                    if (modifiedName.trim().isEmpty()) {
                        System.out.println("Error: Name cannot be blank.");
                    } else {
                        courseToModify.setName(modifiedName.trim());
                        System.out.println("Course name updated successfully!");
                        break;
                    }
                }
                break;
            case "2":
                while (true) {
                    String avgInput = Util.promptWithCancel("Enter new average score (0–100, or 'cancel' to exit): ");
                    if (avgInput == null) {
                        System.out.println("Score update cancelled.");
                        break;
                    }

                    try {
                        double modifiedAvg = Double.parseDouble(avgInput);
                        if (modifiedAvg < 0 || modifiedAvg > 100) {
                            System.out.println("Error: Score must be between 0 and 100.");
                        } else {
                            courseToModify.setAvgScore(modifiedAvg);
                            System.out.println("Average score updated successfully!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid number.");
                    }
                }
                break;
            case "3":
                while (true) {
                    String studInput = Util.promptWithCancel("Enter new number of students (0 or more, or 'cancel' to exit): ");
                    if (studInput == null) {
                        System.out.println("Student count update cancelled.");
                        break;
                    }

                    try {
                        int newNumStudents = Integer.parseInt(studInput);
                        if (newNumStudents < 0) {
                            System.out.println("Error: Number of students cannot be negative.");
                        } else {
                            courseToModify.setNumStudents(newNumStudents);
                            System.out.println("Number of students updated successfully!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Please enter a valid integer.");
                    }
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void scanSortCoursesInput() {
        System.out.println("\nYou have selected Option 6: Sort Courses by Average Score (Descending)\n");

        ArrayList<Course> sortedCourses = manager.getAllCourses();
        if (sortedCourses.isEmpty()) {
            System.out.println("No courses to sort.");
        } else {
            manager.sortByAvgScoreDescending();
            System.out.println("Courses sorted by average score:\n");

            int index = 1;
            for (Course course : sortedCourses) {
                System.out.printf(" %2d) %s%n", index++, course);
            }
        }
    }

    private void scanPrintByIdInput() {
        System.out.println("\nYou have selected Option 5: Print a Course by ID");

        while (true) {
            String input = Util.promptWithCancel("\nEnter the course ID to view (or 'cancel' to exit): ");

            if (input == null) {
                System.out.println("Operation cancelled.");
                break;
            }

            try {
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println("Error: ID must be a positive integer.");
                } else {
                    Course foundCourse = manager.findCourseById(id);
                    if (foundCourse != null) {
                        System.out.println("\nCourse Found:");
                        System.out.println(" * " + foundCourse);
                    } else {
                        System.out.println("Error: No course found with ID " + id + ".");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    private void scanDeleteCourseInput() {
        System.out.println("\nYou have selected Option 3: Delete a Course");

        while (true) {
            String input = Util.promptWithCancel("\nEnter the course ID to delete (or 'cancel' to exit): ");

            if (input == null) {
                System.out.println("Course deletion cancelled.");
                break;
            }

            try {
                int id = Integer.parseInt(input);
                if (id <= 0) {
                    System.out.println("Error: ID must be a positive integer.");
                } else if (!manager.courseIdExists(id)) {
                    System.out.println("Error: No course found with ID " + id + ".");
                } else {
                    String confirm = Util.promptWithCancel("Are you sure you want to delete course with ID " + id + "? (yes/no): ");
                    if (confirm == null || !confirm.equalsIgnoreCase("yes")) {
                        System.out.println("Deletion cancelled.");
                        break;
                    }

                    boolean deleted = manager.deleteCourseById(id);
                    if (deleted) {
                        System.out.println("Course with ID " + id + " deleted successfully.");
                    } else {
                        System.out.println("Unexpected error: Course not deleted.");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number.");
            }
        }
    }

    private void printMenu () {
        System.out.println("\n=== Course Management Menu ===");
        System.out.println("1. View All Courses");
        System.out.println("2. Add a Course");
        System.out.println("3. Delete a Course by ID");
        System.out.println("4. Modify a Course");
        System.out.println("5. Print a Course by ID");
        System.out.println("6. Sort Courses by Avg Score (desc)");
        System.out.println("7. Save Courses to File");
        System.out.println("8. Load Courses from File");
        System.out.println("9. Exit");
    }


    private void scanViewCoursesInput() {
        System.out.println("\nYou have selected Option 1: View All Courses");
        System.out.println();
        ArrayList<Course> courseList = manager.getAllCourses();
        if (courseList.isEmpty()) {
            System.out.println("No courses available.");
        } else {
            System.out.println("\nAll courses (" + courseList.size() + " total):\n");
            int index = 1;
            for (Course course : courseList) {
                System.out.printf(" %2d) %s%n", index++, course);
            }
        }
    }

    private void scanAddCourseInput() {
        System.out.println("\nYou have selected Option 2: Add A Course");

        String newCourseName;
        int newCourseId;
        double newAvg;
        int newNumStud;

        // Enter Name Validation
        while (true) {
            String newCourseNameInput = Util.promptWithCancel("\n1. Enter course name (or type 'cancel' to exit): ");

            if (newCourseNameInput == null) {
                System.out.println("Course creation cancelled.");
                return;
            }

            if (newCourseNameInput.isEmpty()) {
                System.out.println("Error: Name cannot be blank.");
            } else {
                newCourseName = newCourseNameInput;
                break;
            }
        }
        //Enter ID Validation
        while (true) {
            String idInput = Util.promptWithCancel("\n2. Enter course ID (positive integer, or 'cancel' to exit): ");

            if (idInput == null) {
                System.out.println("Course creation cancelled.");
                return;
            }

            try {
                int parsedId = Integer.parseInt(idInput);
                if (parsedId <= 0) {
                    System.out.println("Error: ID must be a positive integer.");
                } else if (manager.courseIdExists(parsedId)) {
                    System.out.println("Error: A course with this ID already exists.");
                } else {
                    newCourseId = parsedId;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
        //Enter AvgScore  Validation
        while (true) {
            String avgInput = Util.promptWithCancel("\n3. Enter average score (0–100, or 'cancel' to exit): ");

            if (avgInput == null) {
                System.out.println("Course creation cancelled.");
                return;
            }

            try {
                double parsedAvg = Double.parseDouble(avgInput);
                if (parsedAvg < 0 || parsedAvg > 100) {
                    System.out.println("Error: Score must be between 0 and 100.");
                } else {
                    newAvg = parsedAvg;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid decimal number.");
            }
        }
        //Enter Student Number Validation
        while (true) {
            String numInput = Util.promptWithCancel("\n4. Enter number of students (0 or more, or 'cancel' to exit): ");

            if (numInput == null) {
                System.out.println("Course creation cancelled.");
                return;
            }

            try {
                int parsedNum = Integer.parseInt(numInput);
                if (parsedNum < 0) {
                    System.out.println("Error: Number of students cannot be negative.");
                } else {
                    newNumStud = parsedNum;
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }

        // Add Course Validation
        try {
            Course newCourse = new Course(newCourseName, newCourseId, newAvg, newNumStud);
            if (manager.addCourse(newCourse)) {
                System.out.println("Course added successfully!");
            } else {
                System.out.println("ID already exists - course not added.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Input: " + e.getMessage());
        }
    }
}





