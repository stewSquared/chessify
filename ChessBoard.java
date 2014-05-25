import java.awt.Point;

public class ChessBoard{
	public Piece Board[][];
	//public ChessMove Moves[] = new ChessMove[];
	private Point Size;
	
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
	
	//Again returns true if position is empty && the move is valid according to the piece
	public boolean move(Point pos, Point delta){
		if ((!Board[pos.x][pos.y].legalMove(delta,this))||(!legalMove(pos,delta))){
			return false; //Move is invalid.
		}
		
		
		
		
		return true;
	}
	
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
		String dstring[] = new String [] {	"Rblack","Nblack","Bblack","Qblack","Kblack","Bblack","Nblack","Rblack",
											"Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack",
											"","","","","","","","",	
											"","","","","","","","",
											"","","","","","","","",
											"","","","","","","","",									
											"Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite",
											"Rwhite","Nwhite","Bwhite","Qwhite","Kwhite","Bwhite","Nwhite","Rwhite"
									};
		reset(dstring);
	}
	
	
	public ChessBoard(Point isize){
		Size=isize;
		defaultReset();
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