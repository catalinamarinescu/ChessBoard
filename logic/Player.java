package logic;

import java.util.ArrayList;

public class Player {
    private boolean white;
    private ArrayList<Piece> onBoardList;
    private ArrayList<Piece> capturedList;
    private Piece allyKing;
    private Piece enemyKing;


    public Player(boolean white) {
        this.white = white;
        this.capturedList = new ArrayList<>();
        this.onBoardList = new ArrayList<>();
    }

    public void resetPlayer(Board board) {
        this.resetCaptured();
        this.resetOnBoard(board);
    }

    public ArrayList<Piece> getCapturedList() {
        return capturedList;
    }

    public void addCaptured(Piece piece) {
        this.capturedList.add(piece);
    }

    public void removeCaptured(Piece piece) {
        this.capturedList.remove(piece);
    }

    public void resetCaptured() {
        this.capturedList = new ArrayList<>();
    }

    private void resetOnBoard(Board board) {
        // maybe change a bit for more productivity
        this.onBoardList = new ArrayList<>();
        for (int i = 0; i < Board.BOARD_SIZE; i++)
            for (int j = 0; j < Board.BOARD_SIZE; j++)
                if (board.isTileOccupied(i, j)) {
                    if (board.getPiece(i, j).isWhite() == this.white) {
                        this.onBoardList.add(board.getPiece(i, j));
                    }
                }
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public ArrayList<Piece> getOnBoardList() {
        return onBoardList;
    }

    public void addOnBoard(Piece piece) {
        this.onBoardList.add(piece);
    }

    public void removeOnBoard(Piece piece) {
        this.onBoardList.remove(piece);
    }

    /**
     * use ONLY after setting/resetting the initial board
     */
    public void setKings(Board board) {
        if (white) {
            this.allyKing = board.getWhiteKnight();
            this.enemyKing = board.getBlackKnight();
        } else {
            this.allyKing = board.getBlackKnight();
            this.enemyKing = board.getWhiteKnight();
        }
    }

    public Piece getAllyKing() {
        return allyKing;
    }

    public Piece getEnemyKing() {
        return enemyKing;
    }
    /**
     * use ONLY after setting/resetting the initial board
     */
}
