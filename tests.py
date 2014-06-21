import ChessBoard
import ChessMove
import unittest

class TestCastling(unittest.TestCase):
    """Test castling accord to FIDE rules.

    From FIDE Laws of Chess:

    (1) The right to castle has been lost:

        a. if the king has already moved, or

        b. with a rook that has already moved.

    (2) Castling is prevented temporarily:

        a. if the square on which the king stands, or the square which
           it must cross, or the square which it is to occupy, is
           attacked by one or more of the opponent's pieces, or

        b. if there is any piece between the king and the rook with
           which castling is to be effected."""
    
    def setUp(self):
        self.bks = ChessMove("e8g8")  # black kingside
        self.bqs = ChessMove("e8c8")  # black queenside
        self.wks = ChessMove("e1g1")  # white kingside
        self.wqs = ChessMove("e1c1")  # white queenside

    def test_castle_result(self):
        board = ChessBoard("boards/castling.cb")
        ks = ChessBoard("boards/castle-ks-result.cb")
        qs = ChessBoard("boards/castle-qs-result.cb")
        self.assertTrue(board.move(self.bks).move(self.wks).equals(ks))
        self.assertTrue(board.move(self.bqs).move(self.wqs).equals(qs))

    def test_lost_right_to_castle(self):
        start = ChessBoard("boards/castling.cb")

        # a. king wander
        board = (start
                 .move(ChessMove("e1e2"))
                 .move(ChessMove("e2e1")))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

        # b. ks rook wander
        board = (start
                 .move(ChessMove("h1h2"))
                 .move(ChessMove("h2h1")))
        self.assertFalse(board.legalMove(self.wks))
        self.assertTrue(board.legalMove(self.wqs))

        # b. qs rook wander
        board = (start
                 .move(ChessMove("a1a2"))
                 .move(ChessMove("a2a1")))
        self.assertTrue(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_rook_path_blocked(self):
        board = ChessBoard("boards/castle-rook-path-blocked.cb")
        self.assertFalse(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_during_check(self):
        board = ChessBoard("boards/castle-during-check.cb")
        self.assertTrue(board.legalMove(self.bks))
        self.assertTrue(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_through_check(self):
        board = ChessBoard("boards/castle-through-check.cb")
        self.assertTrue(board.legalMove(self.bks))
        self.assertTrue(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_into_check(self):
        board = ChessBoard("boards/castle-into-check.cb")
        self.assertTrue(board.legalMove(self.bks))
        self.assertTrue(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_through_piece(self):
        board = ChessBoard("boards/castle-through-piece.cb")
        self.assertFalse(board.legalMove(self.bks))
        self.assertFalse(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

    def test_castle_onto_piece(self):
        board = ChessBoard("boards/castle-onto-piece.cb")
        self.assertFalse(board.legalMove(self.bks))
        self.assertFalse(board.legalMove(self.bqs))
        self.assertFalse(board.legalMove(self.wks))
        self.assertFalse(board.legalMove(self.wqs))

if __name__ == '__main__':
    unittest.main()
