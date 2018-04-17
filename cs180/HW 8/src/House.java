/**
 * Created by farhanshafi on 10/31/16.
 */
public class House extends Residence {
    private int numFloors;
    private boolean hasGarage;

    public House(String address, int numBedrooms, int numBathrooms, int squareFootage, double monthlyRent, int numFloors, boolean hasGarage){
        super(address,numBedrooms,numBathrooms,squareFootage,monthlyRent);
        this.numFloors = numFloors;
        this.hasGarage = hasGarage;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public boolean hasGarage() {
        return hasGarage;
    }

    public String toString(){
        return "Address: " + super.getAddress() + "\n" + "Number Bedrooms: " + super.getNumBedrooms() + "\n" + "Number Bathrooms: " +
                super.getNumBathrooms() + "\n" + "Square Footage: " + super.getSquareFootage() + "\n" + "Monthly Rent: " +
                super.getMonthlyRent() + "\n" + "Number Floors: " + getNumFloors() + "\n" + "Has Garage: " + hasGarage();
    }
}
