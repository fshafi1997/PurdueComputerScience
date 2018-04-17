/**
 * Created by farhanshafi on 10/26/16.
 */
public class Model {
    private String userToken = "";
    private String gameToken = "";
    private int userCounter = 1;

    public String getGameToken() {
        return gameToken;
    }
    public void setGameToken(String gameToken) {
        this.gameToken = gameToken;
    }
    public String getUserToken(){
        return userToken;
    }
    public void setUserToken(String userToken){
        this.userToken = userToken;
    }
    public int getUserCounter(){
        return this.userCounter;
    }
    public void incrementUserCounter(){
        userCounter++;
    }
}
