import edu.princeton.cs.algs4.StdIn;

import java.util.Arrays;

public class Ferry {
    public static int[] array;
    public static int ferryLength;

    public static void putCarsandPrint(){
        boolean[][] mapOfFerry = new boolean[2][ferryLength + 1]; // This is for the two lanes and then length of lane
        Arrays.fill(mapOfFerry[0], false);
        mapOfFerry[0][0] = true; //both sides are set to 0 initially

        int[] lengthOfCars = new int[array.length]; // this contains the length of the cars
        int[][] placementOfCars = new int[array.length][ferryLength + 1]; //Will be placing cars in this array and will use this array for final


        int indexOfCar = 0; //This is like what number car it is
        boolean carPlacementDone = false;   //check if thecar been placed
        int numberOfCars2Load = 0; //Number of cars to load
        int point;
        int fs = 0;

        int sumOfLengths = 0;  //sum of the lengths of car in the lane of ferry

        while (true) {
            for (int index = 0; index < array.length; index++) {
                int lengthOfTheCarChosen = array[index];  //Length of the car chosen from the array
                /*If the car chosen has length 0 then break. If the car has already been placed then go to next car*/
                if (lengthOfTheCarChosen == 0) {
                    break;
                }
                if (carPlacementDone == true) {
                    continue;
                }

                point = fs;
                fs = fs ^ 1;
                lengthOfCars[++indexOfCar] = lengthOfTheCarChosen;
                //Getting sum of the lengths of all the cars
                sumOfLengths = sumOfLengths + lengthOfTheCarChosen;

                Arrays.fill(mapOfFerry[fs], false);

                boolean checkToLoadCar = false;

                for (int length = 0; length <= ferryLength; length++) {
                    if (mapOfFerry[point][length] == false){
                        continue;
                    }

                    if (length + lengthOfTheCarChosen <= ferryLength && sumOfLengths - (length + lengthOfTheCarChosen) <= ferryLength) {
                        mapOfFerry[fs][length + lengthOfTheCarChosen] = true;
                        //Car is put on the left side
                        placementOfCars[indexOfCar][length + lengthOfTheCarChosen] = 0;
                        checkToLoadCar = true;
                    }


                    if (sumOfLengths - length <= ferryLength) {
                        mapOfFerry[fs][length] = true;
                        //Car is put on right side lane
                        placementOfCars[indexOfCar][length] = 1;
                        checkToLoadCar = true;
                    }
                }
                if (!checkToLoadCar) {
                    carPlacementDone = true;
                } else{
                    numberOfCars2Load = indexOfCar;
                }
            }
            System.out.println(numberOfCars2Load); //The number of cars are printed and while loop ends
            break;
        }
    }

    //This method gets the lengths of the cars in the testfile and puts them in array
    public static void readTestFile(){
        int arraySize = StdIn.readInt();
        array = new int[arraySize+4];
        int index=0;
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            array[index] = p;
            index++;
        }
    }

    public static void main(String[] args) {
        readTestFile();
        int firstArg = 0;
        //Try catch for getting the args[0] that is the length of ferry
        if (args.length > 0) {
            try {
                firstArg = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Argument" + args[0] + " must be an integer.");
                System.exit(1);
            }
        }
        ferryLength = firstArg; //Getting length of ferry from args

        //This method will put the cars in either the left lane or the right lane and then print the total number of cars put in
        putCarsandPrint();
    }
}
