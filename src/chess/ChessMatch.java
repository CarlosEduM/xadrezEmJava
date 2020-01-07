/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 *
 * @author Cadu
 */
public class ChessMatch {
    
    private final Board board;

    public ChessMatch() {
        this.board = new Board(8, 8);
        initialSetup();
    }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColums()];
        
        for(int i = 0; i < board.getRows(); i++){
            for(int j = 0; j < board.getColums(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        
        return mat;
    }
    
    private void initialSetup(){
        board.placePiece(new Rook(Color.WHITE, board), new Position(-2, 1));
        board.placePiece(new King(Color.BLACK, board), new Position(0, 4));
        board.placePiece(new King(Color.WHITE, board), new Position(7, 4));
    }
}
