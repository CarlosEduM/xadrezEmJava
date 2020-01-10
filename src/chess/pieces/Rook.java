/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author Cadu
 */
public class Rook extends ChessPiece{

    public Rook(Board board, Color color) {
        super(color, board);
    }

    @Override
    public String toString() {
        return "R";
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColums()];
        
        return mat;
    }
    
}
