package fr.diginamic.automates;

import java.util.ArrayList;
import java.util.List;

public class JeuDeLaVie {
    private boolean[][] state;

    private JeuDeLaVie(boolean[][] state) {
        this.state = state;
    }

    public static JeuDeLaVie prepare(String initialStateString) {
        String[] stateStringLines = initialStateString.split("\n");
        boolean[][] initialState = stateFromStringLines(stateStringLines);
        return new JeuDeLaVie(initialState);
    }

    public static JeuDeLaVie prepare(String[] stateStringLines) {
        boolean[][] initialState = stateFromStringLines(stateStringLines);
        return new JeuDeLaVie(initialState);
    }

    private static boolean[][] stateFromStringLines(String[] stateStringLines) {
        List<String> lineList = new ArrayList<>();

        for (String line : stateStringLines) {
            lineList.add(line.replaceAll("\\|", ""));
        }

        String[] lines = lineList.toArray(new String[0]);

        int height = lines.length;
        int length = lines[0].length();

        if (stateStringLines[0].isEmpty()) {
            throw new RuntimeException("There must be at least one line and one char in the initial state");
        }

        boolean[][] state = new boolean[height][length];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < length; j++) {
                state[i][j] = lines[i].charAt(j) == 'X';
            }
        }

        return state;
    }

    public void process(int iterations) {
        System.out.println("Initial State:");
        this.printState();
        for (int i = 0; i < iterations; i++) {
            System.out.println("\n\n" + "State " + (i+1) + ":" + "\n");
            this.state = nextState(this.state);
            this.printState();
        }
    }

    private static boolean[][] nextState(boolean[][] state) {
        boolean[][] newState = new boolean[state.length][state[0].length];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[i].length; j++) {
                newState[i][j] = nextCellState(state, i, j);
            }
        }
        return newState;
    }

    private static boolean nextCellState(boolean[][] currentState, int x, int y) {
        boolean cell = currentState[x][y];
        int count = nearbyCellCount(currentState, x, y);
        if (!cell) {
            return count == 3;
        } else {
            return count == 2 || count == 3;
        }
    }

    private static int nearbyCellCount(boolean[][] state, int x, int y) {
        int count = 0;

        int xStart = Math.max(x - 1, 0);
        int xEnd = Math.min(x + 1, state.length - 1);
        int yStart = Math.max(y - 1, 0);
        int yEnd = Math.min(y + 1, state[0].length - 1);

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (state[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public void printState() {
        for (boolean[] booleans : this.state) {
            System.out.print('|');
            for (boolean isCell : booleans) {
                System.out.print(isCell ? 'X' : ' ');
                System.out.print('|');
            }
            System.out.print('\n');
        }
    }
}
