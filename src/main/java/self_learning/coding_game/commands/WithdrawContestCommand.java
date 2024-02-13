package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.dtos.UserRegistrationDto;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidOperationException;
import self_learning.coding_game.exceptions.UserNotFoundException;
import self_learning.coding_game.services.IUserService;

public class WithdrawContestCommand implements ICommand {

    private final IUserService userService;

    public WithdrawContestCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        try {
            String contestId = tokens.get(1);
            String userName = tokens.get(2);
            UserRegistrationDto user = userService.withdrawContest(contestId, userName);
            System.out.print(user);
        } catch (ContestNotFoundException e) {
            System.out.print(e.getMessage());
        } catch (UserNotFoundException e) {
            System.out.print(e.getMessage());
        } catch (InvalidOperationException e) {
            System.out.print(e.getMessage());
        }
    }

}
