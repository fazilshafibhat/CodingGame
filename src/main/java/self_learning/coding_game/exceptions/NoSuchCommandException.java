package self_learning.coding_game.exceptions;

public class NoSuchCommandException extends RuntimeException{
    public NoSuchCommandException(){
        super();
    }
    public NoSuchCommandException(String msg){
        super(msg);
    }
}
