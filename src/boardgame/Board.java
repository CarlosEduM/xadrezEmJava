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
public class Board {
    
    private int rows;
    private int colums;
    private Piece[][] pieces;

    public Board(int rows, int columns) {
        if(rows < 1 || columns < 1){
            throw new BoardException("Erro na criação do tabuleiro: numero de linhas e colunas não pode ser menor que 1");
        }
        
        this.rows = rows;
        this.colums = columns;
        this.pieces = new Piece[rows][colums];
    }

    public int getRows() {
        return rows;
    }

    public int getColums() {
        return colums;
    }
    
    public Piece piece(int row, int column){
        if(!positionExists(row, column)){
            throw new BoardException("Posição inexistente");
        }
        return this.pieces[row][column];
    }
    
    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Posicao inexistente");
        }
        return this.pieces[position.getRow()][position.getColumn()];
    }
    
    public void placePiece(Piece piece, Position position){
        if(thereIsAPiece(position)){
            throw new BoardException("Já há uma peça na posição " + position);
        }
        
        this.pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
    
    public Piece removePiece(Position position){
        if(!this.positionExists(position)){
            throw new BoardException("Posicao fora do tabuleiro");
        }
        
        if(this.piece(position) == null){
            return null;
        }
        
        Piece aux = this.piece(position);
        aux.position = null;
        
        pieces[position.getRow()][position.getColumn()] = null;
        
        return aux;
    }
    
    private boolean positionExists(int row, int column){
        return row >= 0 && row < this.rows && column >= 0 && column < this.colums;
    }
    
    public boolean positionExists(Position position){
        return this.positionExists(position.getRow(), position.getColumn());
    }
    
    public boolean thereIsAPiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("Posicao inexistente");
        }
        return this.piece(position) != null;
    }
}
