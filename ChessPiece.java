import java.awt.Point;

public abstract class ChessPiece {
    
    final public ChessPiece parent;
    final public String team;

    public ChessPiece(String team) { this(team, null); }

    public ChessPiece(String team, ChessPiece parent) {
	this.team = team;
	this.parent = parent;
    }

    public abstract String toString();

    public abstract ChessPiece move();

    public String getTeam() { return team; }

    protected boolean moved() {
	return parent != null;
    }

    public boolean legalMove(ChessMove m, ChessBoard board) {
	// Note that a (0,0) move is illegal by this logic
	return board.getPiece(m.getDest()) == null 
	    || board.getPiece(m.getDest()).getTeam() != this.getTeam();
    }
}
