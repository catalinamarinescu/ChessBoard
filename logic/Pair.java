package logic;

import jdk.jfr.Percentage;

import java.util.ArrayList;
import java.util.Objects;

public class Pair<T> {
   private T first;
   private T second;

   public Pair(T first, T second) {
      this.first = first;
      this.second = second;
   }

   public Pair(Pair<T> other) {
      this.first = other.first;
      this.second = other.second;
   }

   public T getFirst() {
      return first;
   }

   public void setFirst(T first) {
      this.first = first;
   }

   public T getSecond() {
      return second;
   }

   public void setSecond(T second) {
      this.second = second;
   }
   @Override
   public String toString(){
      return first.toString() + " " + second.toString();
   }
   @Override
   public boolean equals(Object obj){
      if (this == obj) {
         return true;
      }
      if (obj == null || this.getClass() != obj.getClass()) {
         return false;
      }
      Pair<Integer> pair = (Pair<Integer>) obj;
      return pair.getFirst() == this.first && pair.getSecond() == this.second;
   }
   @Override
   public int hashCode() {
      return Objects.hash(first, second);
   }
}