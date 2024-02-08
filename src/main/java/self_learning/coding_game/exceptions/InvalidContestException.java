package self_learning.coding_game.exceptions;

public class InvalidContestException extends RuntimeException{
    public InvalidContestException(){
        super();
    }
    public InvalidContestException(String msg){
        super(msg);
    }
}
