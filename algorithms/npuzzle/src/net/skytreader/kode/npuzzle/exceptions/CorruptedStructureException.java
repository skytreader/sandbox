package net.skytreader.kode.npuzzle.exception;

import net.skytreader.kode.npuzzle.NPuzzle;

public class CorruptedPuzzleException extends RuntimeException{
    
    public CorruptedPuzzleException(String message){
        super(message);
    }
}
