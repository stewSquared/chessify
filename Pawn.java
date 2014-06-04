import java.awt.Point;

public class Pawn extends Piece {

    private boolean moved;
    
    public Pawn(Point inpos, String inteam){
	super(inpos,inteam);
	this.moved = false;
    }
    
    public String toString() {
	return ("P");
    }

    public void move(Point delta, ChessBoard b) {
	super.move(delta, b);
	this.moved = true;
    }
    
    public boolean legalMove(Point delta, ChessBoard b) {
	
        boolean badDirection =
            (team == "white" && delta.y >= 0)
	    || (team == "black" && delta.y <= 0);
	
        if (badDirection) {System.out.println(delta); return false;}
        
        Point target = new Point(pos.x + delta.x,
                                 pos.y + delta.y);
	
	if (delta.x == 0) { // straight?
            return b.isEmpty(target) && pathFree(delta, b)
		&& !((Math.abs(delta.y) == 2) && moved);
	} else if (Math.abs(delta.x) == 1 && Math.abs(delta.y) == 1) { // diagonal?
            return !b.isEmpty(target);
	} else {
	    return false;
	}
    }
}
