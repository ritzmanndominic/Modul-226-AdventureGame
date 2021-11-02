package ch.noseryoung.blj;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class StoreScore {

    public static void saveData(String fileWriteTo, Player player, Game game) {
        ArrayList<String> arrayList = new ArrayList<>();
        Date date = new Date();
        Timestamp currentTime = new Timestamp(date.getTime());

        arrayList.add(game.getRooms().get(game.getActiveRoom()).getName());
        arrayList.add(String.valueOf(player.getLives()));

        arrayList.add(String.valueOf((currentTime.getTime() - player.getStartTime().getTime() + player.getGameTime())));

        for (int i = 0; i < game.getRooms().size(); i++) {
            for (int j = 0; j < game.getRooms().get(i).getItemsArrayList().size(); j++) {
                //arrayList.add(game.getRooms().get(i).getItemsArrayList().get(j).getName());
                //arrayList.add(player.getItemList().get(j).getName());
                arrayList.add(game.getRooms().get(i).getItemsArrayList().get(j).getName() + game.getRooms().get(i).getItemsArrayList().get(j).getPrice());
            }
        }

        for (int i = 0; i < player.getItemList().size(); i++) {
            arrayList.add(player.getItemList().get(i).getName());
        }

        try {
            FileOutputStream fos = new FileOutputStream(fileWriteTo);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(arrayList);
            oos.close();
        } catch (IOException e) {
            System.out.println("There was an error, file could not be saved");
        }
    }

    public static void loadData(String fileToReadForm, Player player, Game game) {
        try {
            FileInputStream fis = new FileInputStream(fileToReadForm);
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<String> arrayList = (ArrayList<String>) ois.readObject();
            ois.close();

            //set active room
            for (int i = 0; i < game.getRooms().size(); i++) {
                if (arrayList.get(0).equals(game.getRooms().get(i).getName())) {
                    game.setActiveRoom(i);
                }
            }

            //set lives
            player.setLives(Integer.parseInt(arrayList.get(1)));

            //set game time
            player.setGameTime(Long.parseLong(arrayList.get(2)));

            //set empty room
            for (int i = 3; i < 13; i++) {
                if (arrayList.get(i).isEmpty()) {
                    game.getRooms().get(i).setItemsArrayList(null);
                }
            }

            //remove
            for (int i = 0; i < 13; i++) {
                arrayList.remove(0);
            }

            //set item list
            for (int j = 0; j < game.getItems().size(); j++) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).equals(game.getItems().get(j).getName())) {
                        player.getItemList().add(game.getItems().get(i));
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("There was an error, data could not be loaded");
        }
    }
}
