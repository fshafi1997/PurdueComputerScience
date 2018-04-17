public class RhymeBranch implements Comparable {

    private String word;

    public RhymeBranch(String word) {
        this.word = word;
    }

    public String getString () {
        return this.word;
    }

    public int compareTo ( Object object ) {
        String compare2Object = ((RhymeBranch)object).getString();
        if ( word.length() == compare2Object.length() ) {
            return word.compareTo(compare2Object);
        } else {
            if ( word.length() == compare2Object.length()) return 0;
            else if ( word.length() < compare2Object.length() ) return -1;
            else if ( word.length() > compare2Object.length() ) return 1;
        }
        return -1;
    }
}
