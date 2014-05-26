import java.awt.Point;

public class Bishop extends Piece {
    
    public Bishop(Point inpos, String inteam){
        super(inpos,inteam);
    }
    
    public String toString() {
        return "B";
    }

    public boolean legalMove(Point delta, ChessBoard b) {
        boolean diagonal = Math.abs(delta.x) == Math.abs(delta.y) &&
            !delta.equals(new Point(0,0));
        return diagonal && this.pathFree(delta, b);
    }
}
