package self_learning.coding_game.commands;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.ContestStatus;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.QuestionNotFoundException;
import self_learning.coding_game.exceptions.UserNotFoundException;
import self_learning.coding_game.services.IContestService;

@DisplayName("CreateContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class CreateContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IContestService contestServiceMock;

    @InjectMocks
    CreateContestCommand createContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print newly Created Contest To Console Given NumQuestions")
    public void execute_ShouldPrintContest_GivenNumQuestions() {
        // Arrange
        String expectedOutput = "Contest [id=4, name=contest4, level=EASY, creator=user4, contestStatus=NOT_STARTED, questions=[Question [id=4, level=EASY, score=10, title=title4], Question [id=5, level=EASY, score=10, title=title5]]]";
        final List<Question> questionEasy = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest contest = new Contest("4", "contest4", questionEasy, Level.EASY, user, ContestStatus.NOT_STARTED);
        when(contestServiceMock.create(anyString(), any(Level.class), anyString(), anyInt())).thenReturn(contest);

        // Act
        createContestCommand.execute(List.of("CREATE-CONTEST", "name", "EASY", "creator", "2"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).create(anyString(), any(Level.class), anyString(), anyInt());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print newly Created Contest To Console")
    public void execute_ShouldPrintContest() {
        // Arrange
        String expectedOutput = "Contest [id=4, name=contest4, level=EASY, creator=user4, contestStatus=NOT_STARTED, questions=[Question [id=4, level=EASY, score=10, title=title4], Question [id=5, level=EASY, score=10, title=title5]]]";
        final List<Question> questionEasy = new ArrayList<Question>() {
            {
                add(new Question("4", "title4", Level.EASY, 10));
                add(new Question("5", "title5", Level.EASY, 10));
            }
        };
        final User user = new User("4", "user4", 0);
        Contest contest = new Contest("4", "contest4", questionEasy, Level.EASY, user, ContestStatus.NOT_STARTED);
        when(contestServiceMock.create(anyString(), any(Level.class), anyString(), isNull())).thenReturn(contest);

        // Act
        createContestCommand.execute(List.of("CREATE-CONTEST", "name", "EASY", "creator"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).create(anyString(), any(Level.class), anyString(), isNull());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print Error Message To Console If ContestCreator Not Found")
    public void execute_ShouldPrintErrorMessage_GivenContestCreatorNotFound() {
        // Arrange
        String expectedOutput = "Contest Creator for given name: creator not found!";
        doThrow(new UserNotFoundException(expectedOutput)).when(contestServiceMock).create(anyString(),
                any(Level.class), anyString(), isNull());

        // Act
        createContestCommand.execute(List.of("CREATE-CONTEST", "name", "EASY", "creator"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).create(anyString(), any(Level.class), anyString(), isNull());
    }

    @Test
    @DisplayName("execute method of CreateContestCommand Should Print Error Message To Console If Not Enough Questions Found")
    public void execute_ShouldPrintErrorMessage_GivenContestCreatorNotEnoughQuestionsFound() {
        // Arrange
        String expectedOutput = "Request cannot be processed as enough number of questions can not found. Please try again later!";
        doThrow(new QuestionNotFoundException(expectedOutput)).when(contestServiceMock).create(anyString(),
                any(Level.class), anyString(), isNull());

        // Act
        createContestCommand.execute(List.of("CREATE-CONTEST", "name", "EASY", "creator"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).create(anyString(), any(Level.class), anyString(), isNull());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
