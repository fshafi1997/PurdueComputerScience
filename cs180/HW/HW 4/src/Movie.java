import java.lang.*;
public class Movie {
    private String name;
    private double criticRating;
    private double usersRating;
    private int numUsersRatings;

    //Constructor
    public Movie(String name, double criticRating, double usersRating, int numUsersRatings) {
        this.name = name;
        if ((criticRating >= 1 && criticRating <= 5)) {
            this.criticRating = criticRating;
        }else this.criticRating = 5;
        if ((usersRating >= 1 && usersRating <= 5)) {
            this.usersRating = usersRating;
        }else this.usersRating = 5;
        if ((numUsersRatings > 0)) {
            this.numUsersRatings = numUsersRatings;
        }else this.numUsersRatings = 5;
    }

    //accessors
    public String getName() {
        return name;
    }

    public double getCriticRating() {
        return criticRating;
    }

    public double getUsersRating() {
        return usersRating;
    }

    public int getNumUsersRatings() {
        return numUsersRatings;
    }

    //modifier add user rating

    public boolean addUserRating(int newRating) {
        if ((newRating >= 1 && newRating <= 5)) {
            usersRating = ((usersRating * numUsersRatings) + newRating) / ++numUsersRatings;
            return true;
        } else return false;
    }

    // method compareMovie

    //reviewRange
    private double reviewRange(){
        if (numUsersRatings >=0 && numUsersRatings<=1000){
            return 1;
        }else if (numUsersRatings >= 1001 && numUsersRatings<=5000){
            return 2;
        }else if (numUsersRatings >= 5001 && numUsersRatings<=10000){
            return 3;
        }else if (numUsersRatings >= 10001 && numUsersRatings<=15000){
            return 4;
        }else if (numUsersRatings >= 15001 && numUsersRatings<=20000){
            return 5;
        }else if (numUsersRatings >= 20001 && numUsersRatings<=25000){
            return 6;
        }else if (numUsersRatings >= 25001 && numUsersRatings<=30000){
            return 7;
        }else if (numUsersRatings >= 30001 && numUsersRatings<=50000){
            return 8;
        }else if (numUsersRatings >= 50001 && numUsersRatings<=100000){
            return 9;
        }else
            return 10;
    }

    //smartScore

    private double getSmartScore(){
        double smartScore = 0.5*criticRating+0.3*usersRating+0.1*reviewRange();
        return smartScore;
    }

    public static int compareMovies(Movie movie1, Movie movie2) {
        if ((movie1.getCriticRating() > movie2.getCriticRating()) && (movie1.getUsersRating() >= movie2.getUsersRating())) {
            return 1;
        } else if ((movie2.getCriticRating() > movie1.getCriticRating()) && (movie2.getUsersRating() >= movie1.getUsersRating())) {
            return 2;
        } else if (movie1.getCriticRating() == movie2.getCriticRating()) {
            if (movie1.getUsersRating() > movie2.getUsersRating()) ;
            return 1;
        } else if (movie2.getUsersRating() > movie1.getUsersRating()) {
            return 2;
        } else if ((movie1.getCriticRating()>movie2.getCriticRating()) && (movie1.getUsersRating()<movie2.getUsersRating()) &&
                (movie1.getSmartScore()>movie2.getSmartScore())){
            return 1;
        }else if ((movie2.getCriticRating()>movie1.getCriticRating()) && (movie2.getUsersRating()<movie1.getUsersRating())
                && (movie2.getSmartScore()>movie1.getSmartScore())) {
            return 2;
        }else return 0;
    }
}
