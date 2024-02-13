package self_learning.coding_game.commands;

import java.util.List;
import java.util.stream.Collectors;

import self_learning.coding_game.dtos.ContestSummaryDto;
import self_learning.coding_game.services.IContestService;

public class RunContestCommand implements ICommand {

    private final IContestService contestService;

    public RunContestCommand(IContestService contestService) {
        this.contestService = contestService;
    }

    @Override
    public void execute(List<String> tokens) {
        String contestId = tokens.get(1);
        String contestCreator = tokens.get(2);
        try {
            ContestSummaryDto contestSummaryDto = contestService.runContest(contestId, contestCreator);
            System.out.println(contestSummaryDto.getUsers().stream()
                    .map(u -> "[UserName:" + u.getName() + " [Questions: "
                            + u.getQuestionsByContest(contestSummaryDto.getContest()) + "]" + "]")
                    .collect(Collectors.joining(", ")));
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

}
