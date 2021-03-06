public class Game {

    public static void main(String args[]) {
	textPlay(args.length == 0 ? new ChessBoard()
		 : new ChessBoard(args[0]));
    }

    public static void textPlay(ChessBoard board) {
	Player white = new TextPlayer("white");
	Player black = new TextPlayer("black");

	boolean whiteTurn = true;

	while (true) {// while (!board.checkMate()) {
	    Player player = whiteTurn ? white : black;
	    System.out.println("" + player + " to go:");
	    System.out.println("\n"+board+"\n");

	    ChessMove m = player.move(board);
	    while(!board.legalMove(m, player.toString())) {
		System.out.println(""+m+" is not a legal move for "+player);
		m = player.move(board);
	    }
	    board = board.move(m);
	    whiteTurn = ! whiteTurn;
	}
    }
}
