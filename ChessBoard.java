import java.awt.Point;

public class ChessBoard{
	public Piece Board[][];
	//public ChessMove Moves[] = new ChessMove[];
	private Point Size;
	
	final String DefaultPositions[] = new String [] {
		"Rblack"	,"Nblack"	,"Bblack"	,"Qblack"	,"Kblack"	,"Bblack"	,"Nblack"	,"Rblack",
		"Pblack"	,"Pblack"	,"Pblack"	,"Pblack"	,"Pblack"	,"Pblack"	,"Pblack"	,"Pblack",
		""			,""			,""			,""			,""			,""			,""			,"",	
		""			,""			,""			,""			,""			,""			,""			,"",
		""			,""			,""			,""			,""			,""			,""			,"",
		""			,""			,""			,""			,""			,""			,""			,"",									
		"Pwhite"	,"Pwhite"	,"Pwhite"	,"Pwhite"	,"Pwhite"	,"Pwhite"	,"Pwhite"	,"Pwhite",
		"Rwhite"	,"Nwhite"	,"Bwhite"	,"Qwhite"	,"Kwhite"	,"Bwhite"	,"Nwhite"	,"Rwhite"};
	
	public boolean isEmpty(Point pos){
		return (Board[pos.x][pos.y]==null);
	}
	public boolean isInBounds(Point pos){
		return (pos.x>0&&pos.y>0&&pos.x<Size.x&&pos.y<Size.y);
	}
	public boolean inCheck(String cteam){
		return false;
	}
	public Piece getPiece(Point pos){
		return Board[pos.x][pos.y];
	}
	public Piece getPiece(int tx, int ty){
		return Board[ty][ty];
	}
	
	public boolean legalMove(Point pos, Point delta){//dx/dy 
	
		
	
		if (!isEmpty(new Point(pos.x+delta.x,pos.y+delta.y))){
			return false;
		}
		
		String tstr = new String(Board[pos.x][pos.y].toString());
	
		if (tstr.equals('K')){ //King='K' Knight='N' 
			System.out.println("DERP");
		}
		
		return true;
		
	}
	
	/**
	Typically the cmd method will call this method, but it is allowable for the player
	to directly call it, however this doesn't update the command history.
	The order of operations is as follows:
	1. Assure the destination is either blank, or an enemy.
	2. Ask the origin piece if the move is valid according to its rules
	3. Assert that the move will not place the team in check
	4. Make the move, send updated check information about other team
	**/
	public boolean move(Point pos, Point delta){
		if ((!Board[pos.x][pos.y].legalMove(delta,this))||(!legalMove(pos,delta))){
			return false; //Move is invalid.
		}
		
		
		
		
		return true;
	}
	
	/**
	Player will call this method, it will return true if the command was both valid
	and feasible. This method will handle things like concession and individual moves.
	It requires Long Algebraic Notation by default. May implement ICCF notation.
	**/
	public boolean cmd(String cm){
		return false;
	}
	
	/**
	This method resets the board to a specified set of pieces, in a String array. The
	formatting should be as follows:
	
	[1 character indicating piece type (K,Q,N,B,R,P)][Team Name, for now white or black]
	Eg. String board[8][8] = new String[8][8];
	board[0][0]="Rblack" would me a black rook at the very top left position
	**/
	public int reset(String initstr[]){
		Board = new Piece[Size.x][Size.y];
		if (initstr.length!=Size.x*Size.y){
			return -1;
		}
		int curindex=0;
		for (String cur : initstr){
			int thex=curindex/Size.x;
			int they=curindex%Size.x;
			if (cur.length()==0){
				Board[thex][they]=null;
			}
			else{
				
				
				String pc = cur.substring(0,1); //Type
				String team = cur.substring(1,cur.length()); //Team
				
				
				if (pc.equals("P")){
					Board[thex][they]=new Pawn(new Point(thex,they),team);
				}
				else if (pc.equals("K")){
					Board[thex][they]=new King(new Point(thex,they),team);
				}
				else if (pc.equals("Q")){
					Board[thex][they]=new Queen(new Point(thex,they),team);
				}
				else if (pc.equals("N")){
					Board[thex][they]=new Knight(new Point(thex,they),team);
				}
				else if (pc.equals("B")){
					Board[thex][they]=new Bishop(new Point(thex,they),team);
				}
				else if (pc.equals("R")){
					Board[thex][they]=new Rook(new Point(thex,they),team);
				}
				else{
					Board[thex][they]=null;
				}
			}
			curindex++;
		}
		return 1;
	}
	
	
	public void defaultReset(){
		reset(DefaultPositions);
	}
	
	
	public ChessBoard(){
		Size=new Point(8,8);
		defaultReset();
	}	
	
	public ChessBoard(Point isize, String pcs[]){
		Size=new Point(isize);
		reset(pcs);
	}	
	
	
	public void displayBoard(){
	
	
		System.out.print(""+(char)201 + " ");
		for (int a = 0; a < Size.y; a++){
			char tbase = 'a';
			tbase += a;
			System.out.print(tbase);
		}
		
		System.out.print(" "+ (char)187+"\r\n\r\n");
	
		for (int tx = 0; tx < Size.x; tx++){
		
			System.out.print(""+(9-(tx+1))+" ");
		
			for (int ty = 0; ty < Size.y; ty++){
				
				if (Board[tx][ty]==null){
					char c;
					if ((tx+ty)%2==0)
						c=178;//219
					else
						c=' ';
					System.out.print(c);
				}
				else{
					String tm = Board[tx][ty].getTeam();
					if (tm.equals("white")){
						System.out.print(Board[tx][ty].toString());
					}
					else{
						System.out.print((Board[tx][ty].toString()).toLowerCase());
					}
				}

			}
			
			System.out.print(" "+(9-(tx+1)));
			
			System.out.print("\r\n");
		}
		
		System.out.print("\r\n");
		
		System.out.print(""+(char)200 + " ");
		for (int a = 0; a < Size.y; a++){
			char tbase = 'a';
			tbase += a;
			System.out.print(tbase);
		}
		
		System.out.print(" "+ (char)188+"\r\n\r\n");
		
	}
	
}