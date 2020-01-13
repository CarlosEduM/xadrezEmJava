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
public class King extends ChessPiece{
    
    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(color, board);
        this.chessMatch = chessMatch;
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece) this.getBoard().piece(position);
        
        return p == null || p.getColor() != this.getColor();
    }
    
    private boolean testRookCasting(Position position){
        ChessPiece p = (ChessPiece) this.getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == this.getColor() && p.getMoveCount() == 0;
    }
    
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[this.getBoard().getRows()][this.getBoard().getColums()];
        
        Position p = new Position(0, 0);
        
        // Cima
        p.setValues(this.position.getRow() - 1, this.position.getColumn());
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Baixo
        p.setValues(this.position.getRow() + 1, this.position.getColumn());
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Esquerda
        p.setValues(this.position.getRow(), this.position.getColumn() - 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Direita
        p.setValues(this.position.getRow(), this.position.getColumn() + 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Noroeste
        p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Nordeste
        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Sudoeste
        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Sudeste
        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        if(this.getBoard().positionExists(p) && this.canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }
        
        // Movimento especial Roque
        if(this.getMoveCount() == 0 && !this.chessMatch.getCheck()){
            Position posT1 = new Position(this.position.getRow(), this.position.getColumn() + 3);
            
            if(this.testRookCasting(posT1)){
                Position p1 = new Position(this.position.getRow(), this.position.getColumn() + 1);
                Position p2 = new Position(this.position.getRow(), this.position.getColumn() + 2);
                
                if(this.getBoard().piece(p1) == null && this.getBoard().piece(p2) == null){
                    mat[this.position.getRow()][this.position.getColumn() + 2] = true;
                }
            }
            
            Position posT2 = new Position(this.position.getRow(), this.position.getColumn() - 4);
            
            if(this.testRookCasting(posT1)){
                Position p1 = new Position(this.position.getRow(), this.position.getColumn() - 1);
                Position p2 = new Position(this.position.getRow(), this.position.getColumn() - 2);
                Position p3 = new Position(this.position.getRow(), this.position.getColumn() - 3);
                
                if(this.getBoard().piece(p1) == null && this.getBoard().piece(p2) == null && this.getBoard().piece(p3) == null){
                    mat[this.position.getRow()][this.position.getColumn() - 2] = true;
                }
            }
        }
        
        return mat;
    }
    
}