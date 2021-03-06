/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author Cadu
 */
public class Pawn extends ChessPiece{
    
    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(color, board);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColums()];
        
        Position p = new Position(0, 0);
        
        if(this.getColor() == Color.WHITE){
            p.setValues(this.position.getRow() - 1, this.position.getColumn());
            
            if(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() - 2, this.position.getColumn());
            
            Position p2 = new Position(this.position.getRow() - 1, this.position.getColumn());
            
            if(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p) && this.moveCount == 0 && this.getBoard().positionExists(p2) && !this.getBoard().thereIsAPiece(p2)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
            
            if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
            
            if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            // en passant WHITE
            if(this.position.getRow() == 3){
                Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
                if(this.getBoard().positionExists(left) && this.isThereOpponentPiece(left) && this.getBoard().piece(left) == this.chessMatch.getEnPassantVunerable()){
                    mat[left.getRow() - 1][left.getColumn()] =  true;
                }
                
                Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
                if(this.getBoard().positionExists(right) && this.isThereOpponentPiece(right) && this.getBoard().piece(right) == this.chessMatch.getEnPassantVunerable()){
                    mat[right.getRow() - 1][right.getColumn()] =  true;
                }
            }
            
        }
        else{
            p.setValues(this.position.getRow() + 1, this.position.getColumn());
            
            if(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() + 2, this.position.getColumn());
            
            Position p2 = new Position(this.position.getRow() + 1, this.position.getColumn());
            
            if(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p) && this.moveCount == 0 && this.getBoard().positionExists(p2) && !this.getBoard().thereIsAPiece(p2)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
            
            if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
            
            if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
                mat[p.getRow()][p.getColumn()] = true;
            }
            
            // en passant BLACK
            if(this.position.getRow() == 4){
                Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
                if(this.getBoard().positionExists(left) && this.isThereOpponentPiece(left) && this.getBoard().piece(left) == this.chessMatch.getEnPassantVunerable()){
                    mat[left.getRow() + 1][left.getColumn()] =  true;
                }
                
                Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
                if(this.getBoard().positionExists(right) && this.isThereOpponentPiece(right) && this.getBoard().piece(right) == this.chessMatch.getEnPassantVunerable()){
                    mat[right.getRow() + 1][right.getColumn()] =  true;
                }
            }
        }
            
        
        
        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
    
}
