import java.util.*;

public class Main  {

   public static final String PLAYER_1 = "O";
   public static final String PLAYER_2 = "X";
   public static final int STRIKE_NUM = 3;

   public static void main(String[] args) {

      play();

   }

  
   public static void play() {

      Scanner console = new Scanner(System.in);
      Map<String, String> numMap = new HashMap<>();

      boolean isPlaying = true;
      int inningCount = 0;
      String playerNum;
      String player;

      while(isPlaying) {

        
         if (inningCount == 0) {
            init(numMap);
         }

         
         while (true) {

            
            if (inningCount % 2 == 0) {
               System.out.print("Player1 - Enter the number: ");
               player = PLAYER_1;
            } else {
               System.out.print("Player2 - Enter the number: ");
               player = PLAYER_2;
            }

            playerNum = console.next();

            
            if (!isInputValidNumber(playerNum)) {
               System.out.println("[INVALID INPUT] Number should be between 1-9");
            } else if (!numMap.get(playerNum).equals("")) {
               System.out.println("[INVALID INPUT] Number is already taken.");
            } else {
               break;
            }
         }

         updateBoard(playerNum, player, numMap);
         inningCount++;

         
         if (isWinning(player, numMap)) {

            if (player.equals(PLAYER_1)) {
               System.out.println("Player 1  won! Yay!");
            } else {
               System.out.println("Player 2  won! Yay!");
            }

            isPlaying = playAgain(console);
            inningCount = 0;
         }

       
         if (!isWinning(player, numMap) && !numMap.containsValue("")) {
            System.out.println("It's draw!");

            isPlaying = playAgain(console);
            inningCount = 0;
         }
      }
   }

  
   private static void init(Map<String, String> numMap) {
      numMap.put("1", "");
      numMap.put("2", "");
      numMap.put("3", "");
      numMap.put("4", "");
      numMap.put("5", "");
      numMap.put("6", "");
      numMap.put("7", "");
      numMap.put("8", "");
      numMap.put("9", "");

      System.out.println();
      System.out.println("Welcome to Tic-tac-toe game!");
      System.out.println();

      showBoard(numMap);
   }

  
   public static void showBoard(Map<String, String> numMap) {
      System.out.print("| ");
      numMap.forEach ((strNum, OX) -> {
         if (!OX.equals("")) {
            System.out.print(OX);
         } else {
            System.out.print(strNum);
         }
         int intN = Integer.parseInt(strNum);
         System.out.print(" | ");
         if (intN % 3 == 0 && intN != 9) {
            System.out.println();
            System.out.println("|---+---+---|");
            System.out.print("| ");
         }
      });
      System.out.println();
      System.out.println();
   }

   public static boolean isInputValidNumber(String s) {

      boolean result = false;
      try {
        
         double d = Double.parseDouble(s);
         
         if (d >= 1 && d <= 9) {
            result = true;
         }
      } catch (NumberFormatException | NullPointerException nfe) {
         return false;
      }

      return result;
   }

  
   public static void updateBoard(String inputNum, String player, Map<String, String> numMap) {

      if (numMap.containsKey(inputNum)) {
         numMap.put(inputNum, player);
      }

      System.out.println();
      showBoard(numMap);
   }

 
   public static boolean isWinning(String player, Map<String, String> numMap) {

      StringBuilder sb = new StringBuilder();
      
      numMap.forEach((num, XO) -> {
         if (XO.equals(player)) {
            sb.append(num);
         }
      });
      return checkHorizontal(sb.toString()) || checkVerticalAndDiagonal(sb.toString());
   }

   
   private static boolean checkHorizontal(String marks) {
     
      String[] winningArray = {"123", "456", "789"};
      for (String s : winningArray) {
         if (marks.contains(s)) {
            return true;
         }
      }
      return false;
   }

   
   private static boolean checkVerticalAndDiagonal(String marks) {
      // winning: 147, 258, 369, 159, 357
      String[][] winning2DArray = {
              {"1", "4", "7"},
              {"2", "5", "8"},
              {"3", "6", "9"},
              {"1", "5", "9"},
              {"3", "5", "7"}
      };
      boolean isStrike;
      int strikeCount;

      for (int i = 0; i < winning2DArray.length; i++) {
        
         isStrike = false;
         strikeCount = 0;

         for (int j = 0; j < STRIKE_NUM; j++) {
            if (marks.contains(winning2DArray[i][j])) {
               isStrike = true;
               strikeCount++;
            }
         }

         if (isStrike && strikeCount == STRIKE_NUM) {
            return true;
         }

      }
      return false;
   }


  
   public static boolean playAgain(Scanner console) {

      System.out.print("Would you like to play again? (Y/N): ");
      String yesNo = console.next();
      if (yesNo.toLowerCase().charAt(0) == 'n') {
         System.out.println("Thank you for playing! Bye bye");
         return false;
      } else return yesNo.toLowerCase().charAt(0) == 'y';
   }

}
