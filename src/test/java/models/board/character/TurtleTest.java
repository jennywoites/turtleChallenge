package models.board.character;

import models.board.Board;
import models.board.Direction;
import models.board.Position;
import models.board.boardElements.Mine;
import org.junit.jupiter.api.Test;

public class TurtleTest {

    @Test
    void updatePositionWithNullPositionCHangesToIt() {
        Position origin = new Position(0,0);

        Turtle turtle = new Turtle(origin, Direction.NORTH);
        turtle.updatePosition(null);
        assert(turtle.getPosition() == null);
    }

    @Test
    void updatePositionWithANewPositionChangesIt() {
        Position origin = new Position(0,0);

        Turtle turtle = new Turtle(origin, Direction.NORTH);
        Position currentPos = new Position(2,3);

        turtle.updatePosition(currentPos);
        assert(turtle.getPosition() == currentPos);
    }

    @Test
    void rotateGoesInClockWise() {
        Turtle turtle = new Turtle(new Position(0,0), Direction.NORTH);
        assert(turtle.getDirection() == Direction.NORTH);

        turtle.rotate(null);
        assert(turtle.getDirection() == Direction.EAST);

        turtle.rotate(null);
        assert(turtle.getDirection() == Direction.SOUTH);

        turtle.rotate(null);
        assert(turtle.getDirection() == Direction.WEST);

        turtle.rotate(null);
        assert(turtle.getDirection() == Direction.NORTH);
    }

    @Test
    void reduceLifeMoreThanInitialKeepsItAtZero() {
        Turtle turtle = new Turtle(new Position(0,0), Direction.NORTH);
        int initialLife = turtle.getLife();

        turtle.reduceLife(initialLife + 5);
        assert(turtle.getLife() == 0);
    }

    @Test
    void reduceLifeKillsTheTurtle() {
        Turtle turtle = new Turtle(new Position(0,0), Direction.NORTH);
        assert(turtle.isAlive());
        turtle.reduceLife(turtle.getLife());
        assert(!turtle.isAlive());
    }

    @Test
    void makeTurtleSafeChangesItState() {
        Turtle turtle = new Turtle(new Position(0,0), Direction.NORTH);
        assert(!turtle.isSafe());

        turtle.keepSafe();
        assert(turtle.isSafe());

        // Calling again keeps the state safe
        turtle.keepSafe();
        assert(turtle.isSafe());
    }

    @Test
    void moveATurtleNorthInTheBoardWithMineKillTheTurtle() {
        Board board = new Board(3,3);
        board.addBoardElement(new Position(0,1), new Mine());

        Position origin = new Position(1,1);
        board.addTurtle(origin, Direction.NORTH);

        Turtle turtle = board.getTurtle();
        assert(turtle.isAlive());

        turtle.move(board);
        assert(!turtle.isAlive());

        Position currentPos = turtle.getPosition();
        assert(currentPos.posX == (origin.posX - 1) && currentPos.posY == origin.posY);
        assert(turtle.getDirection() == Direction.NORTH);
    }

    @Test
    void moveATurtleSouthInTheBoardBlankSpaceDoesNotKillTheTurtle() {
        Board board = new Board(3,3);

        Position origin = new Position(1,1);
        board.addTurtle(origin, Direction.SOUTH);

        Turtle turtle = board.getTurtle();
        assert(turtle.isAlive());

        turtle.move(board);
        assert(turtle.isAlive());

        Position currentPos = turtle.getPosition();
        assert(currentPos.posX == (origin.posX + 1) && currentPos.posY == origin.posY);
        assert(turtle.getDirection() == Direction.SOUTH);
    }
}