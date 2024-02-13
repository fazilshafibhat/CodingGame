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
import self_learning.coding_game.services.IContestService;

@DisplayName("ListContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class ListContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IContestService contestServiceMock;

    @InjectMocks
    ListContestCommand listContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of ListContestCommand Should List of Contests for Given Level To Console")
    public void execute_ShouldPrintContestList_GivenLevel() {
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
        List<Contest> contestList = new ArrayList<Contest>() {
            {
                add(contest1);
                add(contest2);
            }
        };
        String expectedOutput = "[Contest [id=1, name=contestName1, level=HARD, creator=creator1, contestStatus=NOT_STARTED, questions=[Question [id=1, level=HARD, score=100, title=title1], Question [id=2, level=HARD, score=90, title=title2], Question [id=3, level=HARD, score=80, title=title3]]], Contest [id=1, name=contestName2, level=HARD, creator=creator2, contestStatus=NOT_STARTED, questions=[Question [id=4, level=HARD, score=1100, title=title4], Question [id=5, level=HARD, score=900, title=title5], Question [id=6, level=HARD, score=800, title=title6]]]]";

        when(contestServiceMock.getAllContestLevelWise(any(Level.class))).thenReturn(contestList);

        // Act
        listContestCommand.execute(List.of("LIST-CONTEST", "MEDIUM"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).getAllContestLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("execute method of ListContestCommand Should List of Contests To Console")
    public void execute_ShouldPrintContestList() {
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
                add(new Question("4", "title4", Level.EASY, 1100));
                add(new Question("5", "title5", Level.EASY, 900));
                add(new Question("6", "title6", Level.EASY, 800));
            }
        };
        User contestCreator2 = new User("2", "creator2", 0);
        Contest contest2 = new Contest("1", "contestName2", questionList2, Level.EASY, contestCreator2,
                ContestStatus.NOT_STARTED);
        List<Contest> contestList = new ArrayList<Contest>() {
            {
                add(contest1);
                add(contest2);
            }
        };
        String expectedOutput = "[Contest [id=1, name=contestName1, level=HARD, creator=creator1, contestStatus=NOT_STARTED, questions=[Question [id=1, level=HARD, score=100, title=title1], Question [id=2, level=HARD, score=90, title=title2], Question [id=3, level=HARD, score=80, title=title3]]], Contest [id=1, name=contestName2, level=EASY, creator=creator2, contestStatus=NOT_STARTED, questions=[Question [id=4, level=EASY, score=1100, title=title4], Question [id=5, level=EASY, score=900, title=title5], Question [id=6, level=EASY, score=800, title=title6]]]]";

        when(contestServiceMock.getAllContestLevelWise(isNull())).thenReturn(contestList);

        // Act
        listContestCommand.execute(List.of("LIST-CONTEST"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock, times(1)).getAllContestLevelWise(isNull());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
