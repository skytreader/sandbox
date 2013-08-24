package net.skytreader.battleship.game;

public class GameConfigurationException extends Exception{
    
    public GameConfigurationException(){
        super();
    }
    
    public GameConfigurationException(String message){
        super(message);
    }

    public GameConfigurationException(String message, Throwable cause){
        super(message, cause);
    }

    public GameConfigurationException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public GameConfigurationException(Throwable cause){
        super(cause);
    }
}
