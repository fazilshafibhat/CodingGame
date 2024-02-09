package self_learning.coding_game.services;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import self_learning.coding_game.entities.Level;
import self_learning.coding_game.entities.Question;
import self_learning.coding_game.repositories.IQuestionRepository;

@DisplayName("QuestionServiceTest")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private IQuestionRepository qRepositoryMock;

    @InjectMocks
    private QuestionService questionService;

    @Test
    @DisplayName("create method should create Question")
    public void create_ShouldReturnQuestion() {
        // Arrange
        Question expectedQuestion = new Question("1", "title", Level.EASY, 10);
        when(qRepositoryMock.save(any(Question.class))).thenReturn(expectedQuestion);

        // Act
        Question actualQuestion = questionService.create("title", Level.EASY, 10);

        // Assert
        Assertions.assertEquals(expectedQuestion, actualQuestion);
        verify(qRepositoryMock, times(1)).save(any(Question.class));
    }

    @Test
    @DisplayName("getAllQuestionLevelWise method should return List of Question if input is null")
    public void getAllQuestionLevelWise_ShouldReturnAllQuestionList() {
        // Arrange
        List<Question> expectedQuestionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.MEDIUM, 200));
                add(new Question("3", "title3", Level.HARD, 300));
            }
        };
        when(qRepositoryMock.findAll()).thenReturn(expectedQuestionList);

        // Act
        List<Question> actualQuestionList = questionService.getAllQuestionLevelWise(null);

        // Assert
        Assertions.assertTrue(
                expectedQuestionList.size() == actualQuestionList.size() &&
                        expectedQuestionList.containsAll(actualQuestionList) &&
                        actualQuestionList.containsAll(expectedQuestionList));
        verify(qRepositoryMock, times(1)).findAll();
        verify(qRepositoryMock, times(0)).findAllQuestionsLevelWise(any(Level.class));
    }

    @Test
    @DisplayName("getAllQuestionLevelWise method should return List of Question if level is given")
    public void getAllQuestionLevelWise_ShouldReturnAllQuestionList_GivenLevel() {
        // Arrange
        List<Question> expectedQuestionList = new ArrayList<Question>() {
            {
                add(new Question("1", "title1", Level.EASY, 100));
                add(new Question("2", "title2", Level.EASY, 90));
                add(new Question("3", "title3", Level.EASY, 80));
            }
        };
        when(qRepositoryMock.findAllQuestionsLevelWise(any(Level.class))).thenReturn(expectedQuestionList);

        // Act
        List<Question> actualQuestionList = questionService.getAllQuestionLevelWise(Level.EASY);

        // Assert
        Assertions.assertTrue(
                expectedQuestionList.size() == actualQuestionList.size() &&
                        expectedQuestionList.containsAll(actualQuestionList) &&
                        actualQuestionList.containsAll(expectedQuestionList));
        verify(qRepositoryMock, times(1)).findAllQuestionsLevelWise(any(Level.class));
        verify(qRepositoryMock, times(0)).findAll();
    }
}
