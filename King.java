import java.awt.Point;

public class King extends ChessPiece {

    public King(String team) { super(team); }
    
    public King(String team, ChessPiece parent) { super(team, parent); }
    
    public String toString() { return "K"; }

    public ChessPiece move() { return new King(team, this); }
    
    public boolean legalMove(ChessMove m, ChessBoard board) {
	int dx = m.getDelta().x;
	int dy = m.getDelta().y;
	Point orig = m.getOrig();

	if (!moved()
	    && Math.abs(dx) == 2
	    && Math.abs(dy) == 0
	    && board.legalMove(m.rookCastlingMove(board))
	    && !board.getPiece(m.rookCastlingMove(board).getOrig()).moved()) {

	    Point inter = new Point(orig.x + ((dx > 0) ? 1 : -1),
				    orig.y);

	    return super.legalMove(m, board)
		&& !board.kingInCheck(board.getPiece(orig).team)
		&& board.legalMove(new ChessMove(orig, inter));
	}

        return super.legalMove(m, board)
	    && Math.abs(dx) <= 1
	    && Math.abs(dy) <= 1;
    }
}
