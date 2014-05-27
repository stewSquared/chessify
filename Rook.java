import java.awt.Point;

public class Rook extends Piece {
    
    public Rook(Point inpos, String inteam){
	super(inpos,inteam);
    }
    
    public String toString() {
	return "R";
    }
    
    public boolean legalMove(Point delta, ChessBoard b) {
	boolean perpendicular = (delta.x == 0 ^ delta.y == 0);
	return perpendicular && this.pathFree(delta, b);
    }
}
