package ch.noseryoung.blj;

import java.util.InputMismatchException;
import java.util.Scanner;

public class IO {

    public static Scanner scn = new Scanner(System.in);
    private int price = 0;

    /**
     * This method uses all methods to generate the game.
     * A do while loop loops it through, so it never stops until you exit the game or die.
     *
     * @param game   which game is used
     * @param player which player is used
     */
    public void switcher(Game game, Player player) {
        game.getLastRoom().push(game.getActiveRoom());
        System.out.println("Try to steal as much Items from the abandoned House and escape from it. \n" +
                "Watch out for anything or anyone... \nPress Enter to continue");

        IO.scn.nextLine();

        int choice;
        do {
            map(game);
            choice = readRangedInt(1, 10);
            switch (choice) {
                //print possible rooms
                case 1 -> possibleRoom(game.getActiveRoom(), game);

                //move between rooms
                case 2 -> game.move(player, game);

                //check if room has item
                case 3 -> game.inspectRoom(player);

                //show inventory
                case 4 -> printInventory(player, game);

                //save Data
                case 5 -> StoreScore.saveData("Store_Location-Items", player, game);

                //Load Data
                case 6 -> StoreScore.loadData("Store_Location-Items", player, game);

                //play time
                case 7 -> game.gameTime(player);

                //go room back
                case 8 -> game.safeMove(game);

                //print out possible steps back
                case 9 -> game.countMovesPossibleBack();

                //Exit program
                case 10 -> {
                    listInformation(player);
                }
            }
        } while (choice != 10);
    }

    /**
     * This method draws multiple boxes arround multiple Strings.
     *
     * @param maxLength defines the max lenght of a Box -> 20 meaning it prints 20 Times the 'HO_LINE'.
     * @param width     defines how many boxes are on next to eachother.
     * @param height    defines how many boxes are under eachother.
     * @param game      defines for which game the boxes are drawn
     * @param strings   write all of your Strings here and split the with a comma.
     *                  -> "Attack", "Defense", "Heal"
     *                  -> This will set each String in a Box. Width would be 3 and height 1.
     */
    public static void drawMultipleBox(int maxLength, int width, int height, Game game, String... strings) {
        final String HO_LINE = "\u2550";
        final String VER_LINE = "\u2551";
        final String CORNER_1 = "\u2554";
        final String CORNER_2 = "\u2557";
        final String CORNER_3 = "\u255D";
        final String CORNER_4 = "\u255A";
        final String SPACE = " ";
        int[] lengthDifference;

        for (String s : strings) {
            maxLength = Math.max(s.length(), maxLength);
        }
        int leftDistance = 1; //Index of lengthDifference Array
        int rightDistance = 2; //Index of lengthDifference Array
        for (int k = 0; k < height; k++) {
            for (int j = 0; j < width; j++) {
                if (k * width + j >= strings.length || strings[k * width + j].length() == 0) {
                    System.out.print(SPACE.repeat(2 + maxLength + 2));
                } else {
                    System.out.print(CORNER_1 + HO_LINE.repeat(maxLength) + CORNER_2 + SPACE.repeat(2));
                }
            }
            System.out.println();
            for (int j = 0; j < width; j++) {
                String printText;
                if (k * width + j < strings.length) printText = strings[k * width + j];
                else printText = "";
                lengthDifference = getLength(maxLength, printText.length());
                printText = printText.replace(game.getRooms().get(game.getActiveRoom()).getName(),
                        "\u001B[35m" + printText + "\u001B[0m");
                if (k * width + j >= strings.length || strings[k * width + j].length() == 0) {
                    System.out.print(SPACE.repeat(2 + maxLength + 2));
                } else {
                    System.out.print(VER_LINE + SPACE.repeat(lengthDifference[leftDistance]) + printText +
                            SPACE.repeat(lengthDifference[rightDistance]) + VER_LINE + SPACE.repeat(2));
                }
            }
            System.out.println();
            for (int j = 0; j < width; j++) {
                if (k * width + j >= strings.length || strings[k * width + j].length() == 0) {
                    System.out.print(SPACE.repeat(2 + maxLength + 2));
                } else {
                    System.out.print(CORNER_4 + HO_LINE.repeat(maxLength) + CORNER_3 + SPACE.repeat(2));
                }
            }
            System.out.println();
        }
    }

    /**
     * This method gets the lenght
     *
     * @param maxLength
     * @param usedLength
     * @return
     */
    private static int[] getLength(int maxLength, int usedLength) {
        int[] lengthDifference = new int[3];
        lengthDifference[0] = maxLength - usedLength;
        lengthDifference[1] = lengthDifference[0] / 2;
        if (lengthDifference[0] % 2 == 1) {
            lengthDifference[2] = lengthDifference[0] / 2 + 1;
        } else {
            lengthDifference[2] = lengthDifference[0] / 2;
        }
        return lengthDifference;
    }

    /**
     * This method draws the map and the functions for the game.
     *
     * @param game defines which game
     */
    public void map(Game game) {
        System.out.print("\u001B[0m");
        drawMultipleBox(20, 4, 4, game, "", "Balcony", "Balcony", "Balcony",
                "Bathroom", "Livingroom", "Livingroom", "Gym", "Storeroom", "Bedroom", "Bedroom", "Kitchen",
                "", "Toilet", "Secretroom", "Office");
        System.out.println("\u001B[36m");
        drawMultipleBox(24, 3, 4, game, " 1: Print out the possible rooms ",
                " 2: Move between rooms", "3: to inspect the room", "4: show inventory",
                "5: Save data", "6: Load old data", "7: output playtime", "8: go one Room back", "9: Show possible steps back", "10: exit game");
        System.out.print("\u001B[0m");
    }

    /**
     * This method prints out the possible rooms the player can get in.
     * With the activeRoom the method searches for all door connections.
     * It gets the name of each room and prints it out.
     *
     * @param activeRoom Gets where the player is right now.
     * @param game       defines for which game
     */
    public void possibleRoom(int activeRoom, Game game) {
        System.out.print("Possible rooms: ");
        boolean first = false;
        for (int i = 0; i < game.getDoors().size(); i++) {
            if (game.getDoors().get(i).getConnector()[0] == game.getRooms().get(activeRoom)) {
                if (first) {
                    System.out.print(", ");
                }
                System.out.print(game.getDoors().get(i).getConnector()[1].getName());
                first = true;
            } else if (game.getDoors().get(i).getConnector()[1] == game.getRooms().get(activeRoom)) {
                if (first) {
                    System.out.print(", ");
                }
                System.out.print(game.getDoors().get(i).getConnector()[0].getName());
                first = true;
            }
        }
        System.out.println("\n");
    }

    /**
     * This method reads an int between a range.
     * If the number isn't in the range, an error will be printed.
     *
     * @param min defines the minimum number
     * @param max defines the maximum number
     * @return returns the typed number
     */
    public static int readRangedInt(int min, int max) {
        Scanner scn = new Scanner(System.in);
        int input = min - 1;
        try {
            input = scn.nextInt();
        } catch (InputMismatchException var6) {
            scn.nextLine();
        }

        while (input < min || input > max) {
            System.err.println("There was an Error, please repeat your input");
            try {
                input = scn.nextInt();
            } catch (InputMismatchException var5) {
                scn.nextLine();
            }
        }
        return input;
    }

    /**
     * This method prints the Hearts the player has right now.
     *
     * @param amount how many hearts will be printed
     * @param color  which color is used for the heart
     */
    public void printHeart(int amount, String color) {
        if (color.equals("red")) {
            System.out.println("\u001B[31m");
        } else if (color.equals("green")) {
            System.out.println("\u001B[32m");
        }
        for (int i = 0; i < amount; i++) {
            System.out.print("  ░░░░   ░░░░  ");
        }
        System.out.println();
        for (int i = 0; i < amount; i++) {
            System.out.print(" ░░░░░░ ░░░░░░ ");
        }
        System.out.println();
        for (int i = 0; i < amount; i++) {
            System.out.print("  ░░░░░░░░░░░  ");
        }
        System.out.println();
        for (int i = 0; i < amount; i++) {
            System.out.print("    ░░░░░░░    ");
        }
        System.out.println();
        for (int i = 0; i < amount; i++) {
            System.out.print("      ░░░      ");
        }
        System.out.println("\n\u001B[0m");
    }

    /**
     * This method prints the inventory and the price of all stolen items.
     *
     * @param player defines which player
     * @param game   defines which game
     */
    public void printInventory(Player player, Game game) {
        int amountBoxesInRow = 3;
        String[] name = new String[player.getItemList().size()];
        for (int i = 0; i < player.getItemList().size(); i++) {
            name[i] = player.getItemList().get(i).getName() + " " + player.getItemList().get(i).getPrice() + "$";
            price += player.getItemList().get(i).getPrice();
        }

        if (player.getItemList().size() == 0) {
            System.out.println("Your inventory is empty, collect items with the inspect room command");
        } else {
            System.out.println("[Inventory]");
            drawMultipleBox(20, amountBoxesInRow, (player.getItemList().size() / amountBoxesInRow + 1),
                    game, name);
            System.out.println("Your inventory is " + price + " $ worth");
        }
        System.out.println("\n");
    }

    /**
     * This method is used, when an enemy appeared
     */
    public void printEnemy() {
        System.out.println("An enemy appeared");
        System.out.println(" ``  `` `` ``  `` `` ``  `` `` ``  `` `:++/:..` `` ``  `` `` ``  `` `` ``  `` `` ``  `` `` ``  `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``` `` `.:ohmNNmmNNmmmhyyo+/:.``` `` `` ``` `` `` ``` `` `` ``` `` `` ``` \n" +
                "`````````````````````````````````yNNNNNNNmmmmmmmmmmmmNNNs```````````````````````````````````````````\n" +
                " ``` `` `` ``` `` `` ``` `` `` ``mNNNNNNNmhooossyyhhdmmN+ `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``  `` `` ``  `` NNNNNNNyooooo+++o++oohm/`  `` `` ``  `` `` ``  `` `` ``  `` `` ``  \n" +
                " ``` `` `` ``` `` `` ``` `` `` `.NNNNmdmso:-+sooooo+ossy- `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "````````````````````````````````-NNNNhyhso/:+ysoo+sh/./o.`````````````````::/ooo````````````````````\n" +
                "````````````````````````````````:NNNNhyhssooooyhhhsoo+ss.````````````````-sssddm````````````````````\n" +
                " ``` `` `` ``` `` `` ``` `` `-``+NNdyyhhyyysyhhhhhyyssyy` `` ``` `` `` ./y:::hdm ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``+hhdmmmmhhhhhhhhyyyyyyyyyyyy``` `` `` ````-so::/sds/`` `` ``  `` `` ``  \n" +
                " ``` `` `` ``` `` `` ``` ``:hhhyyhdmmmNmmdhhhhhhhhhyyyyys``` ``` `` ./y/::oydo-` ``` `` `` ``` `` ``\n" +
                "``````````````````````````-yhhhhyysoooosyhyyyhhhhhhhhhhhhy.````````.os::/ody+```````````````````````\n" +
                "`` `` ``` `` `` ``` `` ```yhhhhhysoooooshysssyyhhyyyhhhhhhd.`` ```-y+::+ydo-``` `` `` ``` `` `` ``` \n" +
                " ``` `` `` ``` `` `` ``` oyhhhhyyoooooshhhssssyyyyyyhhyyyymmmmN--/y::/ody+ `` `` ``  `` `` ``  `` ``\n" +
                "`` `` ``` `` `` ``` `` `+yyysyyyoooooshddhssssyyyyyyhysssyhmmddNho::+smo-`` ``` `` `` ``  `` `` ``  \n" +
                " ``` `` `` ``` `` `` ``:hyyyyyy+++++oydddhyyyssyyyyhysssssyhmmymo::shh+``` `` `` ``` `` `` ``` `` ``\n" +
                "``````````````````````-hhyyyyyo/++++sdddhhyyyssyyyyhyssssosydmdhyyymo:``````````````````````````````\n" +
                "`` `` ``` `` `` ``` `.yhyyyyho/+++osddddhhysssyyyyyhhysooooshmhdhhhmh+`` `` ``` `` `` ``` `` `` ``` \n" +
                " ``` `` `` ``` `` `` shhhhhys//+++shhdhhhyysssyyssyyyysossooshmNmdmhmmh``` `` `` ``  `` `` ``  `` ``\n" +
                "`` `` ``` `` `` ``` ohhhhhhy///++o:hhhhhyyssssyyssyy++ssoosoooyhhy:mmmN` `` ``` `` `` ``` `` `` ``` \n" +
                "```````````````````:hhhhhhy+///++..hhhhhyyssssyssyyy+`+ooooooosyyyy.````````````````````````````````\n" +
                "````````````````````-shhhh+//+++-`.ddddhhhyyyyyyyhsy/``+oooosyhho:``````````````````````````````````\n" +
                "`` `` ``` `` `` ``` ``.+ho+/+++: `.dmmmdmdhhhhhhhhhy/```/ooss+.`` ``` `` `` ``` `` `` ``` `` `` ``` \n" +
                " ``` `` `` ``` `` `` ````.```` ````yyhdmddhhhhhhhh+/-` `` `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``` `` `` -ydmmmddhhhhhhy- `` ``` `` `` ``` `` `` ``` `` `` ``` `` `` ``` \n" +
                "```````````````````````````````````.hdmmmddddhhhhhy.````````````````````````````````````````````````\n" +
                " ``` `` `` ``` `` `` ``` `` `` ````+mmdmmmddddhhhhhy-` `` `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``` `` .ymmmmdmmmddddhhhhhh+` ``` `` `` ``` `` `` ``` `` `` ``` `` `` ``  \n" +
                " ``` `` `` ``` `` `` ``` `` `` `:dmmmmmmdmmmmddddhhhhho.` `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``` `ommmmmmmmddmmmmddddhhhhhy-`` `` `` ``` `` `` ``` `` `` ``` `` `` ``` \n" +
                "`````````````````````````````.ymmmmmmmdhhhdmmmmddddhhhhhh/``````````````````````````````````````````\n" +
                " ``` `` `` ``` `` `` ``` `` `.sdmmmmmdhhhs-smmmddmdddhhyyy+` ``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "`` `` ``` `` `` ``` `` `` ``` `/dmmdyyyy/` `+mmmdddddhssssso-` `` ``` `` `` ``` `` `` ``` `` `` ``` \n" +
                " ``` `` `` ``` `` `` ``` `` `` `.syo+/:. ``` :dddddddddyssyho``` `` `` ``` `` `` ``` `` `` ``` `` ``\n" +
                "``````````````````````````````````````````````-hdddddddmmmo.````````````````````````````````````````\n" +
                "```````````````````````````````````````````````.ydddmNNh/```````````````````````````````````````````\n" +
                " ``` `` `` ``` `` `` ``` `` `` ``` `` `` ``` `` `oso/. `` `` ``` `` `` ``` `` `` ``` `` `` ``` `` ``");
    }

    /**
     * This method shows with how much items and money the player escaped.
     * If no Item was found, a diffrent message is printed out. 
     *
     * @param player which player is used
     */
    public void listInformation(Player player) {
        for (int i = 0; i < player.getItemList().size(); i++) {
            price += player.getItemList().get(i).getPrice();
        }
        System.out.println("You successfully escaped from the abandoned house. \nYou escaped with " + price + "$");

        if (player.getItemList().size() == 0) {
            System.out.println("You collected no items");
        } else {
            System.out.print("You collected the listed items: ");
            for (int i = 0; i < player.getItemList().size(); i++) {
                System.out.print(player.getItemList().get(i).getName());
                System.out.print(", ");
            }
        }
        System.out.println("You had " + player.getLives() + " lives left");
        printHeart(player.getLives(),"\u001B[32m");
        System.exit(0);
    }

}
