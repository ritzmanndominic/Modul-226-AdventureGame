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
                arrayList.add(game.getRooms().get(i).getItemsArrayList().get(j).getName());
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
}
