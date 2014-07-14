import java.awt.Point;

public class Queen extends ChessPiece {

    public Queen(String team) { super(team); }
    
    public Queen(String team, ChessPiece parent) { super(team, parent); }
    
    public String toString() { return "Q"; }

    public ChessPiece move(ChessMove m) { return new Queen(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;

        boolean diagonal = Math.abs(dx) == Math.abs(dy);
	boolean perpendicular = (dx == 0 ^ dy == 0);
	return super.legalMove(m, board)
	    && (diagonal ^ perpendicular)
	    && m.pathFree(board);
    }
}
