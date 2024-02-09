package self_learning.coding_game.services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import self_learning.coding_game.dtos.UserRegistrationDto;
import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.ContestStatus;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidContestException;
import self_learning.coding_game.exceptions.QuestionNotFoundException;
import self_learning.coding_game.exceptions.UserNotFoundException;
import self_learning.coding_game.repositories.IContestRepository;
import self_learning.coding_game.repositories.IQuestionRepository;
import self_learning.coding_game.repositories.IUserRepository;

@DisplayName("ContestServiceTest")
@ExtendWith(MockitoExtension.class)
public class ContestServiceTest {
    @Mock
    private IUserRepository userRepositoryMock;

    @Mock
    private IContestRepository contestRepositoryMock;

    @Mock
    private IQuestionRepository questionRepositoryMock;

    @Mock
    private IUserService userServiceMock;

    @InjectMocks
    private ContestService contestService;

    @Test
    @DisplayName("create method Should Throw UserNotFoundException If No Creator User Found")
    public void create_ShouldThrowUserNotFoundException() {
        // Arrange
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.empty());

        // Act and Assert
        Assertions.assertThrows(UserNotFoundException.class,
                () -> contestService.create("contest4", Level.EASY, "contestCreator", 4));
        verify(userRepositoryMock, times(1)).findByName(anyString());
    }

    @Test
    @DisplayName("create method Should Throw QuestionNotFoundException If No Questions Found in Repository Given Level")
    public void create_ShouldThrowQuestionNotFoundException() {
        // Arrange
        final User user = new User("4", "user4", 0);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        when(questionRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(Collections.emptyList());

        // Act and Assert
        Assertions.assertThrows(QuestionNotFoundException.class,
                () -> contestService.create("contest4", Level.EASY, "contestCreator", 4));
        verify(userRepositoryMock, times(1)).findByName(anyString());
        verify(questionRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("create method Should Create Contest with All Questions Given NumQuestions is Empty")
    public void create_ShouldCreateContest_GivenNumQuestionsEmpty() {
        // Arrange
        final List<Question> questionLow = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest expectedContest = new Contest("4", "contest4", questionLow, Level.EASY, user,
                ContestStatus.NOT_STARTED);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        when(questionRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(questionLow);
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(expectedContest);
        when(userServiceMock.attendContest(anyString(), anyString())).thenReturn(any(UserRegistrationDto.class));

        // Act
        Contest actualContest = contestService.create("contest4", Level.EASY, "contestCreator", null);

        // Assert
        Assertions.assertEquals(expectedContest, actualContest);
        verify(userRepositoryMock, times(1)).findByName(anyString());
        verify(questionRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
        verify(contestRepositoryMock, times(1)).save(any(Contest.class));
        verify(userServiceMock, times(1)).attendContest(anyString(), anyString());
    }

    @Test
    @DisplayName("create method Should Create Contest with All Questions Given NumQuestions is Zero")
    public void create_ShouldCreateContest_GivenNumQuestionsZero() {
        // Arrange
        final List<Question> questionLow = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest expectedContest = new Contest("4", "contest4", questionLow, Level.EASY, user, ContestStatus.NOT_STARTED);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        when(questionRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(questionLow);
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(expectedContest);
        when(userServiceMock.attendContest(anyString(), anyString())).thenReturn(any(UserRegistrationDto.class));

        // Act
        Contest actualContest = contestService.create("contest4", Level.EASY, "contestCreator", 0);

        // Assert
        Assertions.assertEquals(expectedContest, actualContest);
        verify(userRepositoryMock, times(1)).findByName(anyString());
        verify(questionRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
        verify(contestRepositoryMock, times(1)).save(any(Contest.class));
        verify(userServiceMock, times(1)).attendContest(anyString(), anyString());
    }

    @Test
    @DisplayName("create method Should Create Contest with All Questions Given NumQuestions is More than available questions in the repository")
    public void create_ShouldCreateContest_GivenNumQuestionsMoreThanAvailableQuestions() {
        // Arrange
        final List<Question> questionLow = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest expectedContest = new Contest("4", "contest4", questionLow, Level.EASY, user, ContestStatus.NOT_STARTED);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        when(questionRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(questionLow);
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(expectedContest);
        when(userServiceMock.attendContest(anyString(), anyString())).thenReturn(any(UserRegistrationDto.class));

        // Act
        Contest actualContest = contestService.create("contest4", Level.EASY, "contestCreator", 3);

        // Assert
        Assertions.assertEquals(expectedContest, actualContest);
        verify(userRepositoryMock, times(1)).findByName(anyString());
        verify(questionRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
        verify(contestRepositoryMock, times(1)).save(any(Contest.class));
        verify(userServiceMock, times(1)).attendContest(anyString(), anyString());
    }

    @Test
    @DisplayName("create method Should Create Contest with Questions Given NumQuestions")
    public void create_ShouldCreateContest_GivenNumQuestions() {
        // Arrange
        final List<Question> questionLow = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest expectedContest = new Contest("4", "contest4", questionLow, Level.EASY, user,
                ContestStatus.NOT_STARTED);
        when(userRepositoryMock.findByName(anyString())).thenReturn(Optional.of(user));
        when(questionRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(questionLow);
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(expectedContest);
        when(userServiceMock.attendContest(anyString(), anyString())).thenReturn(any(UserRegistrationDto.class));

        // Act
        Contest actualContest = contestService.create("contest4", Level.EASY, "contestCreator", 1);

        // Assert
        Assertions.assertEquals(expectedContest.getQuestions().size(), actualContest.getQuestions().size());
        verify(userRepositoryMock, times(1)).findByName(anyString());
        verify(questionRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
        verify(contestRepositoryMock, times(1)).save(any(Contest.class));
        verify(userServiceMock, times(1)).attendContest(anyString(), anyString());
    }

    @Test
    @DisplayName("getAllContestLevelWise method should return List of Contest if input is null")
    public void getAllContestLevelWise_ShouldReturnAllContestList() {
        // Arrange
        List<Question> questionList1 = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        User contestCreator1 = new User("1", "creator1", 0);
        Contest contest1 = new Contest("1", "contestName1", questionList1, Level.EASY, contestCreator1,
                ContestStatus.NOT_STARTED);
        List<Question> questionList2 = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.HARD, 1100));
                add(new Question("5", "title5", Level.HARD, 900));
                add(new Question("6", "title6", Level.HARD, 800));
            }
        };
        User contestCreator2 = new User("2", "creator2", 0);
        Contest contest2 = new Contest("1", "contestName2", questionList2, Level.HARD, contestCreator2,
                ContestStatus.NOT_STARTED);
        List<Contest> expectedContestList = new ArrayList<Contest>() {
            {
                add(contest1);
                add(contest2);
            }
        };
        when(contestRepositoryMock.findAll()).thenReturn(expectedContestList);

        // Act
        List<Contest> actualContestList = contestService.getAllContestLevelWise(null);

        // Assert
        Assertions.assertTrue(
                expectedContestList.size() == actualContestList.size() &&
                        expectedContestList.containsAll(actualContestList) &&
                        actualContestList.containsAll(expectedContestList));
        verify(contestRepositoryMock, times(1)).findAll();
        verify(contestRepositoryMock, times(0)).findAllContestLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("getAllContestLevelWise method should return List of Contest if level is given")
    public void getAllContestLevelWise_ShouldReturnAllContestList_GivenLevel() {
        // Arrange
        List<Question> questionList1 = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.HARD, 100));
                add(new Question("2", "title2", Level.HARD, 90));
                add(new Question("3", "title3", Level.HARD, 80));
            }
        };
        User contestCreator1 = new User("1", "creator1", 0);
        Contest contest1 = new Contest("1", "contestName1", questionList1, Level.HARD, contestCreator1,
                ContestStatus.NOT_STARTED);
        List<Question> questionList2 = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.HARD, 1100));
                add(new Question("5", "title5", Level.HARD, 900));
                add(new Question("6", "title6", Level.HARD, 800));
            }
        };
        User contestCreator2 = new User("2", "creator2", 0);
        Contest contest2 = new Contest("1", "contestName2", questionList2, Level.HARD, contestCreator2,
                ContestStatus.NOT_STARTED);
        List<Contest> expectedContestList = new ArrayList<Contest>() {
            {
                add(contest1);
                add(contest2);
            }
        };
        when(contestRepositoryMock.findAllContestLevelWise(any(Level.class))).thenReturn(expectedContestList);

        // Act
        List<Contest> actualContestList = contestService.getAllContestLevelWise(Level.HARD);

        // Assert
        Assertions.assertTrue(
                expectedContestList.size() == actualContestList.size() &&
                        expectedContestList.containsAll(actualContestList) &&
                        actualContestList.containsAll(expectedContestList));
        verify(contestRepositoryMock, times(1)).findAllContestLevelWise(any(Level.class));
        verify(contestRepositoryMock, times(0)).findAll();

    }

    @Test
    @DisplayName("runContest method Should Throw NoContestFoundException If No Contest Found")
    public void runContest_ShouldThrowNoContestFoundException() {
        // Arrange
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.empty());
        // Act and Assert
        Assertions.assertThrows(ContestNotFoundException.class,
                () -> contestService.runContest("1", "contestCreator1"));
        verify(contestRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method Should Throw InvalidContestException If Contest is in Progress")
    public void runContest_ShouldThrowInvalidOperationException_GivenContestInProgress() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        User contestCreator = new User("1", "contestCreator1", 0);
        Contest contest = new Contest("1", "contestName", questionList, Level.EASY, contestCreator,
                ContestStatus.IN_PROGRESS);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidContestException.class, () -> contestService.runContest("1", "contestCreator1"));
        verify(contestRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method Should Throw InvalidContestException If Contest has ended")
    public void runContest_ShouldThrowInvalidOperationException_GivenContestEnded() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        User contestCreator = new User("1", "contestCreator1", 0);
        Contest contest = new Contest("1", "contestName", questionList, Level.EASY, contestCreator,
                ContestStatus.ENDED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidContestException.class, () -> contestService.runContest("1", "contestCreator1"));
        verify(contestRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method Should Throw InvalidContestException If Not ran by Contest Creator")
    public void runContest_ShouldThrowInvalidOperationException_GivenWrongContestCreator() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        User contestCreator = new User("1", "contestCreator1", 0);
        Contest contest = new Contest("1", "contestName", questionList, Level.EASY, contestCreator,
                ContestStatus.NOT_STARTED);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        // Act and Assert
        Assertions.assertThrows(InvalidContestException.class, () -> contestService.runContest("1", "contestCreator2"));
        verify(contestRepositoryMock, times(1)).findById(anyString());
    }

    @Test
    @DisplayName("runContest method")
    public void testRunContest() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        User contestCreator = new User("1", "contestCreator1", 0);
        User user1 = new User("2", "user1", 0);
        User user2 = new User("3", "user2", 0);
        Contest contest = new Contest("1", "contestName", questionList, Level.EASY, contestCreator,
                ContestStatus.NOT_STARTED);
        Contest contestEnded = new Contest("1", "contestName", questionList, Level.EASY, contestCreator,
                ContestStatus.ENDED);
        contestCreator.addContest(contest);
        user1.addContest(contest);
        when(contestRepositoryMock.findById(anyString())).thenReturn(Optional.of(contest));
        when(userRepositoryMock.findAll()).thenReturn(List.of(contestCreator, user1, user2));
        when(contestRepositoryMock.save(any(Contest.class))).thenReturn(contestEnded);

        // Act

        contestService.runContest("1", "contestCreator1");

        // Assert
        verify(contestRepositoryMock, times(1)).findById(anyString());
        verify(userRepositoryMock, times(1)).findAll();
        verify(contestRepositoryMock, times(1)).save(any(Contest.class));
        verify(userRepositoryMock, times(2)).save(any(User.class));
    }
}
