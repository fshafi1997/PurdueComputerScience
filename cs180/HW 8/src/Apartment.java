/**
 * Created by farhanshafi on 10/31/16.
 */
public class Apartment extends Residence {
    private int floorNumber;

    public Apartment(String address, int numBedrooms, int numBathrooms, int squareFootage, double monthlyRent, int floorNumber){
        super(address,numBedrooms,numBathrooms,squareFootage,monthlyRent);
        this.floorNumber = floorNumber;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public String toString(){
        return "Address: " + super.getAddress() + "\n" + "Number Bedrooms: " + super.getNumBedrooms() + "\n" + "Number Bathrooms: " +
                super.getNumBathrooms() + "\n" + "Square Footage: " + super.getSquareFootage() + "\n" + "Monthly Rent: " +
                super.getMonthlyRent() + "\n" + "Floor Number: " + getFloorNumber();
    }


}
