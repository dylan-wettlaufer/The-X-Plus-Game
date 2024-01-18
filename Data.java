/* This class represents data that is stored in the dictionary */
public class Data {

    private String config; /* string of the data */

    private int score; /* score of the data */

    /* Constructor: creates data with a score and a configuration */
    public Data (String config, int score) {
        this.config = config;
        this.score = score;
    }
    /* Gets the configuration */
    public String getConfiguration() {
        return config;
    }
    /* Gets the score */
    public int getScore() {
        return score;
    }


}
