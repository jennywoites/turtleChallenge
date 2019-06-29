package models.actions;

import models.board.Board;

public class Move implements Action {

    public void doAction(Board board) {
        board.doMoveAction();
    }
}
