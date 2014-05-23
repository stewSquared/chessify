import Piece;
import ChessMove;
import awt.Point;

public class ChessBoard{
	public Piece Pieces[][] = new Piece[8][8];
	public ChessMove Moves[] = new ChessMove[];
	
	public boolean isEmpty(Point pos){
		return (Pieces[pos.x][pos.y]==NULL);
	}
	
	//Returns true if position is empty and a legal chess move, false if position occupied
	public boolean legalMove(Point pos, Point delta){//dx/dy 
		///CHECK FOR CHECK if in check, must remedy before moving something else, and cannot put into check again
	
		if (!isEmpty(Point(pos.x+delta.x,pos.y+delta.y))){
			return true;
		}
		
	
		if (Pieces[pos.x][pos.y].toString().equals('K')){ //King='K' Knight='N' 
			///CHECK FOR CHECK
		}
		
		
		
		
	}
	
	//Again returns true if position is empty && the move is valid according to the piece
	public boolean move(Point pos, Point delta){
		if ((!Pieces[pos.x][pos.y].legalMove(delta))||(!legalMove(pos,delta))){
			return false; //Move is invalid.
		}
		
		
		
		
		
	}
	
	
	public ChessBoard(){
		
	}	
	
}