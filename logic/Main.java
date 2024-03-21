package logic;

import commands.MoveCommand;

import java.io.BufferedReader;
import java.util.Scanner;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
   private static String features() {
      return    "feature"
            + " sigint=0"
            + " san=0"
            + " name=\"PlaceHolder\"\n";
   }
   private static BufferedOutputStream output = new BufferedOutputStream (System.out);
   private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
   private static void sendToXboard(String command){
   	try {
   	output.write(command.getBytes());
   	output.flush();}
   	catch(Exception e){
   	System.out.println("# Write exception");
   	System.exit(1);
   	}
   }
   private static boolean checkForMove(String input) {
      if (input.length() < 4 || input.length() >5) {
          return false;
      }
  
      char secondChar = input.charAt(1);
      char thirdChar = input.charAt(2);
      char fourthChar = input.charAt(3);
  
      if (!Character.isDigit(secondChar) && secondChar != '@') {
          return false;
      }
  
      if (!Character.isLetter(thirdChar) || thirdChar < 'a' || thirdChar > 'h') {
          return false;
      }
      if(!Character.isDigit(fourthChar)){
         return false;
      }

      return true;
  }
  
   public static void main(String[] args) throws IOException {
      MoveCommand moveCommand = new MoveCommand();
      Player player1 = null;
      Player player2 = null;
      Board board = null;
      String line;
      Bot bot = new Bot();
      while ((line = reader.readLine())!=null) {
         String command = line;
         if (command.equals("quit")) {
            break;
         }
         int x;
         String[] commandParts = command.split(" ");
         String action = commandParts[0];
         if(checkForMove(action)){
            moveCommand.execute(action);
            moveCommand.changePlayers();
            if(bot.isOnPause()){
               continue;
            }
            String botMove = bot.calculateNextMove();
            if(botMove.equals("resign")){
               sendToXboard(botMove + "\n");
               continue;
            }
            moveCommand.execute(botMove);
            moveCommand.changePlayers();
            sendToXboard("move " + botMove + "\n");
            continue;
         }

         switch (action) {
            case "xboard" -> sendToXboard("");
            case "protover" -> {
               String feat = features();
               sendToXboard(feat);
            }
            case "force" ->{
               bot.Pause();
            }
            case "new" -> {
               bot.Go();
               GameState.reset();
               player1 = new Player(true);
               player2 = new Player(false);
               board = new Board(player1, player2);
               GameState.setBoard(board);
               bot.setBlack(player2);
               bot.setBoard(board);
               moveCommand = new MoveCommand(board);
               moveCommand.setPlayerNow(player1);
               moveCommand.setPlayerEnemy(player2);
            }
            case "move" -> {
               String move = commandParts[1];
               moveCommand.execute(move);
               moveCommand.changePlayers();
               if(bot.isOnPause()){
                  continue;
               }
               String botMove = bot.calculateNextMove();
               moveCommand.execute(botMove);
               moveCommand.changePlayers();
              	sendToXboard("move " + botMove + "\n");
            }
            case "go" -> {
               // String move = commandParts[1];
               //moveCommand.execute(move);
               //moveCommand.changePlayers();
               bot.Go();
               if(moveCommand.getPlayerNow() != bot.getBlack());
               String botMove = bot.calculateNextMove();
               moveCommand.execute(botMove);
               moveCommand.changePlayers();
               sendToXboard("move " + botMove + "\n");
            }
            /**
             * DELETE THIS
             */
            case "1" -> {
               System.out.println("onBoard:" + player1.getOnBoardList());
               System.out.println("captured:" + player1.getCapturedList());
               System.out.println("king on position " + player1.getAllyKing().getRow() + "," + player1.getAllyKing().getColumn());
               System.out.println();
            }
            /**
             * DELETE THIS
             */
            case "2" -> {
               System.out.println("onBoard:" + player2.getOnBoardList());
               System.out.println("captured:" + player2.getCapturedList());
               System.out.println("king on position " + player2.getAllyKing().getRow() + "," + player2.getAllyKing().getColumn());
               System.out.println();
            }
            case "print" -> {
               System.out.println();
               System.out.println(moveCommand.getPlayerNow().isWhite());
               board.displayBoard();
            }
            case "?" ->{
               bot.Go();
               if(moveCommand.getPlayerNow() != bot.getBlack());
               String botMove = bot.calculateNextMove();
               moveCommand.execute(botMove);
               moveCommand.changePlayers();
               sendToXboard("move " + botMove + "\n");
            
            }
            default ->
               // Handle unsupported commands or display error messages
               //sendToXboard("move a7a5\n");
               x = 5;
         }
         /*
         if (board != null) {

         }
         */
      }
   }
}
