/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import chess.ChessMatch;

/**
 *
 * @author Cadu
 */
public class Programa {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ChessMatch chessMatch = new ChessMatch();
        
        UI.printBoard(chessMatch.getPieces());
    }
    
}
