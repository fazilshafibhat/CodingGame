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

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.services.IQuestionService;

@DisplayName("ListQuestionCommandTest")
@ExtendWith(MockitoExtension.class)
public class ListQuestionCommandTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @Mock
    IQuestionService questionServiceMock;

    @InjectMocks
    ListQuestionCommand listQuestionCommand;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @DisplayName("execute method of ListQuestionCommand Should List of Question for Given Level To Console")
    public void execute_ShouldPrintQuestionList_GivenLevel() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 200));
                add(new Question("3", "title3", Level.EASY, 300));
            }
        };
        String expectedOutput = "[Question [id=1, level=EASY, score=100, title=title1], Question [id=2, level=EASY, score=200, title=title2], Question [id=3, level=EASY, score=300, title=title3]]";

        when(questionServiceMock.getAllQuestionLevelWise(any(Level.class))).thenReturn(questionList);

        // Act
        listQuestionCommand.execute(List.of("LIST-QUESTION", "MEDIUM"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(questionServiceMock, times(1)).getAllQuestionLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("execute method of ListQuestionCommand Should List of All Question To Console")
    public void execute_ShouldPrintQuestionList() {
        // Arrange
        List<Question> questionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.MEDIUM, 200));
                add(new Question("3", "title3", Level.HARD, 300));
            }
        };
        String expectedOutput = "[Question [id=1, level=EASYEASYEASY, score=100, title=title1], Question [id=2, level=MEDIUM, score=200, title=title2], Question [id=3, level=HIGH, score=300, title=title3]]";

        when(questionServiceMock.getAllQuestionLevelWise(isNull())).thenReturn(questionList);

        // Act
        listQuestionCommand.execute(List.of("LIST-QUESTION"));

        // Assert
        Assertions.assertEquals(expectedOutput, outputStreamCaptor.toString().trim());

        verify(questionServiceMock, times(1)).getAllQuestionLevelWise(isNull());
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

}
