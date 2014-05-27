import java.util.Scanner;

public class TextPlayer extends Player {

    protected Scanner commands;

    public TextPlayer() {
	super();
	commands = new Scanner(System.in);
	System.out.println("%s, what is your name?".format(name));
	name = commands.nextLine();
    }

    public TextPlayer(String name) {
	super(name);
	commands = new Scanner(System.in);
    }

    public ChessMove move(ChessBoard board) {
	System.out.print("Enter a move: ");
	String m = commands.nextLine();
	while(!ChessMove.validSmith(m)) {
	    System.out.println("%s is not valid Smith Notation.".format(m));
	    System.out.print("Enter another move: ");
	}
	return new ChessMove(m);
    }
}
