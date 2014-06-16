import java.awt.Point;
import java.io.*;
import java.util.Vector;

public class ChessBoard{

    public static final String BLACK = "black";
    public static final String WHITE = "white";

    public final ChessPiece[][] board;
    public final Point size;

    public ChessBoard() { this("boards/default.cb"); }

    public ChessBoard(Point size, ChessPiece[][] board) {
        this.size = size;
        this.board = board;
    }

    public ChessBoard(String boardFile) {
	System.out.println(boardFile);
        try (BufferedReader br = new BufferedReader(new FileReader(boardFile))) {
	    String line = br.readLine();
            int width = Integer.parseInt(line.split(" ")[0]);
            int height = Integer.parseInt(line.split(" ")[1]);
	    
            this.size = new Point(width, height);
            this.board = new ChessPiece[width][height];
                
            for (int y = 0; y < height; y++) {
                line = br.readLine();
                for (int x = 0; x < width; x++) {
                    char pc = line.charAt(x);
                    board[x][y] = ChessPiece.fromChar(Character.isLowerCase(pc) ?
                                                      BLACK : WHITE, pc);
                }
            }
        } catch (IOException e) {
	    throw new RuntimeException(e);
        }
    }

    public ChessPiece getPiece(Point pos) {
        return board[pos.x][pos.y];
    }

    public ChessPiece getPiece(int tx, int ty) {
        return board[tx][ty];
    }
    
    public boolean isEmpty(Point pos) {
        return (getPiece(pos)==null);
    }

    public boolean inBounds(Point pos) {
        return (pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y);
    }

    private void remove(Point pos) {
        board[pos.x][pos.y] = null;
    }

    private void place(ChessPiece pc, Point pos) {
        board[pos.x][pos.y] = pc;
    }
    
    public boolean legalMove(ChessMove m) { return legalMove(m, null); }

    public boolean legalMove(ChessMove m, String team) {
        Point orig = m.getOrig();
        Point dest = m.getDest();
        Point delta = m.getDelta();

        // Does the piece even exist?
        if (isEmpty(orig)) return false;

        //Are both positions within bounds?
        if (!(inBounds(orig) && inBounds(dest))) return false;

        // Does the piece belong to the player trying to move it?
        if ((team != null) && !getPiece(orig).team.equals(team)) return false;
        
        //Is this move legal according to the piece?
        if (!getPiece(orig).legalMove(m, this)) return false;
        
        // Does the move put that piece's king in check?
        if (moveRevealsCheck(m)) return false;

        return true;
    }

    public Vector<Point> occupiedBy(String team) {
	Point pos;
	ChessPiece pc;
	Vector<Point> occupied = new Vector<Point>(16);
	
	for (int x = 0; x < size.x; x++) {
	    for (int y = 0; y < size.y; y++) {
		pos = new Point(x,y);
		pc = getPiece(pos);
		if (pc != null && pc.team == team) occupied.add(pos);
	    }
	}
	return occupied;
    }

    /**
     * pre: The piece indicated by move exists. This function is only
     * expected to be called from legalMove.
     *
     * post: Return true if performing the move leaves that team's
     * king in check.
     */
    private boolean moveRevealsCheck(ChessMove m) {
	return move(m).kingInCheck(getPiece(m.getOrig()).team);
    }

    public boolean kingInCheck(String team) {
	String enemyTeam = team.equals(BLACK) ? WHITE : BLACK;
	return !kingCheckers(enemyTeam).isEmpty();
    }

    public Vector<Point> kingCheckers(String team) {
	String enemyTeam = team.equals(BLACK) ? WHITE : BLACK;
	Point kingPos = kingPos(enemyTeam);
	Vector<Point> checkers = new Vector<Point>();

	for (Point p : occupiedBy(team)) {
	    ChessPiece pc = getPiece(p);
	    if (pc != null && pc.team == team
		&& pc.legalMove(new ChessMove(p, kingPos), this))

		checkers.add(p);
	}
	return checkers;
    }

    public Point kingPos(String team) {
	for (Point p : occupiedBy(team))
	    if (getPiece(p).toString().equals("K"))
		return p;
	return null;
    }

    /**
     * Calculate whether team's king is checkmated. The king is free
     * if any of the following are true:
     *
     * - There are noChecks
     * - The kingCanEscape
     * - The checkIsBlockable
     *
     */
    public boolean checkMate(String team) {
	String enemyTeam = team.equals(BLACK) ? WHITE : BLACK;
	Point kingPos = kingPos(team);
	Vector<Point> checkers = kingCheckers(enemyTeam);
	boolean noChecks = checkers.size() < 1;
	boolean kingCanEscape = false;
	boolean checkIsBlockable = false;

	if (noChecks) return false;

	for (int dx = -1; dx <= 1; dx++) {
	    for (int dy = -1; dy <= 1; dy++) {
		Point escapePos = kingPos.getLocation();
		escapePos.translate(dx, dy);
		if (legalMove(new ChessMove(kingPos, escapePos))) {
		    kingCanEscape = true;
		}
	    }
	}
	if (kingCanEscape) return false;

	if (checkers.size() == 1) {
	    for (Point pathPos : new ChessMove(checkers.get(0),
					       kingPos).path()) {
		for (Point allyPos : occupiedBy(team)) {
		    ChessPiece pc = getPiece(allyPos);
		    if (( pc.toString().equals("R")
			  || pc.toString().equals("B")
			  || pc.toString().equals("Q") )
			&& pc.legalMove(new ChessMove(allyPos, pathPos),
					this))
			checkIsBlockable = true;
		}
	    }
	}
	return !checkIsBlockable; // && !kingCanEscape && !noChecks;
    }
    
    /**
     * Return the chessboard that would result from a given move.
     */
    public ChessBoard move(ChessMove m) {
        Point orig = m.getOrig();
        Point dest = m.getDest();
        Point delta = m.getDelta();
        ChessPiece[][] newBoard = new ChessPiece[size.x][size.y];

        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                newBoard[x][y] = board[x][y];
            }
        }

        ChessPiece pc = getPiece(orig);
        newBoard[orig.x][orig.y] = null;
        newBoard[dest.x][dest.y] = pc.move(m);

        boolean pawnPassing = (getPiece(orig).toString().equals("P")
                               && Math.abs(m.getDelta().y) == 2);

        boolean castling = (getPiece(orig).toString().equals("K")
                            && Math.abs(m.getDelta().x) == 2);

        if (pawnPassing) {
            Point passingSquare = new Point(dest.x,
                                            (dest.y + orig.y)/2);

            return new PassedBoard(size,
                                   newBoard,
                                   move(orig, passingSquare));
        } else if (castling) {
            return new ChessBoard(size, newBoard).move(m.rookCastlingMove(this));
        } else {
            return new ChessBoard(size, newBoard);
        }
    }

    private ChessBoard move(Point orig, Point dest) {
        return move(new ChessMove(orig, dest));
    }

    public void displayBoard() {
        System.out.print(this);
    }
    
    public String toString() {
        String boardstr = "";
        
        String whiteSqr = " .";
        String blackSqr = " ,";

        boardstr += (" X"+"  ");
        for (char file = 'a'; file < 'a'+size.x ; file++) boardstr += (" "+file);
        boardstr += ("  "+" X");
        boardstr += '\n';

        boardstr += ("  "+" +");
        for (int i = 0; i < size.x; i++) boardstr += ("--");
        boardstr += ("-+"+"  ");
        boardstr += '\n';
        
        for (int ty = 0; ty < size.y; ty++) {
            int rank = size.y - ty;

            boardstr += (" "+rank + " |");

            for (int tx = 0; tx < size.x; tx++) {
                if (getPiece(tx, ty) == null) {
                    boardstr += ((tx + ty)%2 == 0 ? whiteSqr : blackSqr);
                } else {
                    String piece;
                    if (getPiece(tx, ty).team.equals("white")) {
                        piece = (getPiece(tx,ty).toString());
                    } else {
                        piece = ((getPiece(tx, ty).toString()).toLowerCase());
                    }
                    boardstr += (" " + piece);
                }
            }

            boardstr += (" |" + " "+rank);
            boardstr += '\n';
        }

        boardstr += ("  "+" +");
        for (int i = 0; i < size.x; i++) boardstr += ("--");
        boardstr += ("-+"+"  ");
        boardstr += '\n';
        
        boardstr += (" X"+"  ");
        for (char file = 'a'; file < 'a'+size.x ; file++) boardstr += (" "+file);
        boardstr += ("  "+" X");

        return boardstr;
    }
}
