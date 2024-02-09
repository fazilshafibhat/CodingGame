package self_learning.coding_game.entities;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserTest")
public class UserTest {
    @Test
    @DisplayName("checkIfContestExists should Return true If Contest is Found")
    public void checkIfContestExists_ShouldReturnTrue_GivenContest() {
        // Arrange
        String id = "1";
        String name = "PhonePe TechScholars Assessment #1";
        List<Question> questions = new ArrayList<Question>() {
            {
                add(new Question("1", "Question1", Level.EASY, 10));
                add(new Question("1", "Question2", Level.EASY, 20));
                add(new Question("1", "Question3", Level.EASY, 30));
            }
        };
        Level level = Level.EASY;
        User creator = new User("1", "Fazil", 0);
        ContestStatus contestStatus = ContestStatus.IN_PROGRESS;
        Contest contest = new Contest(id, name, questions, level, creator, contestStatus);
        User user = new User("2", name, 0, new ArrayList<Contest>() {
            {
                add(contest);
            }
        });

        // Act
        Assertions.assertTrue(user.checkIfContestExists(contest));
    }

    @Test
    @DisplayName("checkIfContestExists should Return False If No Contest is Found")
    public void checkIfContestExists_ShouldReturnFalse_GivenContest() {
        // Arrange
        String id = "1";
        String name = "PhonePe TechScholars Assessment #1";
        List<Question> questions = new ArrayList<Question>() {
            {
                add(new Question("1", "Question1", Level.EASY, 10));
                add(new Question("1", "Question2", Level.EASY, 20));
                add(new Question("1", "Question3", Level.EASY, 30));
            }
        };
        Level level = Level.EASY;
        User creator = new User("1", "Fazil", 0);
        ContestStatus contestStatus = ContestStatus.IN_PROGRESS;
        Contest contest = new Contest(id, name, questions, level, creator, contestStatus);
        User user = new User("2", name, 0);

        // Act
        Assertions.assertFalse(user.checkIfContestExists(contest));
    }

}
