import java.awt.Point;

public class Pawn extends ChessPiece {

    final public boolean isPassing;

    public Pawn(String team) { this(team, null, false); }

    public Pawn(String team, ChessPiece parent) {
	this(team, parent, false);
    }

    private Pawn(String team, ChessPiece parent, boolean isPassing) {
	super(team, parent);
	this.isPassing = isPassing;
    }

    public String toString() { return "P"; }

    public ChessPiece move(ChessMove m) {
	return ((m.getPromotion() == 0) ?
		new Pawn(team, this, false) :
		ChessPiece.fromChar(this.team,
				    m.getPromotion(),
				    (ChessPiece) this));
    }

    public Pawn pass() {
	return new Pawn(team, this, true);
    }

    private boolean promotePosition(Point pos, ChessBoard board) {
	int finalRow = ((team == ChessBoard.BLACK) ? board.size.y - 1 :
			(team == ChessBoard.WHITE) ? 0 : -1);
	return pos.y == finalRow;
    }

    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;
	
        boolean badDirection =
            (team == ChessBoard.WHITE && dy >= 0)
	    || (team == ChessBoard.BLACK && dy <= 0);
	
        if (badDirection) return false;

	if (!(promotePosition(m.getDest(), board)
	      ^ m.getPromotion() == 0)) return false;
	
	if (dx == 0) { // straight?
            return board.isEmpty(m.getDest()) && m.pathFree(board)
		&& !((Math.abs(dy) == 2) && moved());
	} else if (Math.abs(dx) == 1 && Math.abs(dy) == 1) { // diagonal?
            return !board.isEmpty(m.getDest());
	} else {
	    return false;

	}
    }
}
