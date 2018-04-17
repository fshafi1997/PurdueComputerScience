import java.util.Arrays;

/**
 * Created by farhanshafi on 10/11/16.
 */
public class ArrayBuilder {
    char[][] letterArray;
    int baseIndex;
    boolean isUpperCase;

    public ArrayBuilder(char baseLetter, int n, int m) {
        letterArray = new char[n][m];
        isUpperCase = Character.isUpperCase(baseLetter);
        if (isUpperCase == true){
            baseLetter = Character.toLowerCase(baseLetter);
        }
        baseIndex = findLetter(baseLetter);
    }

    char[] alphabets = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
            'x', 'y', 'z'};

    public int findLetter(char c) {
        int i = 0;
        while (i <= 25) {
            if (c == alphabets[i]) {
                return i;
            }
            i++;
        }

        return -1;
    }

    public void build() {
        for (int row = 0; row < letterArray.length; row++) {
            for (int column = 0; column < letterArray[row].length; column++) {
                char c = alphabets[(baseIndex + column) % 26];
                if (isUpperCase == true) {
                    c = Character.toUpperCase(c);
                }
                letterArray[row][column] = c;
            }
            baseIndex = (baseIndex + 1) % 26;
        }
    }

    public char[][] getLetterArray() {
        return Arrays.copyOf(letterArray, letterArray.length);
    }

    public void printLetterArray() {
        for (int i=0; i<letterArray.length; i++){
            for (int j=0; j<letterArray[i].length; j++){
                System.out.print(letterArray[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        ArrayBuilder example = new ArrayBuilder('a', 6, 6);
        example.build();
        example.printLetterArray();
    }
}