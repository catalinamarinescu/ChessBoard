package moves;

import logic.Pair;
import logic.Piece;

import java.util.ArrayList;

public class DecodeMove {
    private DecodeMove(){

    }
    public static ArrayList<Pair<Integer>> decode(String move) {
        ArrayList<Pair<Integer>> coordinates = new ArrayList<>();
        final int fromY = decodeColumn(move.charAt(0));
        final int fromX = decodeLine(move.charAt(1));
        final int toY = decodeColumn(move.charAt(2));
        final int toX = decodeLine(move.charAt(3));

        Pair<Integer> fromPair = new Pair<>(fromX, fromY);
        Pair<Integer> toPair = new Pair<>(toX, toY);
        coordinates.add(fromPair);
        coordinates.add(toPair);

        return coordinates;
    }
    public static Piece.PieceType getReplacement (String move){
        return switch (move.charAt(4)) {
            case 'r' -> Piece.PieceType.ROOK;
            case 'b' -> Piece.PieceType.BISHOP;
            case 'n' -> Piece.PieceType.KNIGHT;
            case 'q' -> Piece.PieceType.QUEEN;
            default -> null;
        };
    }

    private static int decodeColumn(char coordinate) {
        return (int) coordinate - (int) 'a';
    }

    private static int decodeLine(char coordinate) {
        return 8 - ((int) coordinate - (int) '0');
    }

}
