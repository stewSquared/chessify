public class Knight extends Piece {

    public String toString() {
	return "N";
    }

    protected boolean legalMove(Point delta) {
	return Math.abs(move.x) + Math.abs(move.y) == 3 &&
	    ((Math.abs(move.x) == 1) ^ (Math.abs(move.y) == 1)) &&
	    !delta.equals(new Point(0,0));
    }
}