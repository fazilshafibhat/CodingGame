package self_learning.coding_game.entities;

public enum ScoreWeight {
    EASY(50),
    MEDIUM(30),
    HARD(0);

    private int weight;

    ScoreWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
