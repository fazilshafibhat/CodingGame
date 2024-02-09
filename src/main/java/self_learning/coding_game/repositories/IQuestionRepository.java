package self_learning.coding_game.repositories;

import java.util.List;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;

public interface IQuestionRepository extends CRUDRepository<Question, String> {
   public List<Question> findAllQuestionsLevelWise(Level level);
}
