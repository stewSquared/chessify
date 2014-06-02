import java.awt.Point;

public class Pawn extends Piece {

    public Pawn(Point inpos, String inteam){
		super(inpos,inteam);
	}

    public String toString() {
		return ("P");
    }

    public boolean legalMove(Point delta, ChessBoard b) {
	
	boolean badDirection =
	    (team == "white" && delta.y >= 0)
	      || (team == "black" && delta.y <= 0);

	if (badDirection) {System.out.println(delta); return false;}
	    
	Point target = new Point(pos.x + delta.x,
				 pos.y + delta.y);

	return (delta.x == 0) ? // straight move?
	    b.isEmpty(target) && pathFree(delta, b) :
	    (Math.abs(delta.x) == 1 && Math.abs(delta.y) == 1) ? // diagonal move?
	    !b.isEmpty(target) : false;
    }
}
