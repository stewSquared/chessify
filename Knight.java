import java.awt.Point;

public class Knight extends Piece {

	public Knight(Point inpos, String inteam){
		super(inpos,inteam);
	}

    public String toString() {
		return "N";
    }

    public Boolean legalMove(Point delta, ChessBoard b) {
		return ((Math.abs(delta.x) + Math.abs(delta.y) == 3) &&
			((Math.abs(delta.x) == 1) ^ (Math.abs(delta.y) == 1)) &&
			(!delta.equals(new Point(0,0))));
    }
}
