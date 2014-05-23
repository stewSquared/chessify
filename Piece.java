public abstract class Piece {

    private Point pos;
    private String team;

    public abstract boolean legalMove(Point delta);

    public abstract String toString();

    public Piece(Point pos, String team) {
	this.pos = pos;
	this.team = team;
    }

    public string getTeam() {
	return this.team;
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
