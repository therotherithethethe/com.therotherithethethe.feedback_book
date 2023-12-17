package ui;

import dal.DbManipulation;
import dal.Account;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final File db = new File("db.txt");
    private static final ArrayList<Account> personsList = new ArrayList<>();
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void printMenu() {
        System.out.println("1. Переглянути усі відгуки.\n2. Додати відгук.");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> readFeedbacks();
        }
        printMenu();
    }
    private static void addFeedback() {
        System.out.println("Введіть ім'я користувача:");
        String name = scanner.nextLine();
    }
    private static void readFeedbacks() {
        String[] text = DbManipulation.dbToString(db).split("\n");
        if(text[0].split(":").length == 1) {
            System.out.println("db is null or crashed");
        }
        else {
            for(int i = 0; i < text.length; i++) {
                String user = ANSI_RESET + text[i].split(":")[0] + ":";
                System.out.println(user);
                for (int j = 2; j < text[0].split(":").length; j+=2) {
                    String feedback = text[i].split(":")[j];
                    System.out.println(ANSI_CYAN + "\t" + feedback);
                }
            }
        }
    }

}
