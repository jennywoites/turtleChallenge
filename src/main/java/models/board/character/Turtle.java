package models.board.character;

import models.board.Board;
import models.board.Direction;
import models.board.Position;
import models.board.boardElements.BoardElement;

import static models.board.Direction.EAST;
import static models.board.Direction.NORTH;
import static models.board.Direction.SOUTH;
import static models.board.Direction.WEST;

public class Turtle implements BoardElement {

    Position currentPosition;
    Direction[] directions;
    int currentDirection;
    boolean isSafe; //The turtle is safe when it reached the exit
    int life;

    private static final int MAX_LIFE = 1;

    public Turtle() {}

    public Turtle(Position position, Direction direction) {
        isSafe = false;
        life = MAX_LIFE;

        currentPosition = position;
        directions = new Direction[] {NORTH, EAST, SOUTH, WEST};

        // Find the initial direction
        currentDirection = 0;
        while (getDirection() != direction)
            currentDirection ++;
    }

    public Turtle copy(){
        Turtle newTurtle = new Turtle();
        newTurtle.currentPosition = this.currentPosition;
        newTurtle.directions = this.directions;
        newTurtle.currentDirection = this.currentDirection;
        newTurtle.isSafe = this.isSafe;
        newTurtle.life = this.life;
        return newTurtle;
    }

    public Direction getDirection() {
        return directions[currentDirection];
    }

    public Position getPosition() {
        return currentPosition;
    }

    public void updatePosition(Position pos) {
        currentPosition = pos;
    }

    public void rotate(Board board) {
        currentDirection = (currentDirection + 1) % directions.length;
    }

    public void move(Board board) {
        BoardElement element = board.getNextBoardElementFromPosition(currentPosition, getDirection());
        board.moveTurtleToNextPosition(currentPosition, getDirection());
        element.hitTurtle(this);
    }

    public void reduceLife(int power) {
        life -= power;
        if (life < 0)
            life = 0;
    }

    public int getLife() {
        return life;
    }

    public boolean isAlive() {
        return (life > 0);
    }

    public boolean isSafe() {
        return isSafe;
    }

    public void keepSafe() {
        isSafe = true;
    }

    public void hitTurtle(Turtle turtle) {
        /* Do nothing.
        *  This is not happening right now because only one character is contemplated.
        *  If others are added it should be defined what happens when a character hits
        *  another one. */
    }
}
