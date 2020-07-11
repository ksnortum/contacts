package contacts;

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
}
