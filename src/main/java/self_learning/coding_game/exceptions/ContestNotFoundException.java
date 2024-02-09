package self_learning.coding_game.exceptions;

public class ContestNotFoundException extends RuntimeException{
    public ContestNotFoundException(){
        super();
    }
    public ContestNotFoundException(String msg){
    super(msg);
    }
}
