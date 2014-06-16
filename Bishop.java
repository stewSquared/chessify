import java.awt.Point;

public class Bishop extends ChessPiece {
    
    public Bishop(String team) { super(team); }
    
    public Bishop(String team, ChessPiece parent) { super(team, parent); }

    public String toString() { return "B"; }

    public ChessPiece move(ChessMove m) { return new Bishop(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;

        boolean diagonal = Math.abs(dx) == Math.abs(dy);
	return super.legalMove(m, board)
	    && diagonal
	    && m.pathFree(board);
    }
}
