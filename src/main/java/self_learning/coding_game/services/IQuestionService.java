package self_learning.coding_game.services;

import java.util.List;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;

public interface IQuestionService {
    public List<Question> getAllQuestionLevelWise(Level level);

    public Question create(String title, Level level, Integer difficultyScore);
}
