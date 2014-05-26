public abstract class Player {

    protected static int playerCount = 0;

    protected String name;

    public Player() {
	Player.playerCount++;
	this.name = "Player" + Player.playerCount;
    }

    public Player(String name) {
	Player.playerCount++;
	this.name = name;
    }

    public String toString() {
	return name;
    }

    public abstract ChessMove move(ChessBoard board);
}
