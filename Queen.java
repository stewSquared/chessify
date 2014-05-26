import java.awt.Point;

public class Queen extends Piece {

    public Queen(Point inpos, String inteam){
	super(inpos,inteam);
    }
    
    public String toString() {
	return "Q";
    }

    public Boolean legalMove(Point delta, ChessBoard b) {
	Boolean diagonal = Math.abs(delta.x) == Math.abs(delta.y) &&
	    !delta.equals(new Point(0,0));
	Boolean perpendicular = (delta.x == 0 ^ delta.y == 0);
	
	return (diagonal || perpendicular) && this.pathFree(delta, b);
    }
}
