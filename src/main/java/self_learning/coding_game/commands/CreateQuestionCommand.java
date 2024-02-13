package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.services.IQuestionService;

public class CreateQuestionCommand implements ICommand {

    private final IQuestionService questionService;

    public CreateQuestionCommand(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public void execute(List<String> tokens) {
        String title = tokens.get(1);
        String level = tokens.get(2);
        Integer score = Integer.parseInt(tokens.get(3));
        Question question = questionService.create(title, Level.valueOf(level), score);
        System.out.println(question);
    }

}
