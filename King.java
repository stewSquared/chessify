import java.awt.Point;

public class King extends Piece {

	public King(Point inpos, String inteam){
		super(inpos,inteam);
	}

    public String toString() {
		return "K";
    }

    public Boolean legalMove(Point delta, ChessBoard b) {
		return Math.abs(delta.x) <= 1 &&
			Math.abs(delta.y) <= 1 &&
			!delta.equals(new Point(0,0));
    }
}
