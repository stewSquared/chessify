import java.awt.Point;

public class Rook extends Piece {
    
    public Rook(Point inpos, String inteam){
	super(inpos,inteam);
    }
    
    public String toString() {
	return "R";
    }
    
    public Boolean legalMove(Point delta, ChessBoard b) {
	Boolean perpendicular = (delta.x == 0 ^ delta.y == 0);
	return perpendicular && this.pathFree(delta, b);
    }
}
