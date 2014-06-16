import java.awt.Point;
import java.util.regex.*;

public class ChessMove{

    public static final Pattern SMITH_NOTATION_RE =
	Pattern.compile("([a-h][1-8]){2}[EcC]?[NBRQ]?");
    private static final Pattern promoting = Pattern.compile(".*[NBRQ]$");

    protected final Point orig;
    protected final Point dest;
    protected final Point delta;
    protected final String movestr;

    //private final boolean enPassant;
    //private final boolean shortCastle;
    //private final boolean longCastle;
    private final char promotion;

    public ChessMove(String movestr) {
	assert SMITH_NOTATION_RE.matcher(movestr).matches()
	    : "Caller should validate move string before constructing.";

	this.orig = new Point(((int) (movestr.charAt(0) - 'a')),
			      8 - Integer.parseInt(movestr.substring(1,2)));
	this.dest = new Point(((int) (movestr.charAt(2) - 'a')),
			      8 - Integer.parseInt(movestr.substring(3,4)));
	this.delta = new Point(dest.x - orig.x,
			       dest.y - orig.y);
	this.promotion = promoting.matcher(movestr).matches() ?
	    movestr.charAt(movestr.length() - 1) : 0;
	this.movestr = movestr;
    }

    public ChessMove(Point orig, Point dest) {
	this.orig = orig;
	this.dest = dest;
	this.delta = new Point(dest.x - orig.x,
			       dest.y - orig.y);
	String origFile = "" + (char) (((int) 'a') + orig.x);
	String origRank = "" + (8 - orig.y);
	String destFile = "" + (char) (((int) 'a') + dest.x);
	String destRank = "" + (8 - dest.y);
	this.promotion = 0;
	this.movestr = origFile + origRank + 'x' + destFile + destRank;
    }

    public static boolean validSmith(String movestr) {
	return SMITH_NOTATION_RE.matcher(movestr).matches();
    }

    public String toString() { return movestr; }
    public Point getOrig() { return orig.getLocation(); }
    public Point getDest() { return dest.getLocation(); }
    public Point getDelta() { return delta.getLocation(); }
    public char getPromotion() { return promotion; }

    /** 
     * pre: destination is on the same rank/file/diagonal line.
     *
     * post: return true if all spaces between orig and dest open
     */
    public boolean pathFree(ChessBoard b) {
	Point dir = new Point(delta.x == 0 ? 0 : Math.abs(delta.x)/delta.x,
			      delta.y == 0 ? 0 : Math.abs(delta.y)/delta.y);
	if (dir.equals(new Point(0,0))) return true;
	
	Point path = orig.getLocation();
	path.translate(dir.x, dir.y);
	
	while (!path.equals(dest)) {
	    if (!b.isEmpty(path)) return false;
	    path.translate(dir.x, dir.y);
	}
	
	return true;
    }

    /**
     * pre: given a king's castling move, m, return the accompanying Rook's move.
     */
    public ChessMove rookCastlingMove(ChessBoard board) {
	if (delta.x > 0) {
	    return new ChessMove(new Point(board.size.x - 1, orig.y),
				 new Point(orig.x + 1, orig.y));
	} else {
	    return new ChessMove(new Point(0, orig.y),
				 new Point(orig.x - 1, orig.y));
	}
    }
}
