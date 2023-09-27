package job.transaction;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ScoreStorage implements Serializable {
    private final Map<String, Float> transactionScores = new HashMap<>();

    public float get(final String transactionId) {
        return transactionScores.getOrDefault(transactionId, 0f);
    }

    public void add(final String transactionId, final float score) {
        transactionScores.put(transactionId, get(transactionId) + score);
        System.out.println("Transaction score change: " + transactionId + " ==> + " + score + " ==> " + transactionScores.get(transactionId));
    }
}
