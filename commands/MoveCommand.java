package commands;

import logic.*;

import java.util.ArrayList;
import java.util.Optional;

import static logic.GetMovesOfPiece.getMoves;
import static moves.DecodeMove.decode;
import static moves.DecodeMove.getReplacement;

import logic.Player;
import commands.ResignCommand;

public class MoveCommand implements Action {
   private String move;
   private Board board;

   private Player playerNow;
   private Player playerEnemy;

   public MoveCommand() {
   }

   public MoveCommand(Board board) {
      this.board = board;
   }

   public Optional<String> execute(String move) {
      this.move = move;
      return execute();
   }

   @Override
   public Optional<String> execute() {
      ArrayList<Pair<Integer>> coordinates = decode(move);
      Pair<Integer> pairStart = new Pair<>(coordinates.get(0).getFirst(), coordinates.get(0).getSecond());
      Pair<Integer> pairFinish = new Pair<>(coordinates.get(1).getFirst(), coordinates.get(1).getSecond());
      //System.out.println("Trying to move piece from " + pairStart + " to " + pairFinish);
      /*
      if (!(isDropIn(move))) {
         if (!(board.isTileOccupied(pairStart))) {
            System.out.println("No piece to move!");
            return Optional.empty();
         }
      }
      */
      
      if (isDropIn(move)) {
         /*
         if (board.isTileOccupied(pairFinish)) {
            System.out.println("illegal move line 46");
            return Optional.empty();
         }
         if (!(playerNow.getCapturedList().contains(droppedPiece))) {
            System.out.println("illegal move line 50");
            return Optional.empty();
         }
         */
         Piece droppedPiece = null;
	      for (Piece searchPiece : playerNow.getCapturedList()) {
            if (searchPiece.getType() == findType(move)) {
               droppedPiece = searchPiece;
               break;
            }
	      }
         playerNow.removeCaptured(droppedPiece);
         playerNow.addOnBoard(droppedPiece);
         board.setPiece(pairFinish, droppedPiece);
         droppedPiece.setCoordinates(pairFinish);
         return Optional.empty();
      }
      Piece piece = board.getPiece(pairStart.getFirst(), pairStart.getSecond());
      if (piece.isWhite() != playerNow.isWhite()) {
         /*
         System.out.println(piece.isWhite() + " " + playerNow.isWhite());
         System.out.println("Not your piece");
         */
         return Optional.empty();
      }
      /*
      System.out.println("Piece to move is " + piece.getType().toString() + "(" + piece.getCoordinates());
      System.out.println("Possible moves are " + getMoves(piece, board));
      */
      if (isBigCastling(move)) {
         if(bigCastling(piece)){
            return Optional.empty();
         }
      }
      if (isLittleCastling(move)) {
         if(littleCastling(piece)){
            return Optional.empty();
         }
      }
      if (!(board.isTileOccupied(pairFinish))) {
         if (!(getMoves(piece, board,playerNow.getAllyKing()).contains(pairFinish))) {
            System.out.println("illegal move line 71");
            return Optional.empty();
         }
         piece.setCoordinates(pairFinish);
         board.setPiece(pairFinish, piece);
         board.removePiece(pairStart);
         piece.setPieceMoved(true);
         if (isPromotion(move)) {
            piece.setType(getReplacement(move));
            piece.setPromoted(true);
         }
         return Optional.empty();
      }
      Piece finishPiece = board.getPiece(pairFinish);
      if (enPassant(piece, finishPiece)) {
         playerEnemy.removeOnBoard(board.getPiece(pairFinish));
         board.removePiece(pairFinish);
         finishPiece.setWhite(playerNow.isWhite());
         playerNow.addCaptured(finishPiece);
         int moveDirection = piece.isWhite() ? -1 : 1;
         piece.setRow(pairFinish.getFirst() + moveDirection);
         piece.setColumn(pairFinish.getSecond());
         board.setPiece(piece.getCoordinates(), piece);
         board.removePiece(pairStart);
         piece.setPieceMoved(true);
         return Optional.empty();
      }
      /*
      if (!(getMoves(piece, board).contains(pairFinish))) {
         System.out.println("illegal move line 89");
         return Optional.empty();
      }*/
      if (board.isTileOccupied(pairFinish)) {
         if(finishPiece.isPromoted())
            finishPiece.setType(Piece.PieceType.ROOK);
         playerEnemy.removeOnBoard(finishPiece);
         board.removePiece(pairFinish);
         finishPiece.setWhite(playerNow.isWhite());
         playerNow.addCaptured(finishPiece);
         piece.setCoordinates(pairFinish);
         board.setPiece(pairFinish, piece);
         piece.setPieceMoved(true);
         board.removePiece(pairStart);
         if (isPromotion(move)) {
            piece.setType(getReplacement(move));
         }
         return Optional.empty();
      }
      return Optional.empty();
   }

   private boolean isPromotion(String move) {
      if (move.length() == 5) {
         return true;
      }
      return false;
   }

   private boolean isLittleCastling(String move) {
      if (move.equals("e1g1") || move.equals("e8g8")) {
         return true;
      }
      return false;
   }

   private boolean isBigCastling(String move) {
      if (move.equals("e1c1") || move.equals("e8c8")) {
         return true;
      }
      return false;
   }

   private boolean isDropIn(String move) {
      return move.contains("@");
   }

   private boolean littleCastling(Piece king) {
      Integer kingRow = king.getRow();
      Integer kingColumn = king.getColumn();

      if(king.getType() != Piece.PieceType.KING)
         return false;
      if(!board.isTileOccupied(kingRow,Board.BOARD_SIZE - 1))
         return false;
      Pair<Integer> kingInitialCoord = king.getCoordinates();
      Piece rook = board.getBoard()[kingRow][Board.BOARD_SIZE - 1].get();
      Pair<Integer> rookInitialCoord = rook.getCoordinates();
      if (rook.getType() != Piece.PieceType.ROOK) {
         return false;
      }
      if (king.hasPieceMoved() || rook.hasPieceMoved()) {
         return false;
      }
      for (int j = kingColumn + 1; j < Board.BOARD_SIZE - 1; j++) {
         if (board.isTileOccupied(kingRow, j)) {
            return false;
         }
      }
      for (int j = kingColumn; j < Board.BOARD_SIZE - 1; j++) {
         if (GetMovesOfPiece.isInCheck(kingRow, j, king.isWhite(), board)) {
            return false;
         }
      }
      board.getBoard()[kingRow][kingColumn + 2] = Optional.of(king);
      board.getBoard()[kingRow][kingColumn + 1] = Optional.of(rook);
      king.setCoordinates(new Pair<Integer>(kingRow,kingColumn + 2));
      rook.setCoordinates(new Pair<Integer>(kingRow,kingColumn + 1));
      board.removePiece(kingInitialCoord);
      board.removePiece(rookInitialCoord);
      return true;
   }

   private boolean bigCastling(Piece king) {
      Integer kingRow = king.getRow();
      Integer kingColumn = king.getColumn();
      if(king.getType() != Piece.PieceType.KING)
         return false;
      if(!board.isTileOccupied(kingRow,0))
         return false;
      Piece rook = board.getBoard()[kingRow][0].get();
      Pair<Integer> kingInitialCoord = king.getCoordinates();
      Pair<Integer> rookInitialCoord = rook.getCoordinates();
      if (rook.getType() != Piece.PieceType.ROOK) {
         System.out.println("not rook");
         return false;
      }
      if (king.hasPieceMoved() || rook.hasPieceMoved()) {
         System.out.println("already moved");
         return false;
      }
      for (int j = 1; j < kingColumn; j++) {
         if (board.isTileOccupied(kingRow, j)) {
            System.out.println("things in the way");
            return false;
         }
      }
      for (int j = 2; j <= kingColumn; j++) {
         if (GetMovesOfPiece.isInCheck(kingRow, j, king.isWhite(), board)) {
            System.out.println("king is in check");
            return false;
         }
      }

      board.getBoard()[kingRow][kingColumn - 2] = Optional.of(king);
      board.getBoard()[kingRow][kingColumn - 1] = Optional.of(rook);
      king.setCoordinates(new Pair<Integer>(kingRow,kingColumn - 2));
      rook.setCoordinates(new Pair<Integer>(kingRow,kingColumn - 1));
      board.removePiece(kingInitialCoord);
      board.removePiece(rookInitialCoord);
      return true;
   }

   private boolean enPassant(Piece pawn1, Piece pawn2) {
      if (pawn1.getType() != Piece.PieceType.PAWN || pawn2.getType() != Piece.PieceType.PAWN) {
         return false;
      }
      if (pawn2.getRow() != pawn1.getRow()) {
         return false;
      }
      if (!(pawn2.getColumn() == pawn1.getColumn() + 1 || pawn2.getColumn() == pawn1.getColumn() - 1)) {
         return false;
      }
      return pawn2.hasPieceMoved();
   }

   private Piece.PieceType findType(String move) {
      if (move.contains("B")) {
         return Piece.PieceType.BISHOP;
      }
      if (move.contains("N")) {
         return Piece.PieceType.KNIGHT;
      }
      if (move.contains("P")) {
         return Piece.PieceType.PAWN;
      }
      if (move.contains("Q")) {
         return Piece.PieceType.QUEEN;
      }
      if (move.contains("R")) {
         return Piece.PieceType.ROOK;
      }
      return null;
   }

   public Player getPlayerNow() {
      return playerNow;
   }

   public void setPlayerNow(Player playerNow) {
      this.playerNow = playerNow;
   }

   public Player getPlayerEnemy() {
      return playerEnemy;
   }

   public void setPlayerEnemy(Player playerEnemy) {
      this.playerEnemy = playerEnemy;
   }

   public void changePlayers() {
      Player aux = playerNow;
      playerNow = playerEnemy;
      playerEnemy = aux;
   }
}


