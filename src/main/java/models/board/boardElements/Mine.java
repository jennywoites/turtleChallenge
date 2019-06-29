package models.board.boardElements;

import models.board.character.Turtle;

public class Mine implements BoardElement {

    private static final int POWER = 1;

    public void hitTurtle(Turtle turtle) {
        /*The turtle reduce it's life*/
        turtle.reduceLife(POWER);
    }
}
