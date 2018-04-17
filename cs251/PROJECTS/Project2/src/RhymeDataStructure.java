import java.util.LinkedList;

public class RhymeDataStructure {

    private LinkedList<RhymeDataStructure> kid;
    private String contents;

    public RhymeDataStructure( String strings ) {
        this.contents = strings;
        this.kid = new LinkedList<RhymeDataStructure>();
    }

    public LinkedList<RhymeDataStructure> getkids() {
        return kid;
    }

    public String getStrings() {
        return contents;
    }

    public void addKid( String newChild ) {
        RhymeDataStructure newNode = new RhymeDataStructure(newChild);
        this.kid.add(newNode);
    }

    public void removeChild ( RhymeDataStructure node ) {
        this.kid.remove(node);
    }


    public boolean hasKids() {
        return !kid.isEmpty();
    }

    public void addKid( RhymeDataStructure node) {
        this.kid.add(node);
    }
}
