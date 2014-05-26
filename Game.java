public class Game {

    public static void main(String args[]) {
    }

    public static void textPlay(ChessBoard board) {
	Player white = new TextPlayer("White");
	Player black = new TextPlayer("Black");

	boolean whiteTurn = true;

	while (true) {// while (!board.checkMate()) {
	    Player player = whiteTurn ? white : black;
	    System.out.println("" + player + " to go:\n" + board );
	    player = null;

	    ChessMove m = white.move(board);
	    //while(!board.legalMove(m)) {}
	}
    }
}
