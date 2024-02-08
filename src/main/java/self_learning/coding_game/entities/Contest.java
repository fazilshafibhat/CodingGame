package self_learning.coding_game.entities;

import java.util.List;
import java.util.stream.Collectors;

import self_learning.coding_game.exceptions.InvalidContestException;;

public class Contest extends BaseEntity {
    private final String name;
    private final List<Question> questions;
    private final Level level;
    private final User contestCreator;
    private final ContestStatus contestStatus;

    public Contest(String name, List<Question> questions, Level level, User contestCreator,
            ContestStatus contestStatus) {
        this.name = name;
        // this.questions = new ArrayList<>();
        validateQuestionList(questions, level);
        this.questions=questions;
        this.level = level;
        this.contestCreator = contestCreator;
        this.contestStatus = contestStatus;
    }

    private void validateQuestionList(List<Question> qList, Level contestLevel) {
        if (qList.isEmpty()) {
            throw new InvalidContestException("Question list is empty!");
        }
        for (Question question : qList) {
            if (!question.getLevel().equals(contestLevel)) {
                throw new InvalidContestException(
                        "All questions must have the same difficulty level as contest level.");
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions.stream().collect(Collectors.toList());
    }

    public Level getLevel() {
        return level;
    }

    public User getContestCreator() {
        return contestCreator;
    }

    public ContestStatus getContestStatus() {
        return contestStatus;
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
        Contest other = (Contest) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Contest [id=" + id + ", name=" + name + ", level=" + level + ", creator=" + contestCreator.getName()
                + ", contestStatus=" + contestStatus + ", questions=" + questions + "]";
    }
}
