package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.services.IContestService;

public class ListContestCommand implements ICommand {

    private final IContestService contestService;

    public ListContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        Level level;
        if (tokens.size() < 2) {
            level = null;
        } else {
            level = Level.valueOf(tokens.get(1));
        }
        List<Contest> contests = contestService.getAllContestLevelWise(level);
        System.out.println(contests);
    }
}
