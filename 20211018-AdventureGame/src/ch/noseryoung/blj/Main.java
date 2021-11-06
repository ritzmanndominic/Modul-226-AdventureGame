package ch.noseryoung.blj;

public class Main {

    public static void main(String[] args) {
        IO io = new IO();
        Game game = new Game();
        Player player = new Player();
        Player player1 = new Player(player.getItemList(), 3);
        io.switcher(game, player1);
    }
}
