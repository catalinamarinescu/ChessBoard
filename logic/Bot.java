package logic;
import java.util.ArrayList;
import java.util.Random;
public class Bot {
    /* Edit this, escaped characters (e.g newlines, quotes) are prohibited */
    private static final String BOT_NAME = "MyBot";

    /* Declare custom fields below */

    public Player white;
    public Player black;
    public Board board;
    public boolean onPause;

    /* Declare custom fields above */

    public Bot() {
        /* Initialize custom fields here */
        white = new Player(true);
        black = new Player(false);
        board = new Board(white,black);
        onPause = false;
    }

    /**
     * Record received move (either by enemy in normal play,
     * or by both sides in force mode) in custom structures
     * @param move received move
     * @param sideToMove side to move (either PlaySide.BLACK or PlaySide.WHITE)
     */
    public void recordMove(Move move, PlaySide sideToMove) {
        /* You might find it useful to also separately record last move in another custom field */
    }

    /**
     * Calculate and return the bot's next move
     * @return your move
     */
    
    public String calculateNextMove() {
        /* Calculate next move for the side the engine is playing (Hint: Main.getEngineSide())
         * Make sure to record your move in custom structures before returning.
         *
         * Return move that you are willing to submit
         * Move is to be constructed via one of the factory methods defined in Move.java */
        int limit = 100;
        Random random = new Random();
        while(true){
            limit--;
            if(limit <= 0)
                return "resign";
            if(random.nextInt()%2 == 0 || black.getCapturedList().size() == 0){
            Piece randomPiece =
                  black.getOnBoardList().get(random.nextInt(black.getOnBoardList().size()));
            ArrayList<Pair<Integer>> moves = GetMovesOfPiece.getMoves(randomPiece,board,black.getAllyKing());
            if(!(moves.isEmpty())){
                return posToString(randomPiece.getCoordinates(),
                      moves.get(random.nextInt(moves.size())),randomPiece.getType()) ;}
            } else {
                Piece randomPiece =
                black.getCapturedList().get(random.nextInt(black.getCapturedList().size()));
                int randRow = random.nextInt(Board.BOARD_SIZE - 1 );
                int randColumn = random.nextInt(Board.BOARD_SIZE-1);
                while(board.isTileOccupied(randRow,randColumn)){
                    randRow = random.nextInt(Board.BOARD_SIZE);
                    randColumn = random.nextInt(Board.BOARD_SIZE);
                }
                return posToString(randomPiece.getType(), new Pair<Integer>(randRow,randColumn));
            }

        }

    }
    public String posToString(Pair<Integer> startPiece, Pair<Integer> finishPiece,Piece.PieceType type){
        char[] encoded = {'k','k','k','k'};
        Integer startRow = startPiece.getFirst();
        Integer startColumn = startPiece.getSecond();
        Integer finishRow = finishPiece.getFirst();
        Integer finishColumn = finishPiece.getSecond();
        encoded[0] = (char) (startColumn + (int)'a');
        encoded[2] = (char) (finishColumn + (int)'a');
        encoded[1] = (char) ((int)'8' - startRow);
        encoded[3] = (char) ((int)'8' -  finishRow);
        if(type == Piece.PieceType.PAWN){
            if(encoded[3] == '8' || encoded[3] == '1' && encoded[1] != '@')
                if(encoded[0] == encoded[2])
                return new String(encoded) + "w";
        }
        return new String(encoded);
    }
    public String posToString(Piece.PieceType type, Pair<Integer> finishPiece){
        char[] encoded = {'k','k','k','k'};
        Integer finishRow = finishPiece.getFirst();
        Integer finishColumn = finishPiece.getSecond();
        encoded[0] = switch (type){
            case BISHOP -> 'B';
            case KNIGHT -> 'N';
            case ROOK -> 'R';
            case QUEEN -> 'Q';
            case PAWN -> 'P';
            case KING -> 'K';
         };
        encoded[1] = '@';
        encoded[2] = (char) (finishColumn + (int)'a');
        encoded[3] = (char) ((int)'8' -  finishRow);
        if(type == Piece.PieceType.PAWN){
            if(encoded[3] == '8' || encoded[3] == '1' && encoded[1] != '@')
                if(encoded[0] == encoded[2])
                return new String(encoded) + "w";
        }
        return new String(encoded);
    }

    public static String getBotName() {
        return BOT_NAME;
    }

    public Player getWhite() {
        return white;
    }

    public void setWhite(Player white) {
        this.white = white;
    }

    public Player getBlack() {
        return black;
    }

    public void setBlack(Player black) {
        this.black = black;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isOnPause() {
        return onPause;
    }

    public void setOnPause(boolean onPause) {
        this.onPause = onPause;
    }

    public void Pause(){
        onPause = true;
    }

    public void Go(){
        onPause = false;
    }
}