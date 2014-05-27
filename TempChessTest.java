import java.awt.Point;
public class TempChessTest{
	public static void main(String[] args){
		ChessBoard tst = new ChessBoard();
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