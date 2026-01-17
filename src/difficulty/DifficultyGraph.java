package difficulty;

import java.util.HashMap;
import java.util.Map;

/**
 * Directed graph of allowed difficulty transitions.
 */
public class DifficultyGraph {

    private Map<String, String[]> edges = new HashMap<>();

    public DifficultyGraph() {
        edges.put("EASY", new String[]{"MEDIUM"});
        edges.put("MEDIUM", new String[]{"EASY", "HARD"});
        edges.put("HARD", new String[]{"MEDIUM"});
    }

    public String[] getNext(String difficulty) {
        return edges.getOrDefault(difficulty, new String[]{"EASY"});
    }
}
