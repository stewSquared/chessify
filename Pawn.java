import java.awt.Point;

public class Pawn extends Piece {

    public Pawn(Point inpos, String inteam){
		super(inpos,inteam);
	}

    public String toString() {
		return ("P");
    }

    public boolean legalMove(Point delta, ChessBoard b) {
		//if (badDirection) return false; //?? Commented out, assuming badDirection is a placeholder

		Point tmp = new Point();
		tmp = pos;
		tmp.translate(delta.x,delta.y);
		boolean emptyTarget = b.isEmpty(tmp);
		int dist = Math.abs(delta.y);
		if (dist == 1) {
			return (emptyTarget ? delta.x == 0 : Math.abs(delta.x) == 1);
		} else {
			Point path = new Point();
			path=pos;
			path.translate(0, delta.y / Math.abs(delta.y));
			return (dist == 2
			&& delta.x == 0
			&& emptyTarget
			&& b.isEmpty(path));
		}
    }
}
