/**
 * Created by farhanshafi on 9/29/16.
 */

import java.util.*;

public class MyUtils {
    public static String formatStr(String str) {
        int length = str.length();
        String substringSTR = str.substring(1, length);
        char firstCharacter = str.charAt(0);
        //String substringLastpart = str.substring(1);
        String firstCharacterExtracted = firstCharacter + "";
        String upperCaseFirst = firstCharacterExtracted.toUpperCase();
        String newSTR = upperCaseFirst + substringSTR.toLowerCase();
        return newSTR;
    }

    public static boolean isNumeric(String str) {
        int len = str.length();
        if (str == "") {
            return false;
        }
        if (len > 0) {
            for (int i = 0; i < len; i++) {
                if (str.charAt(i) == '.') {
                    if (str.charAt(i + 1) > '9' || str.charAt(i + 1) < '0' || str.charAt(i+1) == '.') {
                        return false;
                    }
                } else if (str.charAt(i) > '9' || str.charAt(i) < '0') {
                    return false;

                }
            }
        }
        return true;
    }


    public static void main(String[] args) {
        String newWord = formatStr("mIcEd");
        System.out.println(newWord);
        boolean newNumber = isNumeric("");
        System.out.println(newNumber);
    }
}
