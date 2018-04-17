import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Cuckoo {
    private static long prime1, prime2;//Primes read in from file
    public static int r = 256; //initial size of array
    //public static int counter = 0;//Counter for put method
    //public static final int maxCapacity = (int) (2 * ((1 + 0.05) * counter));
    public static KeyValue[] table = new KeyValue[r];
    private static int currentSize, count;

    static class KeyValue {
        private int key;
        private int value;
        private long whichPrime;
        private boolean flag;

        public KeyValue(int key, int value) {
            this.key = key;
            this.value = value;
            this.whichPrime = 1;
            this.flag = false;
        }


        public int getKey() {
            return key;
        }

        public int getValue() {
            return value;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }

        public boolean getFlag() {
            return flag;
        }

        public long getWhichPrime() {
            return whichPrime;
        }

        public void setWhichPrime(int whichPrime) {
            this.whichPrime = whichPrime;
        }
    }


    //To clear the table make every index null
    private static void clear() {
        currentSize = 0;
        for (int i = 0; i < table.length; i++)
            table[i] = null;
    }


    //This will give index to place in table
    public static int hash1(Object key) {
        int indexToReturn = -1;
        int tableSize = r;

        int x = key.hashCode();
        long ax = prime1 * x;
        long twoPwr16 = (long) Math.pow(2, 16);
        long function = ax / twoPwr16;

        indexToReturn = (int) (function % tableSize);
        return indexToReturn;
    }

    //This will give index to place in table
    public static int hash2(Object key) {
        int indexToReturn = -1;
        int tableSize = r;

        int x = key.hashCode();
        long ax = prime2 * x;
        long twoPwr16 = (long) Math.pow(2, 16);
        long function = ax / twoPwr16;

        indexToReturn = (int) (function % tableSize);
        return indexToReturn;
    }

    /**
     * Rehash. (size of the hash table is doubled, and all elements that were previously stored
     * in the table must be re-inserted in the order they were stored in)
     */
    public static void rehash() {
        rehash(table.length);
    }

    public static void rehash(int newLength) {
        KeyValue[] oldTable = table;
        int oldCapacity = oldTable.length;

        KeyValue[] newTable = new KeyValue[newLength];
        table = newTable;

        StdOut.println("(hash " + prime1 + " " + prime2 + " " + r + ")");

        //count = 0;

        for (int i = 0; i < oldCapacity; i++) {
            if (oldTable[i] != null) {
                put(oldTable[i]);
            }
        }
    }


    //The put function
    public static void put(KeyValue pair) {
        //Upper bound of length of the sequence
        double LMAX = (3 * (Math.log(r) / Math.log(1.05)) + 1);

        int h1, h2;//flag 1 is h1 and 2 is h2

        if (count >= LMAX) {
            r = 2 * r;
            rehash(r);
        }


        h1 = hash1(pair.getKey());//setwhichhashused true == h1
        h2 = hash2(pair.getKey());//setwhichhashused false == h2


        if (table[h1]!=null && table[h1].getKey() == pair.getKey()){
            table[h1] = pair;
            table[h1].setFlag(true);
            StdOut.println("(" + h1 + " " + pair.getKey() + " " + pair.getValue() + ")");
            return;
        }
        else if (table[h2]!=null && table[h2].getKey() == pair.getKey()){
            table[h2] = pair;
            table[h2].setFlag(false);
            StdOut.println("(" + h2 + " " + pair.getKey() + " " + pair.getValue() + ")");
            return;
        }
        if (r < (int) (2 * ((1 + 0.05) * size()+2))) {
            expand();
        }
        if (table[h1] == null){
            table[h1] = pair;
            StdOut.println("(" + h1 + " " + pair.getKey() + " " + pair.getValue() + ")");
            table[h1].setFlag(true);
            currentSize++;
        }
        else if (table[h2]==null){
            table[h2] = pair;
            StdOut.println("(" + h2 + " " + pair.getKey() + " " + pair.getValue() + ")");
            table[h2].setFlag(false);
            currentSize++;
        }
        else{
            KeyValue dislodged = table[h1];
            table[h1] = pair;
            table[h1].setFlag(true);
            StdOut.println("(" + h1 + " " + pair.getKey() + " " + pair.getValue() + ")");
            count =0;
            put(dislodged,dislodged.getFlag());
        }
    }

    public static void put(KeyValue pair, boolean flag) {
        //Upper bound of length of the sequence

        double LMAX = (3 * (Math.log(r) / Math.log(1.05)) + 1);
        count++;

        int pos, i1 = 0, hash = 0;

        if (count >= LMAX) {
            r = 2 * r;
            rehash(r);
        }


        if (flag == true) {
            hash = hash2(pair.getKey());
        }
        if (flag == false){
            hash = hash1(pair.getKey());
        }

        if (r < (int) (2 * ((1 + 0.05) * size()+2))) {
            expand();
        }


        if (table[hash] == null){
            table[hash] = pair;
            table[hash].setFlag(!flag);
            StdOut.println("(" + hash + " " + pair.getKey() + " " + pair.getValue() + ")");
            currentSize++;
        }
        else {
            KeyValue dislodge = table[hash];
            table[hash] = pair;
            table[hash].setFlag(!flag);
            StdOut.println("(" + hash + " " + pair.getKey() + " " + pair.getValue() + ")");
            put(dislodge,dislodge.getFlag());
        }
    }

    //Will rehash with table size doubled
    private static void expand() {
        r = r * 2;
        rehash(r);
    }

    //The get function (returns value)
    public static void get(int key) {
        KeyValue key1;
        int h1 = hash1(key);
        int h2 = hash2(key);
        if (table[h1] != null) {
            if (table[h1].getKey() == key) {
                StdOut.println(table[h1].getValue());
            } else if (table[h2] != null) {
                if (table[h2].getKey() == key) {
                    StdOut.println(table[h2].getValue());
                }
                else {
                    StdOut.println("none");
                }
            }else {
                StdOut.println("none");
            }
        } else if (table[h2] != null) {
            if (table[h2].getKey() == key) {
                StdOut.println(table[h2].getValue());
            } else if (table[h1] != null) {
                if (table[h1].getKey() == key) {
                    StdOut.println(table[h1].getValue());
                }
                else {
                    StdOut.println("none");
                }
            }
            else {
                StdOut.println("none");
            }
        } else {
            StdOut.println("none");
        }
    }

    //The delete function
    public static void delete(int key) {
        KeyValue key1;
        int h1 = hash1(key);
        int h2 = hash2(key);
        if (table[h1] != null) {
            if (table[h1].getKey() == key) {
                table[h1]=null;
                currentSize--;
            } else if (table[h2] != null) {
                if (table[h2].getKey() == key) {
                    table[h2] = null;
                    currentSize--;
                }
            }
        } else if (table[h2] != null) {
            if (table[h2].getKey() == key) {
                table[h2] = null;
                currentSize--;
            } else if (table[h1] != null) {
                if (table[h1].getKey() == key) {
                    table[h1] = null;
                    currentSize--;
                }
            }
        }
    }

    //The contains method
    public static boolean contains(int key) {
        KeyValue key1;
        int h1 = hash1(key);
        int h2 = hash2(key);
        if (table[h1] != null) {
            if (table[h1].getKey() == key) {
                StdOut.println("yes");
                return true;
            } else if (table[h2] != null) {
                if (table[h2].getKey() == key) {
                    StdOut.println("yes");
                    return true;
                }
                else {
                    StdOut.println("no");
                    return false;
                }
            }else {
                StdOut.println("no");
                return false;
            }
        } else if (table[h2] != null) {
            if (table[h2].getKey() == key) {
                StdOut.println("yes");
                return true;
            } else if (table[h1] != null) {
                if (table[h1].getKey() == key) {
                    StdOut.println("yes");
                    return true;
                }
                else {
                    StdOut.println("no");
                    return false;
                }
            }
            else {
                StdOut.println("no");
                return false;
            }
        } else {
            StdOut.println("no");
            return false;
        }
    }

    //The size function(#elements in table)
    public static int size() {
        //return currentSize;
        int size = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null) {
                size++;
            }
        }
        return size;
    }

    //Length of table
    public static int capacity() {
        return table.length;
    }


    public static void main(String[] args) {
        int size2Read;
        String[] lines;
        prime1 = StdIn.readLong();
        prime2 = StdIn.readLong();
        size2Read = StdIn.readInt();
        lines = new String[size2Read + 1];

        int index = 0;
        while (!StdIn.isEmpty()) {
            lines[index] = StdIn.readLine();
            index++;
        }
        StdOut.println("(hash " + prime1 + " " + prime2 + " " + r + ")");

        clear();

        for (int i = 0; i < lines.length; i++) {
            String[] spllited = lines[i].split("\\W+");

            if (spllited.length == 1 && spllited[0].equals("size")) {
                StdOut.println(size());
            } else if (spllited.length == 3 && spllited[0].equals("put")) {
                put(new KeyValue(Integer.parseInt(spllited[1]), Integer.parseInt(spllited[2])));
                /*if (r < (int) (2 * ((1 + 0.05) * size()))) {
                    expand();
                }*/
            } else if (spllited.length == 2 && spllited[0].equals("get")) {
                get(Integer.parseInt(spllited[1]));
            } else if (spllited.length == 2 && spllited[0].equals("contains")) {
                contains(Integer.parseInt(spllited[1]));
            } else if (spllited.length == 2 && spllited[0].equals("delete")) {
                delete(Integer.parseInt(spllited[1]));
            }
        }

        /*prime1 = 9516234881L;
        prime2 = 2222239L;
        clear();
        KeyValue keyValue = new KeyValue(23, 32);
        put(keyValue);
        KeyValue keyValue1 = new KeyValue(12, 360);
        put(keyValue1);
        get(12);
        KeyValue keyValue2 = new KeyValue(12,95);
        put(keyValue2);
        get(12);
        KeyValue keyValue3 = new KeyValue(56,111);
        put(keyValue3);
        contains(84);
        delete(84);
        KeyValue keyValue4 = new KeyValue(84,234);
        put(keyValue4);
        contains(84);
        delete(12);
        KeyValue keyValue5 = new KeyValue(77,4096);
        put(keyValue5);
        KeyValue keyValue6 = new KeyValue(95,59);
        put(keyValue6);
        KeyValue keyValue7 = new KeyValue(2222,645);
        put(keyValue7);
        StdOut.println(size());
        KeyValue keyValue8 = new KeyValue(1,14);
        put(keyValue8);*/

    }
}
