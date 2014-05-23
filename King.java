public class King extends Piece {

    public String toString() {
	return "K";
    }

    public boolean legalMove(Point delta) {
	return Math.abs(deltay.x) <= 1 &&
	    Math.abs(delta.y) <= 1 &&
	    !delta.equals(new Point(0,0));
    }
}
