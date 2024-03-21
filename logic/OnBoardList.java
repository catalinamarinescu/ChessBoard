package logic;

import java.util.ArrayList;

public class OnBoardList {
   private final ArrayList<Piece> onBoard;
   public OnBoardList(){
      this.onBoard = new ArrayList<>();
   }

   public ArrayList<Piece> getCapturedList() {
      return onBoard;
   }
   public void addOnBoard(Piece piece){
      this.onBoard.add(piece);
   }
   public void removeOnBoard(Piece piece){
      this.onBoard.remove(piece);
   }
   public void setOnBoard(ArrayList<Piece> pieces){
      this.onBoard.addAll(pieces);
   }
}
