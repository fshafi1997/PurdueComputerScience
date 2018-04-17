/**
 * Created by farhanshafi on 9/28/16.
 */
import javax.swing.*;
import java.lang.*;
import java.text.NumberFormat;


public class Team {
    private String name;
    private String location;
    private int nWins;
    private int nLosses;
    private double offense;
    private double defense;
    private double luck;
    Luck luck1 = new Luck();
    public NumberFormat nf = NumberFormat.getNumberInstance();

    //accessors
    public String getName(){
        return name;
    }
    public String getLocation(){
        return location;
    }
    public int getN_Wins(){
        return nWins;
    }
    public int getN_Losses(){
        return nLosses;
    }
    public double getOffense(){
        return offense;
    }
    public double getDefense(){
        return defense;
    }



    //constructor
    public Team (String name, String location){
        this.name = name;
        this.location = location;
        this.nWins = 0;
        this.nLosses = 0;
        this.offense = luck1.luck();
        this.defense = luck1.luck();
    }


    public static Team play(Team team1, boolean isHome, Team team2) {
        Team homeTeam;
        Team awayTeam;
        if (isHome) {
            homeTeam = team1;
            awayTeam = team2;

        } else {
            homeTeam = team2;
            awayTeam = team1;
        }
        double homeScore = (homeTeam.getOffense() + homeTeam.getDefense() + 0.2) * Luck.luck();
        double awayScore = (awayTeam.getOffense() + awayTeam.getDefense()) * Luck.luck();

        if ((homeScore > awayScore) && homeTeam == team1) {
            team1.nWins++;
            team2.nLosses++;
            return team1;
        }
        if ((homeScore > awayScore) && homeTeam == team2) {
            team1.nLosses++;
            team2.nWins++;
            return team2;
        }
        if ((homeScore < awayScore) && awayTeam == team1) {
            team1.nWins++;
            team2.nLosses++;
            return team1;
        }
        if ((homeScore < awayScore) && awayTeam == team2) {
            team1.nLosses++;
            team2.nWins++;
            return team2;
        }
        return team1;
    }

    //Other methods
    public int calcTotalGames(){
        int Totalgames = nWins+nLosses;
        return Totalgames;
    }
    public double calcWinRate(){
        double winningRate = (double)nWins/((double)nWins+(double)nLosses);
        double wR = (winningRate * 100);
        return wR;
    }
    public double calcLossRate(){
        double losingRate = (double)nLosses/((double)nWins+(double)nLosses);
        double lR = (losingRate * 100);
       //return Math.round(((losingRate*1000)/100));
        return lR;
    }
    public int calcDifference(){
        int difference = Math.abs(nWins-nLosses);
        return difference;
    }
    public String generateStats(){
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);

        String stat1 = (name + " " + "(" + location + ")" + "\n" + "Total games: " + calcTotalGames() + "\n" + "No. wins" +
                ": " + nWins + "(" + nf.format(calcWinRate()) + "%)" + "\n" + "No. losses: " + nLosses + "(" + nf.format(calcLossRate()) + "%)" +
                "\n" + "Difference: " +  calcDifference());
        return stat1;
    }

    public static void main(String[] args){
        String nameandlocation = JOptionPane.showInputDialog(null, "Enter name and location for home team seperated " +
                "by a comma(,):");
        int index1 = nameandlocation.indexOf(",");
        String name1 = nameandlocation.substring(0,index1);
        String name11 = name1.toLowerCase();
        int length1 = nameandlocation.length();
        String location1 = nameandlocation.substring(index1+1,length1);
        String location11 = location1.toLowerCase();
        Team team1 = new Team(name11, location11);
        String nameandlocation2 = JOptionPane.showInputDialog(null, "Enter name and location for away team seperated " +
                "by a comma(,):");
        int index2 = nameandlocation2.indexOf(",");
        String name2 = nameandlocation2.substring(0,index2);
        String name22 = name2.toLowerCase();
        int length2 = nameandlocation2.length();
        String location2 = nameandlocation2.substring(index2+1,length2);
        String location22 = location2.toLowerCase();
        Team team2 = new Team(name22, location22);
        //double team1Offense = Math.round(100*(team1.getOffense()))/100;
        //double team1Deffence = Math.round(100*(team1.getDefense()))/100;
        //double team2Offense = Math.round(100*(team2.getOffense()))/100;
        //double team2Deffense = Math.round(100*(team2.getDefense()))/100;
        JOptionPane.showMessageDialog(null, "Home team is: " + name11 + " from " + location11 + " rated " + team1.getOffense()
                + " (" + "offense" + ") " + team1.getDefense() + " (" + "defence" + ")");
        JOptionPane.showMessageDialog(null, "Away team is: " + name22 + " from " + location22 + " rated " + team2.getOffense()
                + " (" + "offense" + ") " + team2.getDefense() + " (" + "defence" + ")");
        Team winner = team1.play(team1, true, team2);
        JOptionPane.showMessageDialog(null, "Round 1" + "\n" + "Winner is: " + winner.getName() + " from " + winner.getLocation() +
                " rated " + winner.getOffense() + " (" + "offense" + ") " + winner.getDefense() + " (" + "defense" + ")");
        winner = team1.play(team1, true, team2);
        JOptionPane.showMessageDialog(null, "Round 2" + "\n" + "Winner is: " + winner.getName() + " from " + winner.getLocation() +
                " rated " + winner.getOffense() + " (" + "offense" + ") " + winner.getDefense() + " (" + "defense" + ")");
        winner = team1.play(team1, true, team2);
        JOptionPane.showMessageDialog(null, "Round 3" + "\n" + "Winner is: " + winner.getName() + " from " + winner.getLocation() +
                " rated " + winner.getOffense() + " (" + "offense" + ") " + winner.getDefense() + " (" + "defense" + ") ");
        JOptionPane.showMessageDialog(null,team1.generateStats());
        JOptionPane.showMessageDialog(null,team2.generateStats());


    }
}
