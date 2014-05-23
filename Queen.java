public class Queen extends Piece {

    public String toString() {
	return "Q";
    }

    public boolean legalMove(Point delta) {
	return Rook.legalMove(delta) || Bishop.legalMove(delta);
    }
}
