package self_learning.coding_game.commands;

import java.util.List;

public interface ICommand {
    void execute(List<String> tokens);
}
