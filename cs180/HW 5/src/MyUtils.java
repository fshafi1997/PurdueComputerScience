/**
 * Created by farhanshafi on 9/29/16.
 */

import java.util.*;

public class MyUtils {
    public static String formatStr(String str) {
        int length = str.length();
        String substringSTR = str.substring(1, length);
        char firstCharacter = str.charAt(0);
        String firstCharacterExtracted = firstCharacter + "";
        String upperCaseFirst = firstCharacterExtracted.toUpperCase();
        String newSTR = upperCaseFirst + substringSTR;
        return newSTR;
    }

    public static boolean isNumeric(String str) {
        int len = str.length();
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if ((str.charAt(i) == '0') || (str.charAt(i) == '1') || (str.charAt(i) == '2') || (str.charAt(i) == '3')
                        || (str.charAt(i) == '4') || (str.charAt(i) == '5') || (str.charAt(i) == '6') ||
                        (str.charAt(i) == '7') || (str.charAt(i) == '8') || (str.charAt(i) == '9') ||
                        (str.charAt(i) == '.')) {
                    return true;
                } else return false;
            }
        }
        return false;
    }


    public static void main(String[] args) {
        String newWord = formatStr("pikachu");
        System.out.println(newWord);
        boolean newNumber = isNumeric("");
        System.out.println(newNumber);
    }
}
