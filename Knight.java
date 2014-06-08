import java.awt.Point;

public class Knight extends ChessPiece {
    
    public Knight(String team) { super(team); }
    
    public Knight(String team, ChessPiece parent) { super(team, parent); }

    public String toString() { return "N"; }
    
    public ChessPiece move() { return new Knight(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;

	return super.legalMove(m, board)
	    && Math.abs(dx) + Math.abs(dy) == 3
	    && (Math.abs(dx) == 1) ^ (Math.abs(dy) == 1);
    }
}
