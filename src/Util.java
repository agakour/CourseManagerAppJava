import java.util.Scanner;

public class Util {
    private static final Scanner scanner = new Scanner(System.in);

    public static String promptWithCancel(String message) {
        System.out.println(message);
        String input = scanner.nextLine().trim();
        return input.equalsIgnoreCase("cancel") ? null : input;
    }
}
