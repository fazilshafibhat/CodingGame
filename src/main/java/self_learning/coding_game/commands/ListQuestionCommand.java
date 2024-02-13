package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.services.IQuestionService;

public class ListQuestionCommand implements ICommand {

    private final IQuestionService questionService;

    public ListQuestionCommand(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void execute(List<String> tokens) {
        if (tokens.size() == 1) {
            List<Question> qList = questionService.getAllQuestionLevelWise(null);
            System.out.println(qList);
            return;
        }
        String level = tokens.get(1);
        List<Question> qList = questionService.getAllQuestionLevelWise(Level.valueOf(level));
        System.out.println(qList);
    }

}
