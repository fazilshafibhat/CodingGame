package self_learning.coding_game.services;

import java.util.List;

import self_learning.coding_game.dtos.ContestSummaryDto;
import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidContestException;
import self_learning.coding_game.exceptions.QuestionNotFoundException;
import self_learning.coding_game.exceptions.UserNotFoundException;

public interface IContestService {
    public Contest create(String contestName, Level level, String contestCreator, Integer numQuestion)
            throws UserNotFoundException, QuestionNotFoundException;

    public List<Contest> getAllContestLevelWise(Level level);

    public ContestSummaryDto runContest(String contestId, String contestCreator)
            throws ContestNotFoundException, InvalidContestException;
}
