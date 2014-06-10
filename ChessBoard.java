import java.awt.Point;

public class ChessBoard{

    public static final String DEFAULT_BOARD_INIT_STR[] = new String [] {
        "Rblack","Nblack","Bblack","Qblack","Kblack","Bblack","Nblack","Rblack",
        "Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack",
        "","","","","","","","",
        "","","","","","","","",
        "","","","","","","","",
        "","","","","","","","",
        "Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite",
        "Rwhite","Nwhite","Bwhite","Qwhite","Kwhite","Bwhite","Nwhite","Rwhite"
    };

    public final ChessPiece[][] board;
    private final Point size;

    public ChessBoard() {
        this(new Point(8,8), DEFAULT_BOARD_INIT_STR);
    }

    public ChessBoard(Point size, ChessPiece[][] board) {
	this.size = size;
	this.board = board;
    }

    public ChessBoard(Point size, String initstr[]) {
	this.size = size;
        if (initstr.length != size.x * size.y) {
	    throw new IllegalArgumentException(""+initstr+" is not size "+size);
        }
	board = new ChessPiece[size.x][size.y];

        int curindex=0;
        for (String cur : initstr) {
	    Point pos = new Point(curindex % size.x,
				  curindex / size.y);

            if (cur.length() == 0) {
		place(null, pos);
            } else {
                String pc = cur.substring(0,1); //Type
                String team = cur.substring(1,cur.length()); //Team
                
                if (pc.equals("P")) {
                    place(new Pawn(team), pos);
                }
                else if (pc.equals("K")) {
		    place(new King(team), pos);
                }
                else if (pc.equals("Q")) {
                    place(new Queen(team), pos);
                }
                else if (pc.equals("N")) {
                    place(new Knight(team), pos);
                }
                else if (pc.equals("B")) {
                    place(new Bishop(team), pos);
                }
                else if (pc.equals("R")) {
                    place(new Rook(team), pos);
                }
                else {
                    place(null, pos);
                }
            }
            curindex++;
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
    
    /**
     * Return true if the resulting board is a legal configuration.
     *
     * Specifically, the destination is in bounds, and the King isn't
     * placed in check.
     *
     * NOTE: this does not check for pieces eating their teammates. At
     * present, nothing does, but it would make sense for that to be
     * the piece's job:
     *
     * Piece's job: Check that the transition is legal:
     * - No pieces block the piece's path (varies by piece, eg. knight).
     * - No (presumably allied) piece's block the pieces destination.
     *
     * Board's job: Check that the resulting configuration is legal.
     * - The piece to be moved exists at the specified location.
     * - The destination is whithin bounds.
     * - The move doesn't put the team's king in check
     *
     * TODO - there is no check that the origPos is actually owned by the player
     *
     * proposal A: pass the team to the board when asking the board if a move is legal.
     *
     * proposal B: the game controller should prevent a player from moving a
     * piece that they don't own.
     */
    public boolean legalMove(ChessMove m) {
	Point orig = m.getOrig();
	Point dest = m.getDest();
	Point delta = m.getDelta();

	// Does the piece even exist?
	if (isEmpty(orig)) return false;
        
	//Are both positions within bounds?
	if (!(inBounds(orig) && inBounds(dest))) return false;

	//Is this move legal according to the piece?
	if (!getPiece(orig).legalMove(m, this)) return false;
        
	// Does the move put that piece's king in check?
	if (moveRevealsCheck(m)) return false;

        return true;
    }

    /**
     * pre: The piece indicated by move exists. This function is only
     * expected to be called from legalMove.
     *
     * post: Return true if performing the move leaves that team's
     * king in check.
     */
    private boolean moveRevealsCheck(ChessMove m) {
	// TODO
	return false;
    }
    
    /**
     * Return the chessboard that would result from a given move.
     */
    public ChessBoard move(ChessMove m) {
	ChessPiece[][] newBoard = new ChessPiece[size.x][size.y];

	for (int x = 0; x < size.x; x++) {
	    for (int y = 0; y < size.y; y++) {
		newBoard[x][y] = board[x][y];
	    }
	}

	ChessPiece pc = getPiece(m.getOrig());
	newBoard[m.getOrig().x][m.getOrig().y] = null;
	newBoard[m.getDest().x][m.getDest().y] = pc.move();

	boolean pawnPassing = (getPiece(m.getOrig()).toString().equals("P")
			       && Math.abs(m.getDelta().y) == 2);

	if (pawnPassing) {
	    Point passingSquare = new Point(m.getDest().x,
					    (m.getDest().y + m.getOrig().y)/2);
	    return new PassedBoard(size,
				   newBoard,
				   move(m.getOrig(), passingSquare));
	} else {
	    return new ChessBoard(size, newBoard);
	}
    }

    public ChessBoard move(Point orig, Point dest) {
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

