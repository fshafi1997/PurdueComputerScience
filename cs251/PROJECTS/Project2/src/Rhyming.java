import edu.princeton.cs.algs4.StdIn;

import java.util.LinkedList;
import java.lang.StringBuilder;
import java.util.ListIterator;

/**
 * I used Quick3way for sorting in rhyming because I was unsure about my Quick3wayBM
 * My Quick3wayBM does sort it but some values are not according to handout that's why I did't use it
 * I copied Quick3way from algs4 into my own class "myQuick3way" and used that for rhyming class.
 */

public class Rhyming {
    private static void addNode(RhymeDataStructure rhymeWordsTree, String newWord) {
        LinkedList<RhymeDataStructure> kids = rhymeWordsTree.getkids();
        if (!rhymeWordsTree.hasKids()) {
            rhymeWordsTree.addKid(newWord);
        } else {
            ListIterator<RhymeDataStructure> iter = kids.listIterator();
            String possibleRhyme = "";
            RhymeDataStructure rhymeWord = new RhymeDataStructure("");
            while (iter.hasNext()) {
                RhymeDataStructure rhymeTreeNow = iter.next();
                String rhyme = checkSuffix(newWord, rhymeTreeNow.getStrings());
                if (possibleRhyme.length() < rhyme.length()) {
                    possibleRhyme = rhyme;
                    rhymeWord = rhymeTreeNow;
                }
            }
            if (!possibleRhyme.equals("")) {
                if (possibleRhyme.equals(rhymeWordsTree.getStrings())) {
                    rhymeWordsTree.addKid(newWord);
                    return;
                }
                if ((possibleRhyme.equals(rhymeWord.getStrings())) && rhymeWord.hasKids()) {
                    addNode(rhymeWord, newWord);
                    return;
                } else {
                    RhymeDataStructure rhyme_branch = new RhymeDataStructure(possibleRhyme);
                    rhyme_branch.addKid(rhymeWord);
                    rhyme_branch.addKid(newWord);
                    rhymeWordsTree.addKid(rhyme_branch);
                    rhymeWordsTree.removeChild(rhymeWord);
                    return;
                }
            }
            rhymeWordsTree.addKid(newWord);
        }
    }

    private static String checkSuffix(String word1, String word2) {
        StringBuilder stringBuilder = new StringBuilder();
        char[] wordOne, wordTwo;
        int i;

        if (word1.length() > word2.length()) {
            wordOne = word2.toCharArray();
            wordTwo = word1.toCharArray();
        } else {
            wordOne = word1.toCharArray();
            wordTwo = word2.toCharArray();
        }
        int difference = wordTwo.length - wordOne.length;

        for (i = wordOne.length - 1; i >= 0; i--) {
            if (wordOne[i] == wordTwo[i + difference])
                stringBuilder.insert(0, wordOne[i]);
            else break;
        }
        return stringBuilder.toString();
    }


    private static RhymeDataStructure buildTree(String[] allWords) {
        rhymingWords = new RhymeDataStructure("");
        for (int i = 0; i < allWords.length; i++) {
            addNode(rhymingWords, allWords[i]);
        }
        return rhymingWords;
    }

    private static RhymeBranch[] getRhymeBranches(RhymeDataStructure rhymeWordsTree) {
        LinkedList<RhymeBranch> branches = new LinkedList<RhymeBranch>();

        if (rhymeWordsTree.hasKids()) {
            RhymeDataStructure presentBrnach;
            if (rhymeWordsTree.getStrings() != "") branches.add(new RhymeBranch(rhymeWordsTree.getStrings()));
            LinkedList<RhymeDataStructure> kid = rhymeWordsTree.getkids();
            ListIterator<RhymeDataStructure> iterator = kid.listIterator();
            while (iterator.hasNext()) {
                presentBrnach = iterator.next();
                for (RhymeBranch i : getRhymeBranches(presentBrnach))
                    branches.add(i);
            }
        }
        RhymeBranch[] finalB = new RhymeBranch[branches.size()];
        finalB = branches.toArray(finalB);
        return finalB;
    }

    private static String[] allKidsOfthatBranch(RhymeDataStructure rhymeTree, String string) {
        LinkedList<String> finalO = new LinkedList<String>();
        if (!rhymeTree.hasKids()) {
            finalO.add(rhymeTree.getStrings());
        } else {
            LinkedList<RhymeDataStructure> kid = rhymeTree.getkids();
            ListIterator<RhymeDataStructure> iterator = kid.listIterator();
            while (iterator.hasNext()) {
                for (String i : allKidsOfthatBranch(iterator.next(), string)) finalO.add(i);
            }
        }
        String[] words = new String[finalO.size()];
        words = finalO.toArray(words);
        return words;
    }

    private static RhymeDataStructure getNodeWithString(RhymeDataStructure rhymeTree, String string) {
        if (rhymeTree.getStrings().equals(string)) return rhymeTree;
        else if (rhymeTree.hasKids()) {
            LinkedList<RhymeDataStructure> children = rhymeTree.getkids();
            ListIterator<RhymeDataStructure> iter = children.listIterator();
            RhymeDataStructure out;
            while (iter.hasNext()) {
                out = getNodeWithString(iter.next(), string);
                if (out != null) return out;
            }
        }
        return null;
    }


    private static RhymeDataStructure rhymingWords;

    public static void main(String[] args) {

        LinkedList<String> tempList = new LinkedList<String>();
        while (!StdIn.isEmpty()) {
            tempList.add(StdIn.readString());
        }
        String[] testArray = new String[tempList.size()];
        testArray = tempList.toArray(testArray);


        rhymingWords = buildTree(testArray);
        RhymeBranch[] rhymingWordsArray = getRhymeBranches(rhymingWords);
        myQuick3way.sort(rhymingWordsArray);
        for (RhymeBranch i : rhymingWordsArray) {
            System.out.print("[ ");
            String[] currentWords = allKidsOfthatBranch(getNodeWithString(rhymingWords, i.getString()), i.getString());
            myQuick3way.sort(currentWords);
            for (int j = 0; j < currentWords.length; j++)
                currentWords[j] = currentWords[j].substring(0, currentWords[j].length() - i.getString().length()) + "|" + i.getString();
            for (int k = 0; k < currentWords.length; k++) {
                if (k < currentWords.length - 1) System.out.print(currentWords[k] + ", ");
                else System.out.println(currentWords[k] + " ]");
            }
        }

    }
}
