package self_learning.coding_game.repositories;

import java.util.List;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.Level;

public interface IContestRepository extends CRUDRepository<Contest, String> {
    public List<Contest> findAllContestLevelWise(Level level);
}
