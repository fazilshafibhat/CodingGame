package self_learning.coding_game.services;

import java.util.ArrayList;
import java.util.List;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.repositories.IQuestionRepository;

public class QuestionService implements IQuestionService {
    private final IQuestionRepository questionRepository;

    public QuestionService(IQuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question create(String title, Level level, Integer difficultyScore) {
        final Question question = new Question(title, level, difficultyScore);
        return questionRepository.save(question);
    }

    @Override
    public List<Question> getAllQuestionLevelWise(Level level) {
        List<Question> expectedQuestionList = new ArrayList<Question>();
        if (level != null) {
            expectedQuestionList.addAll(questionRepository.findAllQuestionsLevelWise(level));
        } else {
            expectedQuestionList.addAll(questionRepository.findAll());
        }
        return expectedQuestionList;
    }

}
