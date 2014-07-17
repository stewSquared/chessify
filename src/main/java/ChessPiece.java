import java.awt.Point;

public abstract class ChessPiece {
    
    final public ChessPiece parent;
    final public String team;

    public ChessPiece(String team) { this(team, (ChessPiece) null); }

    public ChessPiece(String team, ChessPiece parent) {
	this.team = team;
	this.parent = parent;
    }

    /**
     * Map character to chess piece.
     */
    public static ChessPiece fromChar(String team, char pc) {
	return fromChar(team, pc, null);
    }

    public static ChessPiece fromChar(String team, char pc, ChessPiece parent) {
	pc = Character.toUpperCase(pc);
	if (pc == 'P') {return new Pawn(team, parent);
	} else if (pc == 'K') { return new King(team, parent);
	} else if (pc == 'Q') { return new Queen(team, parent);
	} else if (pc == 'N') { return new Knight(team, parent);
	} else if (pc == 'B') { return new Bishop(team, parent);
	} else if (pc == 'R') { return new Rook(team, parent);
	} else { return null;
	}
    }

    public abstract ChessPiece move(ChessMove m);

    public String getTeam() { return team; }

    protected boolean moved() {
	return parent != null;
    }

    public boolean legalMove(ChessMove m, ChessBoard board) {
	// Note that a (0,0) move is illegal by this logic
	return board.getPiece(m.getDest()) == null 
	    || board.getPiece(m.getDest()).getTeam() != this.getTeam();
    }
}
