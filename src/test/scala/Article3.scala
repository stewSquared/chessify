import org.scalatest.FunSuite

/** Test conformity with Article 3 of FIDE "Laws of Chess"
  * 
  * 3.1 It is not permitted to move a piece to a square occupied by a
  * piece of the same colour. If a piece moves to a square occupied by
  * an opponent’s piece the latter is captured and removed from the
  * chessboard as part of the same move. A piece is said to attack an
  * opponent’s piece if the piece could make a capture on that square
  * according to the Articles 3.2 to 3.8.  A piece is considered to
  * attack a square, even if such a piece is constrained from moving
  * to that square because it would then leave or place the king of
  * its own colour under attack.
  * 
  * 3.2 The bishop may move to any square along a diagonal on which it
  * stands.
  * 
  * 3.3 The rook may move to an square along the file or rank on which
  * it stands.
  * 
  * 3.4 The queen may move to any square along the file, the rank or a
  * diagonal on which it stands.
  * 
  * 3.5 When making these moves the bishop, rook or queen may not move
  * over any intervening pieces.
  * 
  * 3.6 The knight may move to one of the squares nearest to that on
  * which it stands but not on the same rank, file or diagonal.
  * 
  * 3.7
  * 
  * a. The pawn may move forward to the unoccupied square immediately
  * in front of it on the same file, or
  * 
  * b. on its first move the pawn may move as in 3.7.a or
  * alternatively it may advance two squares along the same file
  * provided both squares are unoccupied, or
  * 
  * c. the pawn may move to a square occupied by an opponent’s piece,
  * which is diagonally in front of it on an adjacent file, capturing
  * that piece.
  * 
  * d. A pawn attacking a square crossed by an opponent’s pawn which
  * has advanced two squares in one move from its original square may
  * capture this opponent’s pawn as though the latter had been moved
  * only one square. This capture is only legal on the move following
  * this advance and is called an ‘en passant’ capture.
  * 
  * e. When a pawn reaches the rank furthest from its starting
  * position it must be exchanged as part of the same move on the same
  * square for a new queen, rook, bishop or knight of the same
  * colour. The player’s choice is not restricted to pieces that have
  * been captured previously. This exchange of a pawn for another
  * piece is called ‘promotion’ and the effect of the new piece is
  * immediate.
  * 
  * 3.8
  * 
  */
class Article3 extends FunSuite {

  val path = "src/test/resources/"
  def startingBoard = new ChessBoard(path + "default.cb")

  test("3.1.a : Can't capture same color.") {
    val board =
      startingBoard move (new ChessMove("b2b3")) move (new ChessMove("b1a3"))

    val bishopCaptureAlliedKnight = new ChessMove("c1a3")
    board.legalMove(bishopCaptureAlliedKnight) === false
  }

  test("3.1.b : Piece replaces the piece it captures.") {
    val board = startingBoard.move("b2b4").move("a7a5").move("b4a5")
    board.getPiece("a5").team === startingBoard.getPiece("b2").team
  }

  test("3.2 : Bishop moves") {
    val board = new ChessBoard(path + "bishop.cb")

    board.legalMove("e4b1") === true
    board.legalMove("e4a8") === true
    board.legalMove("e4h7") === true
    board.legalMove("e4f3") === true
  }

  test("3.3 : Rook moves") {
    val board = new ChessBoard(path + "rook.cb")

    board.legalMove("e4e1") == true
    board.legalMove("e4a4") == true
    board.legalMove("e4e8") == true
    board.legalMove("e4f4") == true
  }

  test("3.4 : Queen moves") {
    val board = new ChessBoard(path + "bishop.cb")

    board.legalMove("e4b1") === true
    board.legalMove("e4a8") === true
    board.legalMove("e4h7") === true
    board.legalMove("e4f3") === true

    board.legalMove("e4e1") == true
    board.legalMove("e4a4") == true
    board.legalMove("e4e8") == true
    board.legalMove("e4f4") == true
  }

  test("3.5 : Bishop, Rook, and Queen can't move over intervening pieces.") {
    val board = new ChessBoard(path + "pass-through.cb")

    board.legalMove("h8f6") === false

    board.legalMove("a8c8") === false
    board.legalMove("a8a6") === false

    board.legalMove("a1a3") === false
    board.legalMove("a1c3") === false
    board.legalMove("a1c1") === false
  }

  test("3.6 : Knight moves") {
    val board = new ChessBoard(path + "knight.cb")

    board.legalMove("c3a2") === true
    board.legalMove("c3a4") === true
    board.legalMove("c3b5") === true
    board.legalMove("c3d7") === true
    board.legalMove("c3e4") === true
    board.legalMove("c3e2") === true
    board.legalMove("c3d1") === true
    board.legalMove("c3b1") === true

    board.legalMove("g8e7") === false
    board.legalMove("g8f6") === false
    board.legalMove("g8h6") === false
  }

  test("3.7 : Pawns can move forward one square iff unoccupied") {
    val board = new ChessBoard(path + "pawns-a.cb")
    board.legalMove("c2c3") === true
    board.legalMove("f2f3") === false
  }

  test("3.7 : Pawns can move forward twice on first move") {
    val board = new ChessBoard(path + "pawns-b.cb")
    board.legalMove("c2c3") === true
    board.legalMove("c2c4") === true
    board.legalMove("e2e3") === false
    board.legalMove("g2g4") === false
  }

  test("3.7 : Pawns can move diagonally only to capture") {
    val board = new ChessBoard(path + "pawns-c.cb")
    board.legalMove("c2b3") === true
    board.legalMove("c2d3") === true
    board.legalMove("f2e3") === false
    board.legalMove("f2g3") === false
  }

  test("3.7 : En passante capture") {
    val board = new ChessBoard(path + "pawns-d.cb")
    board.move("e7e5").legalMove("d5e6") === true
    board.move("e7e5").move("d5e6").getPiece("e5") === null
    board.move("e7e5").move("a2a3").move("a7a6").legalMove("d5e6") === false
  }
}
