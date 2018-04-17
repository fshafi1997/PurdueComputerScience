import java.util.Arrays;
import java.util.Scanner;
import java.util.zip.CheckedInputStream;

/**
 * Created by farhanshafi on 10/10/16.
 */
public class Connect4 {

    public static final char YELLOW = 'X';
    public static final char NONE = ' ';
    public static final char RED = 'O';


    char[][] board = new char[6][7];


    public Connect4() {
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < 7; column++) {
                board[row][column] = NONE;
            }
        }
    }

    public char[][] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    public int putPiece(int column, char color) {
        for (int x = 5; x >= 0; x--) {
            if (board[x][column] == NONE) {
                board[x][column] = color;
                return x;
            }
        }
        return -1;
    }


    public char checkAlignment(int row, int column) {
        int counter = 1;
        boolean counterHorizontal = true;
        char Piece = board[row][column];
        if (board[row][column] == Piece) {
            if ((row - 1) >= 0 && board[row - 1][column] == Piece) {
                if ((row - 2) >= 0 && board[row - 2][column] == Piece) {
                    if ((row - 3) >= 0 && board[row - 3][column] == Piece) {
                        return Piece;
                    }
                }
            }
        }
        if (board[row][column] == Piece) {
            if ((row + 1) <= 5 && board[row + 1][column] == Piece) {
                if ((row + 2) <= 5 && board[row + 2][column] == Piece) {
                    if ((row + 3) <= 5 && board[row + 3][column] == Piece) {
                        return Piece;
                    }
                }
            }
        }
        if (board[row][column] == Piece) {
            for (int i = 0; i < 4; i++) {
                if (board[row][i] == Piece) {
                    for (int j = 0; j < 4; j++) {
                        if (board[row][i + j] != Piece) {
                            counter = 1;
                        } else {
                            counter++;
                            if (counter >= 4) {
                                return Piece;
                            }
                        }
                    }
                }
            }
        }
        int i = row;
        int j = column;
        int counter2 = 0;
        boolean endPoint = true;
        while (endPoint) {
            if (i > 0 && j < board[0].length - 1) {
                i--;
                j++;
            } else endPoint = false;
        }
        for (int k = i, l = j; k < board.length && l >= 0; k++, l--) {
            if (board[k][l] == Piece) {
                counter2++;
                if (counter2 == 4) {
                    return Piece;
                }
            } else counter2 = 0;
        }
        endPoint = true;
        counter2 = 0;
        int a = row;
        int b = column;

        while (endPoint) {
            if (a > 0 && b > 0) {
                a--;
                b--;
            } else endPoint = false;
        }
        for (int r = a, s = b; r < board.length && s < board[0].length; r++, s++) {
            if (board[r][s] == Piece) {
                counter2++;
                if (counter2 == 4) {
                    return Piece;
                }
            } else counter2 = 0;
        }
        return NONE;
    }


    public void printScreen() {
        System.out.println();
        for (int j = 0; j < board[0].length; j++) {
            System.out.printf("   %d", j);
        }
        System.out.println();

        System.out.print("  ");
        for (int j = 0; j < board[0].length; j++) {
            System.out.printf("----");
        }
        System.out.println("-");

        for (int i = 0; i < board.length; i++) {
            System.out.printf("%c  ", 'A' + i);
            for (int j = 0; j < board[0].length; j++) {
                System.out.printf("| %c ", board[i][j]);
            }
            System.out.println("|");
            System.out.print("   ");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print("----");
            }
            System.out.println("-");
        }
    }


    public void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOn = true;
        while (gameOn) {
            printScreen();
            System.out.println("Current player: " + RED);
            System.out.println("Choose a column: ");
            boolean invalidEntry = true;
            int redColumn = scanner.nextInt();
            while (invalidEntry) {
                if (redColumn >= 0 && redColumn <= 6) {
                    invalidEntry = false;
                } else {
                    System.out.println("Please re-enter the column");
                    redColumn = scanner.nextInt();
                }
            }
            int newRow = putPiece(redColumn, RED);
            char newChar = checkAlignment(newRow, redColumn);
            if (newChar != NONE) {
                printScreen();
                System.out.println("!!! Winner is " + newChar + " !!!");
                break;
            }
            printScreen();
            System.out.println("Current player: " + YELLOW);
            System.out.println("Choose a column: ");
            boolean invalidEntry2 = true;
            int yellowColumn = scanner.nextInt();
            while (invalidEntry2) {
                if (yellowColumn >= 0 && yellowColumn <= 6) {
                    invalidEntry2 = false;
                } else {
                    System.out.println("Please re-enter the column");
                    yellowColumn = scanner.nextInt();
                }
            }
            int newRow2 = putPiece(yellowColumn, YELLOW);
            char newChar2 = checkAlignment(newRow2, yellowColumn);
            if (newChar2 != NONE) {
                printScreen();
                System.out.println("!!! Winner is " + newChar2 + " !!!");
                break;
            }
        }
    }
}