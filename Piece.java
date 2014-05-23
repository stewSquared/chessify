public abstract class Piece {

    private Point pos;

    public Piece(Point pos) {
	this.pos = pos;
    }
	
    public Point getPosition() {
	return pos;
    }
}
