public class Pawn extends Piece {

    public Pawn(Point pos, String team) {
	this.hasMoved = false;
	return super(pos, team);
    }

    public String toString() {
	return "P";
    }

    public boolean legalMove(Point delta, ChessBoard b) {
	if (badDirection) return false;

	boolean emptyTarget = b.isEmpty(new Point(pos).translate(delta));
	int dist = Math.abs(delta.y);
	if (dist == 1) {
	    return emptyTarget ? delta.x == 0 : Math.abs(delta.x) == 1;
	} else {
	    Point path = new Point(pos);
	    path.translate(0, delta.y / Math.abs(delta.y));
	    return dist == 2
		&& delta.x == 0
		&& emptyTarget
		&& b.isEmpty(path);
	}
    }
}
