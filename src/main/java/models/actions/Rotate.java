package models.actions;

import models.board.Board;

public class Rotate implements Action{
    public void doAction(Board board) {
        board.doRotateAction();
    }
}
