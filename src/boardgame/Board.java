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

    public Board(int rows, int colums) {
        this.rows = rows;
        this.colums = colums;
        this.pieces = new Piece[rows][colums];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColums() {
        return colums;
    }

    public void setColums(int colums) {
        this.colums = colums;
    }
    
    
    
}
