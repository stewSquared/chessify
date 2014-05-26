import java.awt.Point;

public class Bishop extends Piece {

	public Bishop(Point inpos, String inteam){
		super(inpos,inteam);
	}

    public String toString() {
		return "B";
    }

    public Boolean legalMove(Point delta, ChessBoard b) {
		if (Math.abs(delta.x) != Math.abs(delta.y) ||
			delta.equals(new Point(0,0))) return false;

		Point d = new Point();
		d=delta;

		Point p = new Point();
		p=pos;
		p.translate(d.x,d.y);

		Point dest = new Point();
		dest=pos;
		dest.translate(delta.x,delta.y);

		while (!p.equals(dest)) {
			if (!b.isEmpty(p)) return false;
			p.translate(d.x,d.y);
		}

		return true;
    }
}
