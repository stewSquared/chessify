import java.awt.Point;

public class King extends ChessPiece {

    public King(String team) { super(team); }
    
    public King(String team, ChessPiece parent) { super(team, parent); }
    
    public String toString() { return "K"; }

    public ChessPiece move() { return new King(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;

        return super.legalMove(m, board)
	    && Math.abs(dx) <= 1
	    && Math.abs(dy) <= 1;
    }
}
