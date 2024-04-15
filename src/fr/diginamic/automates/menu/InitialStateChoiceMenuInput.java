package fr.diginamic.automates.menu;

import fr.diginamic.automates.JeuDeLaVie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class InitialStateChoiceMenuInput extends MenuInput {
    private final String initialStatePath;

    public InitialStateChoiceMenuInput(String label, String initialState) {
        super(label);
        this.initialStatePath = initialState;
    }

    @Override
    public void run() {
        try {
            List<String> initialStatesLines = Files.readAllLines(Path.of(initialStatePath));
            JeuDeLaVie jeuDeLaVie = JeuDeLaVie.prepare(initialStatesLines.toArray(new String[0]));
            jeuDeLaVie.process(10);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading initial state", e);
        }
    }
}
