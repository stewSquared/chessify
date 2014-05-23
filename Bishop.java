public class Bishop extends Piece {

    public Bishop(Point pos, String team) {
	return super(pos, team);
    }

    public String toString() {
	return "B";
    }

    public boolean legalMove(Point delta, ChessBoard b) {
	if (Math.abs(delta.x) != Math.abs(delta.y) ||
	    delta.equals(new Point(0,0))) return false;

	Point d = new Point(delta.x * 1, delta.y * 1);

	Point p = new Point(this.pos);
	p.translate(d);

	point dest = new Point(this.pos);
	dest.translate(delta);

	while (!p.equals(dest)) {
	    if (!b.isEmpty(p)) return false;
	    p.translate(d);
	}

	return true;
    }
}
