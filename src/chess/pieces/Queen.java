/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

/**
 *
 * @author Cadu
 */
public class Queen extends ChessPiece{

    public Queen(Board board, Color color) {
        super(color, board);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColums()];
        
        Position p = new Position(0, 0);
        
        // Cima
        
        p.setValues(this.position.getRow() - 1, this.position.getColumn());
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Esqueda
        
        p.setValues(this.position.getRow(), this.position.getColumn() - 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // direita
        
        p.setValues(this.position.getRow(), this.position.getColumn() + 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Baixo
        
        p.setValues(this.position.getRow() + 1, this.position.getColumn());
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Noroeste
        
        p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Nordeste
        
        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Sudeste
        
        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Sudoeste
        
        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        
        while(this.getBoard().positionExists(p) && !this.getBoard().thereIsAPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        
        if(this.getBoard().positionExists(p) && this.isThereOpponentPiece(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        return mat;
    }
    
}
