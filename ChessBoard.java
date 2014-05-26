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

    public boolean isEmpty(Point pos) {
        return (board[pos.x][pos.y]==null);
    }

    public boolean isInBounds(Point pos) {
        return (pos.x >= 0 && pos.y >= 0 && pos.x < size.x && pos.y < size.y);
    }

    // What is this?
    public boolean inCheck(String cteam) {
        return false;
    }

    public Piece getPiece(Point pos) {
        return board[pos.x][pos.y];
    }

    public Piece getPiece(int tx, int ty) {
        return board[tx][ty];
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
     * Piece's job: Check that the transition is legal.
     *
     * Board's job: Check that the configuration is legal.
     *
     */
    public boolean legalMove(Point pos, Point delta) {//dx/dy 
        if (!isEmpty(new Point(pos.x + delta.x, pos.y + delta.y))) {
            return false;
        }
        
        String tstr = "" + board[pos.x][pos.y];
        
        if (tstr.equals('K')) { //King='K' Knight='N' 
            System.out.println("DERP");
        }
        
        return true;
    }
    
    //Again returns true if position is empty && the move is valid according to the piece
    public boolean move(Point pos, Point delta) {
	// isn't this method just what legalMove should be?
	return board[pos.x][pos.y].legalMove(delta, this) && legalMove(pos,delta);
    }
    
    public int reset(String initstr[]) {
        board = new Piece[size.x][size.y];
        if (initstr.length != size.x * size.y) {
            return -1;
        }
        int curindex=0;
        for (String cur : initstr) {
            int thex = curindex / size.x;
            int they = curindex % size.x;

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

        boardstr += (""+(char)201 + " ");
	for (char file = 'a'; file <= 'h' ; file++) boardstr += (file);
        boardstr += (" "+ (char)187+"\n\n");
	// carriage return is unnecessary with System.out.*
        
        for (int tx = 0; tx < size.x; tx++) {
	    int rank = 8 - tx;
            boardstr += ("" + rank + " ");

            for (int ty = 0; ty < size.y; ty++) {
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
