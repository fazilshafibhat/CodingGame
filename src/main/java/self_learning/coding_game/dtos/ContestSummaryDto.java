package self_learning.coding_game.dtos;

import java.util.List;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.User;

public class ContestSummaryDto {
    private final Contest contest;
    private final List<User> users;

    public ContestSummaryDto(Contest contest, List<User> users) {
        this.contest = contest;
        this.users = users;
    }

    public Contest getContest() {
        return contest;
    }

    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "ContestSummaryDto [contest=" + contest + ", users=" + users + "]";
    }

}
