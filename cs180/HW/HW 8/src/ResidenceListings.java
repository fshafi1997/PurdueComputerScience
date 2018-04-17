import java.util.Arrays;

/**
 * Created by farhanshafi on 10/31/16.
 */
public class ResidenceListings {
    private int numResidences;
    private int maxResidences;
    private Residence[] residences;

    public ResidenceListings(){
        this.numResidences = 0;
        this.maxResidences = 10;
        this.residences = new Residence[10];
    }

    public void addResidence(Residence residence){
        if (numResidences == maxResidences){
           residences =  Arrays.copyOf(residences,maxResidences*2);
            maxResidences = maxResidences*2;
        }
        residences[numResidences] = residence;
        numResidences++;
    }

    public void removeResidence(Residence residence) throws NoSuchResidenceException{
        boolean x = false;
        for (int i=0;i<numResidences; i++){
            if (residences[i] == residence){
                residences[i] = null;
                x = true;
                if (i < numResidences-1) {
                    for (int j = i + 1; j < numResidences; j++) {
                        residences[j - 1] = residences[j];
                        residences[j] = null;
                    }
                }
                numResidences--;
            }
        }
        if (x == false){
            throw new NoSuchResidenceException("The residence was not Found!!");
        }
    }

    public Residence findResidenceByAddress(String address){
        for (int i = 0;i<maxResidences;i++){
            if (residences[i].getAddress().equals(address)){
                return residences[i];
            }
        }
        return null;
    }

    public int getNumResidences(){
        return this.numResidences;
    }

    public int getMaxResidences(){
        return this.maxResidences;
    }

    public Residence[] getResidences(){
        return residences;
    }
}
