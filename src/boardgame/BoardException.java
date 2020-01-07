/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boardgame;

/**
 *
 * @author Cadu
 */
public class BoardException extends RuntimeException{
    public BoardException() {
    }

    public BoardException(String message) {
        super(message);
    }
    
}
