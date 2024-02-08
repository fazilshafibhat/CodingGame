package self_learning.coding_game.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class User extends BaseEntity {
    private final String name;
    private final Integer score;
    private List<Contest> contests;

    public User(String name, Integer score) {
        this.name = name;
        this.score = score;
        this.contests = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Integer getScore() {
        return score;
    }

    public List<Contest> getContests() {
        return contests.stream().collect(Collectors.toList());
    }

    public void addContest(Contest contest) {
        contests.add(contest);
    }
    public void removeContest(Contest contest) {
        contests.removeIf(c -> c.getId() == contest.getId());
    }


    @Override
    public String getId() {
        // TODO Auto-generated method stub
        return super.getId();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", contests=" + contests + ", name=" + name + ", score=" + score + "]";
    }
}
