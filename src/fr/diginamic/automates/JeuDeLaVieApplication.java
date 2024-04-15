package fr.diginamic.automates;

import java.util.Arrays;

public class JeuDeLaVieApplication {
    public static void main(String[] args) {
        if (Arrays.asList(args).contains("--withoutFile")) {
            startFromStringLiteral();
        } else {
            startMenu();
        }
    }

    private static void startMenu() {
        Menu menu = Menu.createMenu();
        menu.printMenuAndAskChoice();
    }

    public static void startFromStringLiteral() {
        String initialState = """
                | | |X| | | | | | |X|
                | | | | | | | |X|X| |
                | | | |X|X| | | | | |
                | | | |X|X| | | | | |
                | | | |X| | | | |X| |
                """;

        JeuDeLaVie jeu = JeuDeLaVie.prepare(initialState);
        jeu.process(10);
    }
}
