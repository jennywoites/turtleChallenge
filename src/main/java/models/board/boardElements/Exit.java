package models.board.boardElements;

import models.board.character.Turtle;

public class Exit implements BoardElement {
    public void hitTurtle(Turtle turtle) {
        /*When the turtle finds the exit is free==safe of dangers*/
        turtle.keepSafe();
    }
}
