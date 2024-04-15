package fr.diginamic.automates;

import fr.diginamic.automates.menu.ExitMenuInput;
import fr.diginamic.automates.menu.InitialStateChoiceMenuInput;
import fr.diginamic.automates.menu.MenuInput;

import java.io.File;
import java.util.*;

public class Menu {
    private final HashMap<Integer, MenuInput> menuInputs;
    private final Scanner scanner;

    private Menu() {
        menuInputs = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public static Menu createMenu() {
        File statesDir = new File("states");

        String[] statesNames = statesDir.list((dir, name) -> name.endsWith(".txt"));

        if (statesNames == null) {
            throw new IllegalArgumentException("No states found in " + statesDir);
        }

        Menu menu = new Menu();
        for (String stateName : statesNames) {
            menu.addInput(new InitialStateChoiceMenuInput(stateName, statesDir.getAbsolutePath() + '/' + stateName));
        }
        menu.addInput(99, new ExitMenuInput());

        return menu;
    }

    private void addInput(int idx, ExitMenuInput exitMenuInput) {
        menuInputs.put(idx, exitMenuInput);
    }


    private void addInput(MenuInput input) {
        ArrayList<Integer> keyList = new ArrayList<>(menuInputs.keySet());
        Collections.sort(keyList);
        int idx = 1;
        if (!keyList.isEmpty()) {
            idx = keyList.getLast() + 1;
        }

        menuInputs.put(idx, input);
    }

    public void printMenuAndAskChoice() {
        printMenu();
        int choice = askChoice();
        menuInputs.get(choice).run();
        if (choice != 99) {
            printMenuAndAskChoice();
        }
    }

    private void printMenu() {
        ArrayList<Map.Entry<Integer, MenuInput>> entries = new ArrayList<>(menuInputs.entrySet());
        Collections.sort(entries, Map.Entry.comparingByKey());

        System.out.println("Menu:");
        for (Map.Entry<Integer, MenuInput> entry : entries) {
            System.out.println("\t" + entry.getKey() + ". " + entry.getValue());
        }
    }

    private int askChoice() {
        int choice;
        do {
            System.out.println("Choix : ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (!menuInputs.containsKey(choice)) {
                System.out.println("Choix non valide - veuillez choisir une option valide");
            }
        } while (!menuInputs.containsKey(choice));
        return choice;
    }
}
