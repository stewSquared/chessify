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
	
	//Returns true if position is empty and a legal chess move, false if position occupied
	public boolean legalMove(Point pos, Point delta){//dx/dy 
	
		
	
		if (!isEmpty(new Point(pos.x+delta.x,pos.y+delta.y))){
			return false;
		}
		
		//CHECK ALONG PATH FOR OBSTRUCTIONS
		
		
		///CHECK FOR CHECK if in check, must remedy before moving something else, and cannot put into check again
		
		
		
		String tstr = new String(Board[pos.x][pos.y].toString());
	
		if (tstr.equals('K')){ //King='K' Knight='N' 
			System.out.println("DERP");
		}
		
		return false;
		
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
											"Rwhite","Nwhite","Bwhite","Qwhite","Kwhite","Bwhite","Nwhite","Rwhite",
									};
		reset(dstring);
	}
	
	
	public ChessBoard(Point isize){
		Size=isize;
		defaultReset();
	}	
	
	public void displayBoard(){
		for (int tx = 0; tx < Size.x; tx++){
			for (int ty = 0; ty < Size.y; ty++){
				
				if (Board[tx][ty]==null){
					System.out.print('_');
				}
				else{
					System.out.print(Board[tx][ty].toString());
				}

			}
			System.out.print("\r\n");
		}
	}
	
}