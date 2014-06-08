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

    public ChessPiece move() {
	return new Pawn(team, this, false);
    }

    public Pawn pass() {
	return new Pawn(team, this, true);
    }

    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;
	
        boolean badDirection =
            (team == "white" && dy >= 0)
	    || (team == "black" && dy <= 0);
	
        if (badDirection) return false;
        
	
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
