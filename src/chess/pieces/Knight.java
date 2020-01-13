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
public class Knight extends ChessPiece{

    public Knight(Board board, Color color) {
        super(color, board);
    }

    @Override
    public String toString() {
        return "N";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) this.getBoard().piece(position);
        
        return p == null || p.getColor() != this.getColor();
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColums()];
        
        Position p = new Position(0, 0);
        
        p.setValues(this.position.getRow() - 1, this.position.getColumn() - 2);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() - 2, this.position.getColumn() - 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() - 2, this.position.getColumn() + 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 2);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 2);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() + 2, this.position.getColumn() + 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() + 2, this.position.getColumn() - 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 2);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        return mat;
    }
}
