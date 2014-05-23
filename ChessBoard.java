import Pieces.*;
import ChessMove;
import awt.Point;

public class ChessBoard{
	public Piece Board[][];
	public ChessMove Moves[] = new ChessMove[];
	private Point Size;
	
	public boolean isEmpty(Point pos){
		return (Board[pos.x][pos.y]==NULL);
	}
	public boolesan isInBounds(Point pos){
		
	}
	
	public boolean inCheck(String cteam){
		
	}
	
	//Returns true if position is empty and a legal chess move, false if position occupied
	public boolean legalMove(Point pos, Point delta){//dx/dy 
	
		
	
		if (!isEmpty(Point(pos.x+delta.x,pos.y+delta.y))){
			return false;
		}
		
		//CHECK ALONG PATH FOR OBSTRUCTIONS
		
		
		///CHECK FOR CHECK if in check, must remedy before moving something else, and cannot put into check again
		
		bool incheck = 
		
		
		
	
		if (Board[pos.x][pos.y].toString().equals('K')){ //King='K' Knight='N' 
			
		}
		
		
		
		
	}
	
	//Again returns true if position is empty && the move is valid according to the piece
	public boolean move(Point pos, Point delta){
		if ((!Board[pos.x][pos.y].legalMove(delta))||(!legalMove(pos,delta))){
			return false; //Move is invalid.
		}
		
		
		
		
		
	}
	
	public int reset(String initstr[]){
		if (initstr.length!=size.x*size.y){
			return -1;
		}
		int curindex=0;
		for (String cur : initstr){
			if (cur.length==0){
				Board[curindex]=null;
			}
			else{
				String pc = cur.substr(0,1); //Type
				String team = cur.substr(1,cur.length-1)); //Team
				
				if (pc.equals('P')){
					Board[curindex]=new Pawn(Point(),team);
				}
				else if (pcequals('K')){
					Board[curindex]=new King(Point(),team);
				}
				else if (pcequals('Q')){
					Board[curindex]=new Queen(Point(),team);
				}
				else if (pcequals('N')){
					Board[curindex]=new Knight(Point(),team);
				}
				else if (pcequals('B')){
					Board[curindex]=new Bishop(Point(),team);
				}
				else if (pcequals('R')){
					Board[curindex]=new Rook(Point(),team);
				}
				else{
					Board[curindex]=null;
				}
			}
			
		}
		return 1;
	}
	
	public void defaultReset(){
		static String dstring[] = {"Rblack","Nblack","Bblack","Qblack","Kblack","Bblack","Nblack","Rblack",
								   "Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack","Pblack",
									0,0,0,0,0,0,0,0,
									0,0,0,0,0,0,0,0,
									0,0,0,0,0,0,0,0,
									0,0,0,0,0,0,0,0,						
								   "Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite","Pwhite",
								   "Rwhite","Nwhite","Bwhite","Qwhite","Kwhite","Bwhite","Nwhite","Rwhite",
									};
	}
	
	
	public ChessBoard(Point size = Point(8,8)){
		
		defaultReset();
	}	
	
}