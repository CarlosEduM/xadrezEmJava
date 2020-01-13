/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Rook;
import chess.pieces.Queen;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Cadu
 */
public class ChessMatch {
    
    private int turn;
    private Color currentPlayer;
    private final Board board;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    
    private List<ChessPiece> piecesOnTheBoard = new ArrayList();
    private List<ChessPiece> capturedPieces = new ArrayList();

    public ChessMatch() {
        this.board = new Board(8, 8);
        this.turn = 1;
        this.currentPlayer = Color.WHITE;
        this.check = false;
        this.checkMate = false;
        initialSetup();
    }

    public int getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }
    
    public boolean getCheck(){
        return check;
    }
    
    public boolean getCheckMate(){
        return checkMate;
    }
    
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[this.board.getRows()][this.board.getColums()];
        
        for(int i = 0; i < this.board.getRows(); i++){
            for(int j = 0; j < this.board.getColums(); j++){
                mat[i][j] = (ChessPiece) this.board.piece(i, j);
            }
        }
        
        return mat;
    }
    
    public ChessPiece getEnPassantVunerable(){
        return enPassantVulnerable;
    }
    
    public ChessPiece getPromoted(){
        return this.promoted;
    }
    
    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        this.validateSourcePosition(position);
        
        return this.board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        this.validateSourcePosition(source);
        this.validateTargetPosition(source, target);
        Piece capturedPiece = this.makeMove(source, target);
        
        if(this.testCheck(this.currentPlayer)){
            this.undoMove(source, target, capturedPiece);
            throw new ChessException("Voce nao pode se coloca em check");
        }
        
        ChessPiece movedPiece = (ChessPiece) this.board.piece(target);
        
        promoted = null;
        if(movedPiece instanceof Pawn){
            if((movedPiece.getColor() == Color.WHITE && target.getRow() == 0) || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)){
                this.promoted = (ChessPiece) this.board.piece(target);
                this.promoted = replacePromotedPiece("Q");
            }
        }
        
        this.check = (this.testCheck(this.opponent(currentPlayer)));
        
        if(this.testCheckMate(this.opponent(currentPlayer))){
            this.checkMate = true;
        }
        else{
            this.nextTurn();
        }
        
        // jogada especial en passant
        if(movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)){
            this.enPassantVulnerable = movedPiece;
        }
        else{
            this.enPassantVulnerable = null;
        }
        
        return (ChessPiece) capturedPiece;
    }
    
    public ChessPiece replacePromotedPiece(String type){
        if(this.promoted == null){
            throw new IllegalStateException("Nao ha peca para ser promovida");
        }
        if(!type.equals("B") && !type.equals("N") && !type.equals("R") && !type.equals("Q")){
            throw new InvalidParameterException("Tipo de promocao invalida");
        }
        
        Position pos = this.promoted.getChessPosition().toPosition();
        Piece p = this.board.removePiece(pos);
        this.piecesOnTheBoard.remove((ChessPiece) p);
        
        ChessPiece newPiece = newPiece(type, this.promoted.getColor());
        this.board.placePiece(newPiece, pos);
        this.piecesOnTheBoard.add(newPiece);
        
        return newPiece;
    }
    
    private ChessPiece newPiece(String type, Color color){
        if("B".equals(type)){
            return new Bishop(this.board, color);
        }
        if("N".equals(type)){
            return new Knight(this.board, color);
        }
        if("Q".equals(type)){
            return new Queen(this.board, color);
        }
        else{
            return new Rook(this.board, color);
        }
    }
    
    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece)this.board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = this.board.removePiece(target);
        this.board.placePiece(p, target);
        
        if(capturedPiece != null){
            this.capturedPieces.add((ChessPiece) capturedPiece);
            this.piecesOnTheBoard.remove((ChessPiece) capturedPiece);
        }
        
        if(p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            
            ChessPiece rook = (ChessPiece) this.board.removePiece(sourceT);
            this.board.placePiece(rook, targetT);
            
            rook.increaseMoveCount();
        }
        
        if(p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            
            ChessPiece rook = (ChessPiece) this.board.removePiece(sourceT);
            this.board.placePiece(rook, targetT);
            
            rook.increaseMoveCount();
        }
        
        if(p instanceof Pawn){
            if(source.getColumn() != target.getColumn() && capturedPiece == null){
                Position pawnPosition;
                
                if(p.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                else{
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                
                capturedPiece = this.board.removePiece(pawnPosition);
                this.capturedPieces.add((ChessPiece) capturedPiece);
                this.piecesOnTheBoard.remove((ChessPiece) capturedPiece);
            }
        }
        
        return capturedPiece;
    }
    
    private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove((ChessPiece) capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}

		// #specialmove castling kingside rook
		if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
			Position targetT = new Position(source.getRow(), source.getColumn() + 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// #specialmove castling queenside rook
		if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
			Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
			Position targetT = new Position(source.getRow(), source.getColumn() - 1);
			ChessPiece rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}
		
		// #specialmove en passant
		if (p instanceof Pawn) {
			if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if (p.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getColumn());
				}
				else {
					pawnPosition = new Position(4, target.getColumn());
				}
				board.placePiece(pawn, pawnPosition);
			}
		}
	}
    
    private void validateSourcePosition(Position source){
        if(!this.board.thereIsAPiece(source)){
            throw new ChessException("Nao existe nenhuma peca nesta posicao");
        }
        
        ChessPiece piece = (ChessPiece)this.board.piece(source);
        
        if(this.currentPlayer != piece.getColor()){
            throw new ChessException("A peca escolhida nao e sua");
        }
        
        if(!this.board.piece(source).isThereAnyPossibleMove()){
            throw new ChessException("Nao existe movimentos possiveis com essa peca");
        }
        
    }
    
    private void validateTargetPosition(Position source, Position target) {
        if(!this.board.piece(source).possibleMove(target)){
            throw new ChessException("Esta peca nao pode fazer esse movimento");
        }
    }
    
    private void nextTurn(){
        this.turn++;
        this.currentPlayer = (this.currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }
    
    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK: Color.WHITE;
    }
    
    private ChessPiece king(Color color){
        List<ChessPiece> list = this.piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
        for(ChessPiece p: list){
            if(p instanceof King){
                return p;
            }
        }
        throw new IllegalStateException("Não existe rei " + color + " no tabuleiro");
    }
    
    private boolean testCheck(Color color) {
            Position kingPosition = king(color).getChessPosition().toPosition();
            List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
            for (Piece p : opponentPieces) {
                    boolean[][] mat = p.possibleMoves();
                    if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                            return true;
                    }
            }
            return false;
    }
    
    private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
                
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
                
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
                        
			for (int i=0; i<board.getRows(); i++) {
				for (int j=0; j<board.getColums(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
                                                
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						
                                                if (!testCheck) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}	
    
    private void placeNewPiece(char column, int row, ChessPiece piece){
        this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
        this.piecesOnTheBoard.add(piece);
    }
    
    private void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }

}
