package ch.noseryoung.blj;

import org.junit.jupiter.api.Test;


class GameTest {

    Game game = new Game();

    @Test
    void createDoors() {

        boolean passed = false;
        if (game.getDoors().isEmpty()) {
            System.err.println("Test not passed! No doors created");
        } else {
            passed = true;
        }
        if (passed == true) {
            System.out.println("Test passed, the doors where created.");
        }
    }

    @Test
    void createRooms() {
        boolean passed = false;
        if (game.getRooms().isEmpty()) {
            System.err.println("Test not passed! No rooms created");
        } else {
            passed = true;
        }
        if (passed == true) {
            System.out.println("Test passed, the rooms where created.");
        }
    }

    @Test
    void addItems() {
        if (game.getItems().isEmpty()) {
            System.err.println("No items found! Test not passed!");
        } else {
            System.out.println("Found items. Test passed");
        }
    }

}