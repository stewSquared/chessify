import java.awt.Point;

public class King extends Piece {

    public King(Point pos, String team) {
	return super(pos, team);
    }

    public String toString() {
	return "K";
    }

    public boolean legalMove(Point delta) {
	return Math.abs(deltay.x) <= 1 &&
	    Math.abs(delta.y) <= 1 &&
	    !delta.equals(new Point(0,0));
    }
}
