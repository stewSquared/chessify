import java.awt.Point;
public class TempChessTest{
	public static void main(String[] args){
		ChessBoard tst = new ChessBoard(new Point(8,8));
		try{
			Runtime.getRuntime().exec("cls");
		}
		catch(Exception e){
			
		}
		System.out.print("\n\n");
		tst.displayBoard();
		System.out.print("\n\n");
	}
}