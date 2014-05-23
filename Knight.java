public class Knight extends Piece {
    public String toString() {
	return "N";
    }

    public boolean legalMove(Point move) {
	return Math.abs(move.x) + Math.abs(move.y) == 3 &&
	    Math.abs(move.x) == 1 ^ Math.abs(move.y) == 1;
    }
}
