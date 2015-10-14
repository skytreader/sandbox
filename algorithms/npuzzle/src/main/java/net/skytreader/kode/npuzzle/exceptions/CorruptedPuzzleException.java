package net.skytreader.kode.npuzzle.exceptions;

import net.skytreader.kode.npuzzle.NPuzzle;

public class CorruptedPuzzleException extends RuntimeException{
    
    public CorruptedPuzzleException(String message){
        super(message);
    }
}
