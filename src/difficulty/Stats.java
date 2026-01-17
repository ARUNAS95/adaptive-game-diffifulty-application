package difficulty;

/**
 * A simple data container capturing summarized performance metrics
 * for recent gameplay intervals.
 */
public class Stats {
    public int kills;
    public int shots;
    public int bypassed;
    public double accuracy;
    public int scoreDelta;

    public Stats(int kills, int shots, int bypassed, double accuracy, int scoreDelta) {
        this.kills = kills;
        this.shots = shots;
        this.bypassed = bypassed;
        this.accuracy = accuracy;
        this.scoreDelta = scoreDelta;
    }
}
