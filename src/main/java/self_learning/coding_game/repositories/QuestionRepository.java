package self_learning.coding_game.repositories;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;


public class QuestionRepository implements IQuestionRepository {

    private final Map<String, Question> questionMap;
    private Integer autoIncrement = 0;

    public QuestionRepository() {
        questionMap = new HashMap<String, Question>();
    }

    public QuestionRepository(Map<String, Question> questionMap) {
        this.questionMap = questionMap;
        this.autoIncrement = questionMap.size();
    }

    @Override
    public Question save(Question entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            Question q = new Question(Integer.toString(autoIncrement), entity.getTitle(),
                    entity.getLevel(), entity.getScore());
            questionMap.put(q.getId(), q);
            return q;
        }
        questionMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Question> findAll() {
        return new ArrayList<>(questionMap.values());
    }

    @Override
    public Optional<Question> findById(String id) {
        return Optional.ofNullable(questionMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public void delete(Question entity) {

    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Question> findAllQuestionsLevelWise(Level level) {
        return questionMap.values().stream().filter(question -> question.getLevel() == level)
                .collect(Collectors.toList());
    }

}
