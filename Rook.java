public class Rook extends Piece {

    public Rook(Point pos, String team) {
	return super(pos, team);
    }

    public String toString() {
	return "R";
    }

    public static boolean legalMove(Point delta, ChessBoard b) {
	if (!(delta.x == 0 ^ delta.y == 0)) return false;

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
