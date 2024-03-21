package logic;

import java.util.ArrayList;

public class GetMovesOfPiece {
   private static final int BISHOP_ROOK_DIRECTIONS_SIZE = 4;

   private GetMovesOfPiece() {

   }

   public static ArrayList<Pair<Integer>> getMoves(Piece piece, Board board, Piece king) {
      switch (piece.getType()) {
         case BISHOP -> {
            return getBishopMoves(piece, board, king);
         }
         case KNIGHT -> {
            return getKnightMoves(piece, board, king);
         }
         case ROOK -> {
            return getRookMoves(piece, board, king);
         }
         case QUEEN -> {
            return getQueenMoves(piece, board, king);
         }
         case PAWN -> {
            return getPawnMoves(piece, board, king);
         }
         case KING -> {
            return getKingMoves(piece, board);
         }
         default -> {
            return new ArrayList<>();
         }
      }
   }

   public static boolean isInCheck(Piece king, Board board) {
      return isInCheck(king.getRow(), king.getColumn(), king.isWhite(), board);
   }

   public static boolean isInCheck(int row, int column, boolean isWhite, Board board) {
      ArrayList<Pair<Integer>> possibleKnightAttacks = getKnightMoves(
            new Piece(isWhite, row, column), board);
      for (Pair<Integer> movePlace : possibleKnightAttacks) {
         if (board.isTileOccupied(movePlace)) {
            Piece tempPiece = board.getPiece(movePlace);
            if (tempPiece.isWhite() != isWhite && tempPiece.getType() == Piece.PieceType.KNIGHT) {
               return true;
            }
         }
      }
      ArrayList<Pair<Integer>> possibleBishopAttacks = getBishopMoves(
            new Piece(isWhite, row, column), board);
      for (Pair<Integer> movePlace : possibleBishopAttacks) {
         if (board.isTileOccupied(movePlace)) {
            Piece tempPiece = board.getPiece(movePlace);
            Piece.PieceType tempType = tempPiece.getType();
            if (tempPiece.isWhite() != isWhite
                  && (tempType == Piece.PieceType.BISHOP || tempType == Piece.PieceType.QUEEN)) {
               return true;
            }
         }
      }
      ArrayList<Pair<Integer>> possibleRookAttacks = getRookMoves(
            new Piece(isWhite, row, column), board);
      for (Pair<Integer> movePlace : possibleRookAttacks) {
         if (board.isTileOccupied(movePlace)) {
            Piece tempPiece = board.getPiece(movePlace);
            Piece.PieceType tempType = tempPiece.getType();
            if (tempPiece.isWhite() != isWhite
                  && (tempType == Piece.PieceType.ROOK || tempType == Piece.PieceType.QUEEN)) {
               return true;
            }
         }
      }
      int possiblePawnTile = row;
      possiblePawnTile += isWhite ? -1 : 1;
      int attackRightColumn = column + 1;
      int attackLeftColumn = column - 1;
      if (possiblePawnTile >= 0 && possiblePawnTile < Board.BOARD_SIZE) {
         if (attackLeftColumn >= 0) {
            if (board.isTileOccupied(possiblePawnTile, attackLeftColumn)) {
               Piece tempPiece = board.getPiece(possiblePawnTile, attackLeftColumn);
               if (tempPiece.isWhite() != isWhite && tempPiece.getType() == Piece.PieceType.PAWN) {
                  return true;
               }
            }
         }
         if (attackRightColumn < Board.BOARD_SIZE) {
            if (board.isTileOccupied(possiblePawnTile, attackRightColumn)) {
               Piece tempPiece = board.getPiece(possiblePawnTile, attackRightColumn);
               if (tempPiece.isWhite() != isWhite && tempPiece.getType() == Piece.PieceType.PAWN) {
                  return true;
               }
            }
         }

      }
      ArrayList<Pair<Integer>> possibleKingAttacks = getKingMovesNoCheck(new Piece(isWhite, row, column),
            board);
      for (Pair<Integer> movePlace : possibleKingAttacks) {
         if (board.isTileOccupied(movePlace)) {
            Piece tempPiece = board.getPiece(movePlace);
            if (tempPiece.isWhite() != isWhite && tempPiece.getType() == Piece.PieceType.KING) {
               return true;
            }
         }
      }
      return false;
   }

   private static ArrayList<Pair<Integer>> getKnightMoves(Piece piece, Board board, Piece king) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();

      int[] X = {-2, -1, 1, 2, 2, 1, -1, -2};
      int[] Y = {1, 2, 2, 1, -1, -2, -2, -1};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < Board.BOARD_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         if (moveRow >= 0 && moveRow < Board.BOARD_SIZE && moveColumn >= 0 && moveColumn < Board.BOARD_SIZE) {
            if (!board.isTileOccupied(moveRow, moveColumn)) {
               board.removePiece(piece.getCoordinates());
               Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
               board.setPiece(pair, piece);
               boolean badMove = isInCheck(king, board);
               board.removePiece(pair);
               board.setPiece(piece.getCoordinates(), piece);
               if(!badMove)
                  moves.add(pair);
               continue;
            }
            Piece destinationPiece = board.getPiece(moveRow, moveColumn);
            if (piece.isWhite() == destinationPiece.isWhite()) {
               continue;
            }
            board.removePiece(piece.getCoordinates());
            Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
            board.setPiece(pair, piece);
            boolean badMove = isInCheck(king, board);
            board.removePiece(pair);
            board.setPiece(piece.getCoordinates(), piece);
            board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
            if(!badMove)
               moves.add(pair);
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getKnightMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();

      int[] X = {-2, -1, 1, 2, 2, 1, -1, -2};
      int[] Y = {1, 2, 2, 1, -1, -2, -2, -1};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < Board.BOARD_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         if (moveRow >= 0 && moveRow < Board.BOARD_SIZE && moveColumn >= 0 && moveColumn < Board.BOARD_SIZE) {
            if (!board.isTileOccupied(moveRow, moveColumn)) {
               Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
               moves.add(pair);
               continue;
            }
            Piece destinationPiece = board.getPiece(moveRow, moveColumn);
            if (piece.isWhite() == destinationPiece.isWhite()) {
               continue;
            }
            Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
            moves.add(pair);
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getBishopMoves(Piece piece, Board board,Piece king) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int[] X = {-1, -1, 1, 1};
      int[] Y = {-1, 1, -1, 1};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < BISHOP_ROOK_DIRECTIONS_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         while (moveRow >= 0 && moveColumn >= 0 && moveRow < Board.BOARD_SIZE && moveColumn < Board.BOARD_SIZE) {
            if (board.isTileOccupied(moveRow, moveColumn)) {
               Piece destinationPiece = board.getPiece(moveRow, moveColumn);
               if (destinationPiece.isWhite() != piece.isWhite()) {
                  board.removePiece(piece.getCoordinates());
                  Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
                  board.setPiece(pair, piece);
                  boolean badMove = isInCheck(king, board);
                  board.removePiece(pair);
                  board.setPiece(piece.getCoordinates(), piece);
                  board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
                  if(!badMove)
                     moves.add(pair);
               }
               break;
            }
            board.removePiece(piece.getCoordinates());
            Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
            board.setPiece(pair, piece);
            boolean badMove = isInCheck(king, board);
            board.removePiece(pair);
            board.setPiece(piece.getCoordinates(), piece);
            if(!badMove)
               moves.add(pair);
            moveRow += X[i];
            moveColumn += Y[i];
         }
      }
      return moves;
   }
   private static ArrayList<Pair<Integer>> getBishopMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int[] X = {-1, -1, 1, 1};
      int[] Y = {-1, 1, -1, 1};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < BISHOP_ROOK_DIRECTIONS_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         while (moveRow >= 0 && moveColumn >= 0 && moveRow < Board.BOARD_SIZE && moveColumn < Board.BOARD_SIZE) {
            if (board.isTileOccupied(moveRow, moveColumn)) {
               if (board.getPiece(moveRow, moveColumn).isWhite() != piece.isWhite()) {
                  moves.add(new Pair<>(moveRow, moveColumn));
               }
               break;
            }
            moves.add(new Pair<>(moveRow, moveColumn));
            moveRow += X[i];
            moveColumn += Y[i];
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getRookMoves(Piece piece, Board board,Piece king) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int[] X = {0, 0, -1, 1};
      int[] Y = {-1, 1, 0, 0};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < BISHOP_ROOK_DIRECTIONS_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         while (moveRow >= 0 && moveColumn >= 0 && moveRow < Board.BOARD_SIZE && moveColumn < Board.BOARD_SIZE) {
            if (board.isTileOccupied(moveRow, moveColumn)) {
               Piece destinationPiece = board.getPiece(moveRow, moveColumn);
               if (destinationPiece.isWhite() != piece.isWhite()) {
                  board.removePiece(piece.getCoordinates());
                  Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
                  board.setPiece(pair, piece);
                  boolean badMove = isInCheck(king, board);
                  board.removePiece(pair);
                  board.setPiece(piece.getCoordinates(), piece);
                  board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
                  if(!badMove)
                     moves.add(pair);
               }
               break;
            }
            board.removePiece(piece.getCoordinates());
            Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
            board.setPiece(pair, piece);
            boolean badMove = isInCheck(king, board);
            board.removePiece(pair);
            board.setPiece(piece.getCoordinates(), piece);
            if(!badMove)
               moves.add(pair);
            moveRow += X[i];
            moveColumn += Y[i];
         }
      }
      return moves;
   }
   private static ArrayList<Pair<Integer>> getRookMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int[] X = {0, 0, -1, 1};
      int[] Y = {-1, 1, 0, 0};
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int i = 0; i < BISHOP_ROOK_DIRECTIONS_SIZE; i++) {
         int moveRow = currentRow + X[i];
         int moveColumn = currentColumn + Y[i];
         while (moveRow >= 0 && moveColumn >= 0 && moveRow < Board.BOARD_SIZE && moveColumn < Board.BOARD_SIZE) {
            if (board.isTileOccupied(moveRow, moveColumn)) {
               if (board.getPiece(moveRow, moveColumn).isWhite() != piece.isWhite()) {
                  moves.add(new Pair<>(moveRow, moveColumn));
               }
               break;
            }
            moves.add(new Pair<>(moveRow, moveColumn));
            moveRow += X[i];
            moveColumn += Y[i];
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getPawnMoves(Piece piece, Board board, Piece king) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      int moveDirection = piece.isWhite() ? -1 : 1;
      int moveRow = currentRow + moveDirection;
      if (!(board.isTileOccupied(moveRow, currentColumn))) {
         board.removePiece(piece.getCoordinates());
         Pair<Integer> pair = new Pair<>(moveRow, currentColumn);
         board.setPiece(pair, piece);
         boolean badMove = isInCheck(king, board);
         board.removePiece(pair);
         board.setPiece(piece.getCoordinates(), piece);
         if(!badMove)
            moves.add(pair);
      }
      if (!piece.hasPieceMoved()) {
         if (!board.isTileOccupied(currentRow + 2 * moveDirection, currentColumn) && !(board.isTileOccupied(moveRow, currentColumn))) {
            board.removePiece(piece.getCoordinates());
            Pair<Integer> pair = new Pair<>(currentRow + 2 * moveDirection, currentColumn);
            board.setPiece(pair, piece);
            boolean badMove = isInCheck(king, board);
            board.removePiece(pair);
            board.setPiece(piece.getCoordinates(), piece);
            if(!badMove)
               moves.add(pair);
         }
      }
      int attackRightColumn = currentColumn + 1;
      int attackLeftColumn = currentColumn - 1;
      if (attackRightColumn < Board.BOARD_SIZE) {
         if (board.isTileOccupied(moveRow, attackRightColumn)) {
            Piece destinationPiece = board.getPiece(moveRow, attackRightColumn);
            if (destinationPiece.isWhite() != piece.isWhite()) {
               board.removePiece(piece.getCoordinates());
               Pair<Integer> pair = new Pair<>(moveRow, attackRightColumn);
               board.setPiece(pair, piece);
               boolean badMove = isInCheck(king, board);
               board.removePiece(pair);
               board.setPiece(piece.getCoordinates(), piece);
               board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
               if(!badMove)
                  moves.add(pair);
            }
         }
      }
      if (attackLeftColumn >= 0) {
         if (board.isTileOccupied(moveRow, attackLeftColumn)) {
            Piece destinationPiece = board.getPiece(moveRow, attackLeftColumn);
            if (destinationPiece.isWhite() != piece.isWhite()) {
               board.removePiece(piece.getCoordinates());
               Pair<Integer> pair = new Pair<>(moveRow, attackLeftColumn);
               board.setPiece(pair, piece);
               boolean badMove = isInCheck(king, board);
               board.removePiece(pair);
               board.setPiece(piece.getCoordinates(), piece);
               board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
               if(!badMove)
                  moves.add(pair);
            }
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getPawnMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      int moveDirection = piece.isWhite() ? -1 : 1;
      int moveRow = currentRow + moveDirection;
      if (!(board.isTileOccupied(moveRow, currentColumn))) {
         moves.add(new Pair<>(moveRow, currentColumn));
      } else {
         System.out.println(board.getPiece(moveRow, currentColumn).getType() + " is occupying on " +
               "coord " + moveRow + "," + currentColumn);
      }
      if (!piece.hasPieceMoved()) {
         if (!board.isTileOccupied(currentRow + 2 * moveDirection, currentColumn) && !(board.isTileOccupied(moveRow, currentColumn))) {
            moves.add(new Pair<>(currentRow + 2 * moveDirection, currentColumn));
         }
      }
      int attackRightColumn = currentColumn + 1;
      int attackLeftColumn = currentColumn - 1;
      if (attackRightColumn < Board.BOARD_SIZE) {
         if (board.isTileOccupied(moveRow, attackRightColumn)) {
            if (board.getPiece(moveRow, attackRightColumn).isWhite() != piece.isWhite()) {
               moves.add(new Pair<>(moveRow, attackRightColumn));
            }
         }
      }
      if (attackLeftColumn >= 0) {
         if (board.isTileOccupied(moveRow, attackLeftColumn)) {
            if (board.getPiece(moveRow, attackLeftColumn).isWhite() != piece.isWhite()) {
               moves.add(new Pair<>(moveRow, attackLeftColumn));
            }
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getQueenMoves(Piece piece, Board board,Piece king) {
      ArrayList<Pair<Integer>> aux = getBishopMoves(piece, board,king);
      aux.addAll(getRookMoves(piece, board,king));
      return aux;
   }
   private static ArrayList<Pair<Integer>> getQueenMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> aux = getBishopMoves(piece, board);
      aux.addAll(getRookMoves(piece, board));
      return aux;
   }

   private static ArrayList<Pair<Integer>> getKingMovesNoCheck(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int moveRow = currentRow - 1; moveRow <= currentRow + 1; moveRow++) {
         for (int moveColumn = currentColumn - 1; moveColumn <= currentColumn + 1; moveColumn++) {
            if (moveRow == currentRow && moveColumn == currentColumn) {
               continue;
            }
            if (moveRow >= 0 && moveRow < Board.BOARD_SIZE && moveColumn >= 0 && moveColumn < Board.BOARD_SIZE) {
               if (!board.isTileOccupied(moveRow, moveColumn)) {
                  moves.add(new Pair<>(moveRow, moveColumn));
               } else {
                  if (board.getPiece(moveRow, moveColumn).isWhite() != piece.isWhite()) {
                     moves.add(new Pair<>(moveRow, moveColumn));
                  }
               }
            }
         }
      }
      return moves;
   }

   private static ArrayList<Pair<Integer>> getKingMoves(Piece piece, Board board) {
      ArrayList<Pair<Integer>> moves = new ArrayList<>();
      int currentRow = piece.getRow();
      int currentColumn = piece.getColumn();
      for (int moveRow = currentRow - 1; moveRow <= currentRow + 1; moveRow++) {
         for (int moveColumn = currentColumn - 1; moveColumn <= currentColumn + 1; moveColumn++) {
            if (moveRow == currentRow && moveColumn == currentColumn) {
               continue;
            }
            if (moveRow >= 0 && moveRow < Board.BOARD_SIZE && moveColumn >= 0 && moveColumn < Board.BOARD_SIZE) {
               if (!board.isTileOccupied(moveRow, moveColumn)) {
                  board.removePiece(piece.getCoordinates());
                  Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
                  board.setPiece(pair, piece);
                  boolean badMove = isInCheck(moveRow,moveColumn,piece.isWhite(), board);
                  board.removePiece(pair);
                  board.setPiece(piece.getCoordinates(), piece);
                  if(!badMove)
                     moves.add(pair);
               } else {
                  Piece destinationPiece = board.getPiece(moveRow, moveColumn);
                  if (destinationPiece.isWhite() != piece.isWhite()) {
                     board.removePiece(piece.getCoordinates());
                     Pair<Integer> pair = new Pair<>(moveRow, moveColumn);
                     board.setPiece(pair, piece);
                     boolean badMove = isInCheck(moveRow,moveColumn,piece.isWhite(), board);
                     board.removePiece(pair);
                     board.setPiece(piece.getCoordinates(), piece);
                     board.setPiece(destinationPiece.getCoordinates(),destinationPiece);
                     if(!badMove)
                        moves.add(pair);
                  }
               }
            }
         }
      }
      return moves;
   }
}