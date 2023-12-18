package ui;

import dal.DbManipulation;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Menu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final File db = new File("db.txt");
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    public static void printMenu() {
        System.out.println(ANSI_RESET + "1. Переглянути усі відгуки.\n2. Додати відгук.");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> readFeedbacks();
            case "2" -> addFeedback();
        }
        printMenu();
    }
    private static void addFeedback() {
        System.out.println("Введіть ім'я користувача:");
        String name = scanner.nextLine();

        String[] dbFirstStructurize = DbManipulation.dbToString(db).split("\n");

        boolean isUserFound = false;
        for(String dbSecondStructurize : dbFirstStructurize) {
            for (int j = 0; j < dbFirstStructurize[0].split(":").length; j+=2) {
                if (dbSecondStructurize.split(":")[j].equals(name)) {
                    isUserFound = true;
                    break;
                }
            }
        }

        if(isUserFound) {
            System.out.println("Користувач знайдений у базі даних. Виберіть дію.\n1. Ввести інше ім'я користувача.\n2. Ввести пароль для поточного користувача.");
            String choice = scanner.nextLine();
            if(choice.equals("1")) {
                addFeedback();
            }
            else {
                int password = scanner.nextLine().hashCode();
                boolean isPasswordEqualsToDatabasePass = false;
                for(String dbSecondStructurize : dbFirstStructurize) {
                    for (int j = 1; j < dbFirstStructurize[0].split(":").length; j+=2) {
                        if(Integer.parseInt(dbSecondStructurize.split(":")[j]) == password) {
                            isPasswordEqualsToDatabasePass = true;
                            break;
                        }
                    }
                }
                if(isPasswordEqualsToDatabasePass) {
                    System.out.println("Додайте фідбек:");
                    String feedback = scanner.nextLine();
                    String date = "(" + new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()) + ")";
                    DbManipulation.writeFeedBackToDb("\n" + name + ":", password + ":", feedback + " " +  date);
                }
                else {
                    System.out.println("Пароль не співпадає з даним користувачем у базі. Поверненя до меню.");
                    printMenu();
                }

            }
        }
        else {
            System.out.println("Новий користувач. Введіть пароль який буде записано у базу.");
            int password = scanner.nextLine().hashCode();
            System.out.println("Введіть фідбек");
            String feedback = scanner.nextLine();
            String date = "(" + new SimpleDateFormat("yyyy-MM-dd HH.mm.ss").format(new Date()) + ")";
            if(db.length() == 1) {
                DbManipulation.writeFeedBackToDb(name + ":", password + ":", feedback + " " + date);
            }
            else {
                DbManipulation.writeFeedBackToDb("\n" + name + ":", password + ":", feedback + " " +  date);
            }
            System.out.println("Дякуємо");
        }
    }
    private static void readFeedbacks() {
        try{
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
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

}
