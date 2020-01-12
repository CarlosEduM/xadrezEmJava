/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

/**
 *
 * @author Cadu
 */
public abstract class ChessPiece extends Piece{
    
    private Color color;
    protected int moveCount;

    public ChessPiece(Color color, Board board) {
        super(board);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public ChessPosition getChessPosition(){
        return ChessPosition.fromPosition(this.position);
    }
    
    public int getMoveCount(){
        return this.moveCount;
    }
    
    protected boolean isThereOpponentPiece(Position position){
        ChessPiece p = (ChessPiece) getBoard().piece(position);
        
        return p != null && p.getColor() != this.color;
    }
    
    public void increaseMoveCount(){
        this.moveCount++;
    }
    
    public void decreaseMoveCount(){
        this.moveCount--;
    }
}
