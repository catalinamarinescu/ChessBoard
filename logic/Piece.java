package logic;

import java.util.ArrayList;

public class Piece {

   private boolean killed;
   private boolean white = false;
   private Integer row;
   private Integer column;
   private boolean promoted = false;
   private boolean pieceMoved = false;

   public enum PieceType {
      PAWN,
      ROOK,
      BISHOP,
      KNIGHT,
      QUEEN,
      KING
   }

   private PieceType type = PieceType.PAWN;

   public Piece() {
      this.killed = false;
   }

   public Piece(boolean white) {
      this.setWhite(white);
      this.killed = false;
   }

   public Piece(boolean white, PieceType type) {
      this.setWhite(white);
      this.killed = false;
      this.type = type;
   }

   public Piece(boolean white, int row, int column) {
      this.setWhite(white);
      this.killed = false;
      this.row = row;
      this.column = column;
   }

   public Piece(PieceType type, boolean white, int row, int column) {
      this.setWhite(white);
      this.killed = false;
      this.row = row;
      this.column = column;
      this.type = type;
   }

   public boolean isWhite() {
      return this.white;
   }

   public void setWhite(boolean white) {
      this.white = white;
   }

   public boolean isKilled() {
      return this.killed;
   }

   public void setKilled(boolean killed) {
      this.killed = killed;
   }

   public Integer getRow() {
      return row;
   }

   public void setRow(Integer row) {
      this.row = row;
   }

   public Integer getColumn() {
      return column;
   }

   public void setColumn(Integer column) {
      this.column = column;
   }

   public void kill() {
      this.killed = true;
   }

   public PieceType getType() {
      return type;
   }

   public void setType(PieceType type) {
      this.type = type;
   }

   public boolean isPromoted() {
      return promoted;
   }

   public void setPromoted(boolean promoted) {
      this.promoted = promoted;
   }

   public boolean hasPieceMoved() {
      return pieceMoved;
   }

   public void setPieceMoved(boolean pieceMoved) {
      this.pieceMoved = pieceMoved;
   }

   public void setCoordinates(Pair<Integer> coordinates) {
      this.row = coordinates.getFirst();
      this.column = coordinates.getSecond();
   }

   public void setCoordinates(int row, int column) {
      this.row = row;
      this.column = column;
   }

   public Pair<Integer> getCoordinates() {
      return new Pair<>(row, column);
   }

   public boolean isPieceMoved() {
      return pieceMoved;
   }

   @Override
   public String toString() {
      String whiteness;
       if (white) {
           whiteness = "W";
       } else {
           whiteness = "B";
       }
      return whiteness + "-" + type.toString();
   }

}



