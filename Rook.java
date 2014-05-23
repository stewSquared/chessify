public class Rook extends Piece {

    public String toString() {
	return "R";
    }

    public static boolean legalMove(Point delta, ChessBoard b) {
	if (!(delta.x == 0 ^ delta.y == 0)) return false;
	foreach(Point dest : path) {
	    if (!b.isEmpty) return false; // TODO check for king
	}
	return true
    }
}
