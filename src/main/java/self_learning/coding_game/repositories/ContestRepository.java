package self_learning.coding_game.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.Level;

public class ContestRepository implements IContestRepository {

    private final Map<String, Contest> contestMap;
    private Integer autoIncrement = 0;

    public ContestRepository() {
        contestMap = new HashMap<String, Contest>();
    }

    public ContestRepository(Map<String, Contest> contestMap) {
        this.contestMap = contestMap;
        this.autoIncrement = contestMap.size();
    }

    @Override
    public Contest save(Contest entity) {
        if (entity.getId() == null) {
            autoIncrement++;
            Contest c = new Contest(Integer.toString(autoIncrement), entity.getName(),
                    entity.getQuestions(), entity.getLevel(), entity.getCreator(),
                    entity.getContestStatus());
            contestMap.put(c.getId(), c);
            return c;
        }
        contestMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public List<Contest> findAll() {
        return contestMap.values().stream().collect(Collectors.toList());

    }

    @Override
    public Optional<Contest> findById(String id) {
        return Optional.ofNullable(contestMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    @Override
    public void delete(Contest entity) {
    }

    @Override
    public void deleteById(String id) {
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public List<Contest> findAllContestLevelWise(Level level) {
        List<Contest> allContestsLevelWise = new ArrayList<>();
        for (Map.Entry<String, Contest> map : contestMap.entrySet()) {
            Contest contest = map.getValue();
            if (contest.getLevel().equals(level)) {
                allContestsLevelWise.add(contest);
            }
        }
        return allContestsLevelWise;
    }

}
