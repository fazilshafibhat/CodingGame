package self_learning.coding_game.exceptions;

public class QuestionNotFoundException extends RuntimeException{
    public QuestionNotFoundException(){
        super();
    }
    public QuestionNotFoundException(String msg){
        super(msg);
    }
}
