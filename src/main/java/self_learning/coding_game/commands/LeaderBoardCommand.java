package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.ScoreOrder;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.services.IUserService;

public class LeaderBoardCommand implements ICommand {

    private final IUserService userService;

    public LeaderBoardCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        String scoreOrder = tokens.get(1);
        List<User> userLeaderBoard = userService.getAllUserScoreOrderWise(ScoreOrder.valueOf(scoreOrder));
        System.out.print(userLeaderBoard);
    }

}
