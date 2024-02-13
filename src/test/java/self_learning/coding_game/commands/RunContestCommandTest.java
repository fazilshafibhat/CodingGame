package self_learning.coding_game.commands;
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

import self_learning.coding_game.dtos.ContestSummaryDto;
import self_learning.coding_game.entities.Contest;
import self_learning.coding_game.entities.ContestStatus;
import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.entities.User;
import self_learning.coding_game.exceptions.ContestNotFoundException;
import self_learning.coding_game.exceptions.InvalidContestException;
import self_learning.coding_game.services.IContestService;

@DisplayName("RunContestCommandTest")
@ExtendWith(MockitoExtension.class)
public class RunContestCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    
    @Mock
    IContestService contestServiceMock;

    @InjectMocks
    RunContestCommand runContestCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }
    @Test
    @DisplayName("execute method of RunContestCommand Should Print Error Message To Console If Contest Not Found")
    public void execute_ShouldPrintErrorMessage_GivenContestNotFound() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Run Contest. Contest for given id:"+contestId+" not found!";
        doThrow(new ContestNotFoundException(expectedOutput)).when(contestServiceMock).runContest(contestId,"Joey");

        //Act
        runContestCommand.execute(List.of("RUN-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).runContest(contestId,"Joey");
    }


    @Test
    @DisplayName("execute method of RunContestCommand Should Print Error Message To Console If Contest is in progress")
    public void execute_ShouldPrintErrorMessage_GivenContestInProgress() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Run Contest. Contest for given id:"+contestId+" is in progress!";
        doThrow(new InvalidContestException(expectedOutput)).when(contestServiceMock).runContest(contestId,"Joey");

        //Act
        runContestCommand.execute(List.of("RUN-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).runContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of RunContestCommand Should Print Error Message To Console If Contest is ended")
    public void execute_ShouldPrintErrorMessage_GivenContestEnded() {
        //Arrange
        String contestId = "1";
        String expectedOutput = "Cannot Run Contest. Contest for given id:"+contestId+" is ended!";
        doThrow(new InvalidContestException(expectedOutput)).when(contestServiceMock).runContest(contestId,"Joey");

        //Act
        runContestCommand.execute(List.of("RUN-CONTEST",contestId,"Joey"));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).runContest(contestId,"Joey");
    }

    @Test
    @DisplayName("execute method of RunContestCommand Should Print Error Message To Console If User is not the Contest Creator")
    public void execute_ShouldPrintErrorMessage_GivenUserNotContestCreator() {
        //Arrange
        String userName = "Joey";
        String contestId = "1";
        String expectedOutput = "Cannot Run Contest. User:"+ userName + " is not the contest creator of contest id:"+contestId;
        doThrow(new InvalidContestException(expectedOutput)).when(contestServiceMock).runContest(contestId,userName);

        //Act
        runContestCommand.execute(List.of("RUN-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).runContest(contestId,userName);
    }

    @Test
    @DisplayName("execute method of RunContestCommand Should Print Contest Summary")
    public void execute_ShouldPrintContestSummary() {
        //Arrange
        String userName = "user4";
        String contestId = "4";
        Question question1 = new Question("4", "title4", Level.EASY,10);
        Question question2 = new Question("5", "title5", Level.EASY,20);
        Question question3 = new Question("6", "title6", Level.EASY,30);
        final List<Question> questionEasy = new ArrayList<Question>(){
            {
                add(question1);
                add(question2);
                add(question3);
            }
        };
        final User user = new User("4","user4",0);
        Contest contest = new Contest("4","contest4",questionEasy,Level.EASY, user,ContestStatus.ENDED);
        User user1 = new User("1", "name1", 10);
        user1.addContestQuestion(contest,List.of(question1,question2));
        User user2 = new User("2", "name2", 20);
        user2.addContestQuestion(contest,List.of(question1,question3));
        User user3 = new User("3", "name3", 30);
        user3.addContestQuestion(contest,List.of(question2,question3));
        String expectedOutput = "[UserName:name1 [Questions: [Question [id=4, level=EASY, score=10, title=title4], Question [id=5, level=EASY, score=20, title=title5]]]], [UserName:name2 [Questions: [Question [id=4, level=EASY, score=10, title=title4], Question [id=6, level=EASY, score=30, title=title6]]]], [UserName:name3 [Questions: [Question [id=5, level=EASY, score=20, title=title5], Question [id=6, level=EASY, score=30, title=title6]]]]";
        when(contestServiceMock.runContest(contestId, userName)).thenReturn(new ContestSummaryDto(contest,List.of(user1,user2,user3)));

        //Act
        runContestCommand.execute(List.of("RUN-CONTEST",contestId,userName));

        //Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(contestServiceMock,times(1)).runContest(contestId,userName);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }
 
}
