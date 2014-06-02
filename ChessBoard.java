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
    }; // caveat, we might want to initialize from a plaintext file.
        //Stephen: agreed! Not top priority though :D

    public Piece board[][];
    //public ChessMove Moves[] = new ChessMove[];
    private Point size;
    
    public ChessBoard() {
        this(new Point(8,8), DEFAULT_BOARD_INIT_STR);
    }

    public ChessBoard(Point isize, String initstr[]) {
        size = isize;
        reset(initstr);
    }        

    public Piece getPiece(Point pos) {
        return board[pos.x][pos.y];
    }

    public Piece getPiece(int tx, int ty) {
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
    
    private void place(Piece pc, Point pos) {
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
     * - No pieces block the pieces path (varies by piece, eg. knight).
     * - No (presumably allied) pieces block the pieces destination.
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

	///Add Team Check Here

	// Does the piece even exist?
	if (isEmpty(orig)) return false;
        
	//Are both positions within bounds?
	if (!(inBounds(orig) && inBounds(dest))) return false;

        //Is the end position one of our team's pieces? (this is a substitute
        //for a check for emptiness)

	/* Stew: That's not really a great substitute check for emptiness,
	 * because if the position is empty, null.getTeam() results in error.
	 * Instead, I'll make it part of the pieces job to test that the
	 * destination isn't blocked by a team-mate. This is consistent with the
	 * pieces present behavior of checking that the pieces path isn't
	 * blocked. */
        
	//Is this move legal according to the piece?
	if (!getPiece(orig).legalMove(delta, this)) return false;
        
	///Todo: temp move the board, and assure it will not place our team in
	///check then unmove the pieces. This must include pieces that would
	///have been deleted, so instead of actually deleting any pieces store
	///them temporarily

	// Does the move put that piece's king in check?
	if (moveRevealsCheck(m)) return false;

        return true;
    }

    public boolean legalMove(Point pos, Point delta) {//dx/dy 
	return legalMove(new ChessMove(pos, new Point(pos.x + delta.x,
						      pos.y + delta.y)));
    }

    /**
     * pre: The piece indicated by move exists. This function is only
     * expected to be called from legalMove.
     *
     * post: Return true if performing the move leaves that team's
     * king in check.
     */
    private boolean moveRevealsCheck(ChessMove m) {
	Piece origPiece = getPiece(m.getOrig());
	Piece destPiece = getPiece(m.getDest());
	boolean check = false;
	move(m);
	// TODO: loop logic to turn check true

	// Restore board
	place(origPiece, m.getOrig());
	place(destPiece, m.getDest());
	return check;
    }
    
    /**
     * Move a piece. This method allows for "illegal" moves, including ones that
     * potentially cause runtime errors. 
     */
    public void move(ChessMove m) {
        place(getPiece(m.getOrig()), m.getDest());
        remove(m.getOrig());
    }

    public void move(Point pos, Point delta) {
	move(new ChessMove(pos, new Point(pos.x + delta.x,
					  pos.y + delta.y)));
    }
    
    public int reset(String initstr[]) {
        board = new Piece[size.x][size.y];
        if (initstr.length != size.x * size.y) {
            return -1;
        }
        int curindex=0;
        for (String cur : initstr) {
            int thex = curindex % size.x;
            int they = curindex / size.y;
	    
            if (cur.length() == 0) {
                board[thex][they] = null;
            } else {
                String pc = cur.substring(0,1); //Type
                String team = cur.substring(1,cur.length()); //Team
                
                if (pc.equals("P")) {
                    board[thex][they] = new Pawn(new Point(thex,they),team);
                }
                else if (pc.equals("K")) {
                    board[thex][they] = new King(new Point(thex,they),team);
                }
                else if (pc.equals("Q")) {
                    board[thex][they] = new Queen(new Point(thex,they),team);
                }
                else if (pc.equals("N")) {
                    board[thex][they] = new Knight(new Point(thex,they),team);
                }
                else if (pc.equals("B")) {
                    board[thex][they] = new Bishop(new Point(thex,they),team);
                }
                else if (pc.equals("R")) {
                    board[thex][they] = new Rook(new Point(thex,they),team);
                }
                else {
                    board[thex][they] = null;
                }
            }
            curindex++;
        }
        return 1;
    }
    
    public void displayBoard() {
        System.out.print(this);
    }
    
    public String toString() {
        String boardstr = "";
        
        char whiteSqr = 178; // '?'; //219
        char blackSqr = ' '; // (char) 32

        boardstr += ((char)201 + " ");
        for (char file = 'a'; file <= 'h' ; file++) boardstr += (file);
        boardstr += (" "+ (char)187+"\n\n");
	
        for (int ty = 0; ty < size.y; ty++) {
            int rank = 8 - ty;
            boardstr += (rank + " ");
            for (int tx = 0; tx < size.x; tx++) {
                if (board[tx][ty]==null) {
                    boardstr += ((tx+ty)%2 == 0 ? whiteSqr : blackSqr);
                }
                else{
                    String tm = board[tx][ty].getTeam();
                    if (tm.equals("white")) {
                        boardstr += (board[tx][ty].toString());
                    }
                    else{
                        boardstr += ((board[tx][ty].toString()).toLowerCase());
                    }
                }
            }
            boardstr += " " + rank;
            boardstr += "\n";
        }
        boardstr += "\n";
        boardstr += (""+(char)200 + " ");
        for (char file = 'a'; file <= 'h' ; file++) boardstr += (file);
        boardstr += (" "+ (char)188+"\n\n");

        return boardstr;
    }
}
