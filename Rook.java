import java.awt.Point;

public class Rook extends ChessPiece {
    
    public Rook(String team) { super(team); }

    public Rook(String team, ChessPiece parent) { super(team, parent); }

    public String toString() { return "R"; }

    public ChessPiece move(ChessMove m) { return new Rook(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;

	boolean perpendicular = (dx == 0 ^ dy == 0);
	return super.legalMove(m, board)
	    && perpendicular
	    && m.pathFree(board);
    }
}
