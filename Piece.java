import java.awt.Point;

public abstract class Piece {
    
    protected Point pos;
    protected String team;

    public abstract Boolean legalMove(Point delta, ChessBoard b);
    
    public abstract String toString();
    
    public Piece(Point inpos, String inteam) {
	pos = new Point(inpos);
	team = new String(inteam);
    }

    public Piece(){
	pos=new Point();
	team=new String();
    }
    
    public String getTeam() {
	return team;
    }
    
    public Point getPosition() {
	return pos;
    }
    
    public void move(Point delta, ChessBoard b) {
	pos.translate(delta.x, delta.y);
    }
    
    // Are all the positions between this.pos and dest open?
    public Boolean pathFree(Point delta, ChessBoard b) {
	Point dir = new Point(Math.abs(delta.x)/delta.x,
			      Math.abs(delta.y)/delta.y);
	
	Point path = new Point(pos);
	path.translate(dir.x, dir.y);
	
	Point dest = new Point(pos);
	dest.translate(delta.x, delta.y);
	
	while (!path.equals(dest)) {
	    if (!b.isEmpty(path)) return false;
	    path.translate(dir.x, dir.y);
	}
	
	return true;
    }
}
