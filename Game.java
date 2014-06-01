public class Game {

    public static void main(String args[]) {
	textPlay(new ChessBoard());
    }

    public static void textPlay(ChessBoard board) {
	Player white = new TextPlayer("white");
	Player black = new TextPlayer("black");

	boolean whiteTurn = true;

	while (true) {// while (!board.checkMate()) {
	    Player player = whiteTurn ? white : black;
	    System.out.println("" + player + " to go:\n" + board );

	    ChessMove m = white.move(board);
	    // TODO The problem here is the `legalMove` doesn't do a
	    // complete check. `move` does, but it also moves the
	    // piece. For now, I'll write the code I'd like to write
	    // for Game. We can resolve the API's later.
	    while(!board.legalMove(m)) { // pass team too
		System.out.println(""+m+" is not a legal move for "+player);
		m = player.move(board);
	    }
	    board.move(m);
	    whiteTurn = ! whiteTurn;
	}
    }
}
