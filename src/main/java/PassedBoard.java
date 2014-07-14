import java.awt.Point;

/**
 * This class allows for a super simple and 100% bug-free[*]
 * implementation of en passant. When a pawn is passed two-spaces
 * forward, ChessBoard.move returns a PassedBoard instead of a regular
 * chessboard. Constructor argument PassingBoard is used as the next
 * board's parent if the pawn is capturing in passing.
 */
public class PassedBoard extends ChessBoard {

    public final ChessBoard passingBoard;

    public PassedBoard(Point size, ChessPiece[][] board,
		       ChessBoard passingBoard) {
	super(size, board);
	this.passingBoard = passingBoard;
    }

    public boolean legalmove(ChessMove m) { return legalMove(m, null); }

    public boolean legalMove(ChessMove m, String team) {
	return super.legalMove(m, team)
	    || (getPiece(m.getOrig()).toString().equals("P")
		&& passingBoard.legalMove(m));
    }

    public ChessBoard move(ChessMove m) {
	return inPassing(m) ? passingBoard.move(m)
	    : super.move(m);
    }

    private boolean inPassing(ChessMove m) {
	return getPiece(m.getOrig()).toString().equals("P")
	    && this.getPiece(m.getDest()) == null
	    && passingBoard.getPiece(m.getDest()) != null;
    }
}
