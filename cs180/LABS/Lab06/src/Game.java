import java.util.*;

public class Game {
    private final static int ROCK = 1;
    private final static int PAPER = 2;
    private final static int SCISSORS = 3;
    private final static int EXIT = 4;
    Random random = new Random();

    public void runGame() {
        System.out.println("Welcome");
        System.out.println("Please enter an option:");
        System.out.print("1. Rock" + "\n" + "2. Paper" + "\n" + "3. Scissors" + "\n" + "4. Exit");
    }

    private int checkWinner(int move1, int move2) {
        if ((move1 == PAPER) && (move2 == ROCK)) {
            return 1;
        } else if ((move1 == ROCK) && (move2 == PAPER)) {
            return 2;
        } else if ((move1 == ROCK) && (move2 == SCISSORS)) {
            return 1;
        } else if ((move1 == SCISSORS) && (move2 == ROCK)) {
            return 2;
        } else if ((move1 == PAPER) && (move2 == SCISSORS)) {
            return 2;
        } else if ((move1 == SCISSORS) && (move2 == PAPER)) {
            return 1;
        } else if ((move1 == ROCK) && (move2 == ROCK)) {
            return 0;
        } else if ((move1 == PAPER) && (move2 == PAPER)) {
            return 0;
        } else if ((move1 == SCISSORS) && (move2 == SCISSORS)) {
            return 0;
        }
        return 0;
    }

    private int simulateComputerMove() {
        int computerInput = random.nextInt(3) + 1;
        return computerInput;
    }

    public static String functionForUser(int value){
        switch (value){
            case 1:
                return  "rock!";
            case 2:
                return "paper!";
            case 3:
                return "scissors!";
        }
        return "Exit";
    }
    public static String functionForComputer(int value2){
        if (value2 == 1){
            return "rock!";
        }else if (value2 == 2){
            return "paper!";
        }else if (value2 == 3){
            return "scissors!";
        }
        return "Exit";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game1 = new Game();
        int userInput = 0;
        do {
            game1.runGame();
            userInput = scanner.nextInt();
            int computerMove = game1.simulateComputerMove();
            if (userInput >= 4){
             break;
            }
            System.out.println("You played " + functionForUser(userInput));
            System.out.println("The computer played " + functionForComputer(computerMove));
            int winner = game1.checkWinner(userInput, computerMove);
            if (winner == 0) {
                System.out.println("Draw!");
            } else if (winner == 1) {
                System.out.println("You Win!");
            } else System.out.println("Computer Wins!");
        } while (userInput != 4);
        System.out.println("Thanks for playing!");
    }
}

