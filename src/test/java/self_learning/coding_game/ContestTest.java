package self_learning.coding_game;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.ContestStatus;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.InvalidContestException;

@DisplayName("ContestTest")
public class ContestTest {
    @Test
    @DisplayName("#1 Contest should throw InvalidContestException if any Question Level in the List is not equal to Contest Level")
    public void contest_ShouldThrowInvalidContestException_GivenInvalidQuestionList() {
        // Arrange
        String name = "PhonePe TechScholars Assessment #1";
        List<Question> questions = new ArrayList<Question>() {
            {
                add(new Question("Question1", Level.EASY, 10));
                add(new Question("Question2", Level.MEDIUM, 20));
                add(new Question("Question3", Level.HARD, 30));
            }
        };
        Level level = Level.HARD;
        User creator = new User("Fazil", 0);
        ContestStatus contestStatus = ContestStatus.IN_PROGRESS;

        // Act and Assert
        Assertions.assertThrows(InvalidContestException.class,
                () -> new Contest(name, questions, level, creator, contestStatus));
    }
}
