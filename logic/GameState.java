package logic;

public class GameState {
    private static Board board;
    private static PlaySide playerColor;
    private static PlaySide botColor;

    private static boolean forceMode;
    private static boolean quitMode;
    private static boolean goMode;

    private GameState(){
    }

    public static void reset() {
        botColor = null;
        playerColor= null;
        forceMode = false;
        quitMode = false;
        board = new Board();
    }

    public static Board getBoard() {
        return board;
    }

    public static void setBoard(Board board) {
        GameState.board = board;
    }

    public static PlaySide getPlayerColor() {
        return playerColor;
    }

    public static void setPlayerColor(PlaySide playerColor) {
        GameState.playerColor = playerColor;
    }

    public static PlaySide getBotColor() {
        return botColor;
    }

    public static void setBotColor(PlaySide botColor) {
        GameState.botColor = botColor;
    }

    public static boolean isForceMode() {
        return forceMode;
    }

    public static void setForceMode(boolean forceMode) {
        GameState.forceMode = forceMode;
    }

    public static boolean isQuitMode() {
        return quitMode;
    }

    public static void setQuitMode(boolean quitMode) {
        GameState.quitMode = quitMode;
    }

}


