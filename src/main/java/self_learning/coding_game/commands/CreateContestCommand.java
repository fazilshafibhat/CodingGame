package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.exceptions.QuestionNotFoundException;
import self_learning.coding_game.exceptions.UserNotFoundException;
import self_learning.coding_game.services.IContestService;

public class CreateContestCommand implements ICommand {

    private final IContestService contestService;

    public CreateContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String contestName = tokens.get(1);
            String level = tokens.get(2);
            String contestCreator = tokens.get(3);
            Integer numQuestion;

            // String numQuestion = (tokens.size() > 4) ? tokens.get(4) : "0";

            if (tokens.size() < 5)
                numQuestion = null;
            else
                numQuestion = Integer.parseInt(tokens.get(4));

            Contest contest = contestService.create(contestName, Level.valueOf(level),
                    contestCreator, numQuestion);
            System.out.print(contest);
        } catch (UserNotFoundException e) {
            System.out.print(e.getMessage());
        } catch (QuestionNotFoundException e) {
            System.out.print(e.getMessage());
        }
    }

}
