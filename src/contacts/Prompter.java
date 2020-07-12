package contacts;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Prompter {
    private final static Scanner STDIN = new Scanner(System.in);

    public static String nextString(String prompt) {
        System.out.print(prompt);
        return STDIN.nextLine();
    }

    public static int nextInt(String prompt) {
        System.out.print(prompt);
        int input = STDIN.nextInt();
        STDIN.nextLine();
        return input;
    }

    public static LocalDate nextDate(String prompt) {
        System.out.print(prompt);
        String dateIn = STDIN.nextLine();

        if (dateIn.isEmpty()) {
            return null;
        }

        LocalDate date;

        try {
            date = LocalDate.parse(dateIn);
        } catch (DateTimeParseException e) {
            System.out.println("Bad birth date!");
            return null;
        }

        return date;
    }

    public static Gender nextGender(String prompt) {
        System.out.print(prompt);
        String genderIn = STDIN.nextLine();

        if (genderIn.isEmpty()) {
            return null;
        }

        Gender gender;

        try {
            gender = Gender.valueOf(genderIn);
        } catch (IllegalArgumentException e) {
            System.out.println("Bad gender!");
            return null;
        }

        return gender;
    }
}
