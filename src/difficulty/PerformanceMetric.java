package difficulty;

/**
 * Represents gameplay performance metrics for a short time interval.
 * This is fed into StatsCalculator and then into AdaptiveDifficultyEngine.
 */
public class PerformanceMetric {

    private int kills;
    private int shots;
    private int bypassed;
    private int scoreDelta;

    public PerformanceMetric(int kills, int shots, int bypassed, int scoreDelta) {
        this.kills = kills;
        this.shots = shots;
        this.bypassed = bypassed;
        this.scoreDelta = scoreDelta;
    }

    public int getKills() {
        return kills;
    }

    public int getShots() {
        return shots;
    }

    public int getBypassed() {
        return bypassed;
    }

    public int getScoreDelta() {
        return scoreDelta;
    }

    @Override
    public String toString() {
        return "Kills=" + kills + ", Shots=" + shots +
                ", Bypassed=" + bypassed + ", ScoreDelta=" + scoreDelta;
    }
}
