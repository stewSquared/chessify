public abstract class Piece {

    private Point pos;

    protected abstract boolean legalMove(Point delta);

    public abstract String toString();

    public Piece(Point pos) {
	this.pos = pos;
    }
	
    public Point getPosition() {
	return pos;
    }

    public Boolean move(Point delta) {
	if (this.legalMove(delta)) {
	    pos.translate(delta.x, delta.y);
	    return True;
	} else {
	    return False;
	}
    }
}
