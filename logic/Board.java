package logic;

import java.util.ArrayList;
import java.util.Optional;

public class Board {
   public static final int BOARD_SIZE = 8;
   private static final int PAWN_ROW = 1;
   private Optional<Piece>[][] board;

   public Board() {

   }

   public Board(Player white, Player black) {
      initializeBoard(white, black);
   }

   public void initializeBoard(Player white, Player black) {
      // Create a new 2D array of Optional<Piece> objects to represent the board
      board = new Optional[BOARD_SIZE][BOARD_SIZE];

      // Create and initialize all the white pieces
      board[0][0] = Optional.of(new Piece(Piece.PieceType.ROOK, false, 0, 0));
      board[0][1] = Optional.of(new Piece(Piece.PieceType.KNIGHT, false, 0, 1));
      board[0][2] = Optional.of(new Piece(Piece.PieceType.BISHOP, false, 0, 2));
      board[0][3] = Optional.of(new Piece(Piece.PieceType.QUEEN, false, 0, 3));
      board[0][4] = Optional.of(new Piece(Piece.PieceType.KING, false, 0, 4));
      board[0][5] = Optional.of(new Piece(Piece.PieceType.BISHOP, false, 0, 5));
      board[0][6] = Optional.of(new Piece(Piece.PieceType.KNIGHT, false, 0, 6));
      board[0][7] = Optional.of(new Piece(Piece.PieceType.ROOK, false, 0, 7));

      for (int i = 0; i < BOARD_SIZE; i++) {
         board[PAWN_ROW][i] = Optional.of(new Piece(Piece.PieceType.PAWN, false, 1, i));
         black.addOnBoard(board[1][i].get());
         black.addOnBoard(board[0][i].get());
      }

      // Create and initialize all the black pieces
      board[BOARD_SIZE - 1][0] = Optional.of(new Piece(Piece.PieceType.ROOK, true, 7, 0));
      board[BOARD_SIZE - 1][1] = Optional.of(new Piece(Piece.PieceType.KNIGHT, true, 7, 1));
      board[BOARD_SIZE - 1][2] = Optional.of(new Piece(Piece.PieceType.BISHOP, true, 7, 2));
      board[BOARD_SIZE - 1][3] = Optional.of(new Piece(Piece.PieceType.QUEEN, true, 7, 3));
      board[BOARD_SIZE - 1][4] = Optional.of(new Piece(Piece.PieceType.KING, true, 7, 4));
      board[BOARD_SIZE - 1][5] = Optional.of(new Piece(Piece.PieceType.BISHOP, true, 7, 5));
      board[BOARD_SIZE - 1][6] = Optional.of(new Piece(Piece.PieceType.KNIGHT, true, 7, 6));
      board[BOARD_SIZE - 1][7] = Optional.of(new Piece(Piece.PieceType.ROOK, true, 7, 7));
      for (int i = 2; i < BOARD_SIZE - 2; i++)
         for (int j = 0; j < BOARD_SIZE; j++){
            board[i][j] = Optional.empty();
         }

      for (int i = 0; i < 8; i++) {
         board[BOARD_SIZE - 1 - PAWN_ROW][i] = Optional.of(new Piece(Piece.PieceType.PAWN, true, 6, i));
         white.addOnBoard(board[BOARD_SIZE - 1 - PAWN_ROW][i].get());
         white.addOnBoard(board[BOARD_SIZE - 1][i].get());
      }
      white.setKings(this);
      black.setKings(this);
   }

   /**
    * only use immediately after use of initial board
    */
   public Piece getBlackKnight() {
      return board[0][4].get();
   }

   /**
    * only use immediately after use of initial board
    */
   public Piece getWhiteKnight() {
      return board[BOARD_SIZE - 1][4].get();
   }

   public boolean isTileOccupied(int row, int column) {
      return board[row][column].isPresent();
   }

   public boolean isTileOccupied(Pair<Integer> coordinates) {
      return board[coordinates.getFirst()][coordinates.getSecond()].isPresent();
   }

   public void setPiece(int row, int column, Piece piece) {
      board[row][column] = Optional.of(piece);
   }

   public void setPiece(Pair<Integer> coordinates, Piece piece) {
      board[coordinates.getFirst()][coordinates.getSecond()] = Optional.of(piece);
   }

   public Piece getPiece(int row, int column) {
      return board[row][column].get();
   }

   public Piece getPiece(Pair<Integer> coordinates) {
      return board[coordinates.getFirst()][coordinates.getSecond()].get();
   }

   public void replaceSpot(int row, int column, int replaceRow, int replaceColumn) {
      board[row][column] = board[replaceRow][replaceColumn];
   }

   public void replaceSpot(int row, int column, Pair<Integer> replaceCoordinates) {
      board[row][column] = board[replaceCoordinates.getFirst()][replaceCoordinates.getSecond()];
   }

   public void replaceSpot(Pair<Integer> originalCoordinates, int replaceRow, int replaceColumn) {
      board[originalCoordinates.getFirst()][originalCoordinates.getSecond()] =
            board[replaceRow][replaceColumn];
   }

   public void replaceSpot(Pair<Integer> originalCoordinates, Pair<Integer> replaceCoordinates) {
      board[originalCoordinates.getFirst()][originalCoordinates.getSecond()] =
            board[replaceCoordinates.getFirst()][replaceCoordinates.getSecond()];
   }


   public Optional<Piece>[][] getBoard() {
      return board;
   }

   public void setBoard(Optional<Piece>[][] board) {
      this.board = board;
   }

   public void removePiece(int row, int column) {
      board[row][column] = Optional.empty();
   }

   public void removePiece(Pair<Integer> coordinates) {
      board[coordinates.getFirst()][coordinates.getSecond()] = Optional.empty();
   }
   public void displayBoard() {
      for (int row = 0; row < BOARD_SIZE; row++) {
         for (int col = 0; col < BOARD_SIZE; col++) {
            String display;
            if (!(isTileOccupied(row,col))) {
               display = "[ ] "; // Empty spot
            } else {
               Piece piece = getPiece(row,col);
               String color = piece.isWhite() ? "W" : "B";
               String pieceType = switch (piece.getType()){
                  case BISHOP -> "B";
                  case KNIGHT -> "N";
                  case ROOK -> "R";
                  case QUEEN -> "Q";
                  case PAWN -> "P";
                  case KING -> "K";
                  default -> "";
               };
               display = color + "-" + pieceType + " "; // Occupied spot
            }

            System.out.print(display);
         }
         System.out.println(); // Move to the next row
      }
   }

}