package self_learning.coding_game.commands;

import java.util.List;

import self_learning.coding_game.entities.User;
import self_learning.coding_game.services.IUserService;

public class CreateUserCommand implements ICommand {

    private final IUserService userService;

    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        User createdUser = userService.create(tokens.get(1));
        System.out.println(createdUser);
    }

}
