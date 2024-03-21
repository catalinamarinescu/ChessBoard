package logic;

import java.util.ArrayList;

public class CapturedList {
   private final ArrayList<Piece> captured;
   public CapturedList(){
      this.captured = new ArrayList<>();
   }

   public ArrayList<Piece> getCapturedList() {
      return captured;
   }
   public void addCaptured(Piece piece){
      this.captured.add(piece);
   }
   public void removeCaptured(Piece piece){
      this.captured.remove(piece);
   }
}
