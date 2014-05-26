import java.awt.Point;
import java.util.regex.*;

public class ChessMove{

    public final static Pattern SMITH_NOTATION_RE =
	Pattern.compile("([a-h][1-8]){2}");

    protected Point orig;
    protected Point dest;
    protected String movestr;

    public ChessMove(String movestr) {
	assert SMITH_NOTATION_RE.matcher(movestr).matches()
	    : "Caller should validate move string before constructing.";

	this.orig = new Point(((int) (movestr.charAt(0) - 'a')),
			      8 - Integer.parseInt(movestr.substring(1,2)));
	this.orig = new Point(((int) (movestr.charAt(2) - 'a')),
			      8 - Integer.parseInt(movestr.substring(3,4)));
	this.movestr = movestr;
    }

    public ChessMove(Point orig, Point dest) {
	this.orig = orig;
	this.dest = dest;
	String origFile = "" + (char) (((int) 'a') + orig.x);
	String origRank = "" + (8 - orig.y);
	String destFile = "" + (char) (((int) 'a') + dest.x);
	String destRank = "" + (8 - dest.y);
	this.movestr = origFile + origRank + 'x' + destFile + destRank;
    }

    public static boolean validSmith(String movestr) {
	return SMITH_NOTATION_RE.matcher(movestr).matches();
    }

    // At present, the move is printed in Smith Notation, which
    // requires no knowledge of the board's state.
    public String toString() {
	return movestr;
    }

    public Point getOrig() {
	return orig.getLocation(); // return a defensive copy
    }

    public Point getDest() {
	return dest.getLocation(); // return a defensive copy
    }

    public Point getDelta() {
	return new Point(dest.x - orig.x, dest.y - orig.y);
    }
}
