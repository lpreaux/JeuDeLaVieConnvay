package fr.diginamic.automates.menu;

public abstract class MenuInput {
    private final String label;

    public MenuInput(String label) {
        this.label = label;
    }

    public abstract void run();

    @Override
    public String toString() {
        return label;
    }
}
