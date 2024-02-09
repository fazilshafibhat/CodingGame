package self_learning.coding_game.services;

import java.util.List;

import self_learning.coding_game.dtos.UserRegistrationDto;
import self_learning.coding_game.entities.ScoreOrder;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidOperationException;
import self_learning.coding_game.exceptions.UserNotFoundException;

public interface IUserService {
    public User create(String name);

    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder);

    public UserRegistrationDto attendContest(String contestId, String userName)
            throws ContestNotFoundException, UserNotFoundException, InvalidOperationException;

    public UserRegistrationDto withdrawContest(String contestId, String userName)
            throws ContestNotFoundException, UserNotFoundException, InvalidOperationException;
}
