package models.board;

import models.board.boardElements.BlankSpace;
import models.board.boardElements.Exit;
import models.board.boardElements.Mine;
import models.board.boardElements.Wall;
import models.board.character.Turtle;
import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    void initializeBoardWithNegativeRowsThrowsNegativeArraySizeException() {
        boolean exceptionHappened = false;
        try{
            Board board = new Board(-1, 10);
        } catch (NegativeArraySizeException e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void initializeBoardWithNegativeColumnsThrowsNegativeArraySizeException() {
        boolean exceptionHappened = false;
        try{
            Board board = new Board(1, -4);
        } catch (NegativeArraySizeException e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void initializeBoardWithZeroRowsAndColumnsThrowsNegativeArraySizeException() {
        boolean exceptionHappened = false;
        try{
            Board board = new Board(0, 0);
        } catch (NegativeArraySizeException e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void addTurtleInABlankSpaceKeepsItAliveAndNotSafe() {
        Board board = new Board(4, 5);
        board.addTurtle(new Position(0, 0), Direction.SOUTH);
        Turtle turtle = board.getTurtle();

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == 0 && turtlePosition.posY == 0);
        assert(turtle.isAlive());
        assert(!turtle.isSafe());
        assert(!board.gameHasEnded());
    }

    @Test
    void addTurtleInExitKeepsItAliveAndSafe() {
        Board board = new Board(4, 5);
        board.addBoardElement(new Position(3,4), new Exit());
        board.addTurtle(new Position(3, 4), Direction.EAST);
        Turtle turtle = board.getTurtle();

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == 3 && turtlePosition.posY == 4);
        assert(turtle.isAlive());
        assert(turtle.isSafe());
        assert(board.gameHasEnded());
    }

    @Test
    void addTurtleInMineKillsIt() {
        Board board = new Board(5, 8);
        board.addBoardElement(new Position(2,4), new Mine());
        board.addTurtle(new Position(2, 4), Direction.EAST);
        Turtle turtle = board.getTurtle();

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == 2 && turtlePosition.posY == 4);
        assert(!turtle.isAlive());
        assert(!turtle.isSafe());
        assert(board.gameHasEnded());
    }

    @Test
    void addTurtleWithMineNextSouthPositionTurtleWithEastOrientationMoveDoesNotKillTheTurtle() {
        Board board = new Board(3, 3);
        board.addBoardElement(new Position(1,0), new Mine());
        board.addTurtle(new Position(0, 0), Direction.EAST);
        board.doMoveAction();

        Turtle turtle = board.getTurtle();

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == 0 && turtlePosition.posY == 1);
        assert(turtle.isAlive());
        assert(!turtle.isSafe());
        assert(!board.gameHasEnded());
    }

    @Test
    void addTurtleWithMineNextSouthPositionTurtleWithSouthOrientationMoveKillTheTurtle() {
        Board board = new Board(3, 3);
        board.addBoardElement(new Position(1,0), new Mine());
        board.addTurtle(new Position(0, 0), Direction.SOUTH);
        board.doMoveAction();

        Turtle turtle = board.getTurtle();

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == 1 && turtlePosition.posY == 0);
        assert(!turtle.isAlive());
        assert(!turtle.isSafe());
        assert(board.gameHasEnded());
    }

    @Test
    void addTurtleTryMoveItToOutsideTheBoardDoesNotChangeTurtlePosition() {
        Board board = new Board(3, 3);
        board.addTurtle(new Position(0, 1), Direction.NORTH);
        board.doMoveAction();

        Turtle turtle = board.getTurtle();
        Position turtlePosition = turtle.getPosition();

        assert(turtlePosition.posX == 0 && turtlePosition.posY == 1);
    }

    @Test
    void moveTurtleNextPositionOutsideBoardDoesNotChangeTurtlePosition() {
        Board board = new Board(3, 3);

        Position originTurtle = new Position(0, 1);
        board.addTurtle(originTurtle, Direction.NORTH);

        Turtle turtle = board.getTurtle();

        board.moveTurtleToNextPosition(turtle.getPosition(), turtle.getDirection());

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == originTurtle.posX && turtlePosition.posY == originTurtle.posY);
    }

    @Test
    void moveTurtleNextPositionSouthInsideBoardChangeTurtlePosition() {
        Board board = new Board(3, 3);

        Position originTurtle = new Position(1, 1);
        board.addTurtle(originTurtle, Direction.SOUTH);

        Turtle turtle = board.getTurtle();
        board.moveTurtleToNextPosition(turtle.getPosition(), turtle.getDirection());

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == (originTurtle.posX + 1) && turtlePosition.posY == originTurtle.posY);
    }

    @Test
    void moveTurtleNextPositionNorthInsideBoardChangeTurtlePosition() {
        Board board = new Board(3, 3);

        Position originTurtle = new Position(1, 1);
        board.addTurtle(originTurtle, Direction.NORTH);

        Turtle turtle = board.getTurtle();
        board.moveTurtleToNextPosition(turtle.getPosition(), turtle.getDirection());

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == (originTurtle.posX -1) && turtlePosition.posY == originTurtle.posY);
    }

    @Test
    void moveTurtleNextPositionEastInsideBoardChangeTurtlePosition() {
        Board board = new Board(3, 3);

        Position originTurtle = new Position(1, 1);
        board.addTurtle(originTurtle, Direction.EAST);

        Turtle turtle = board.getTurtle();
        board.moveTurtleToNextPosition(turtle.getPosition(), turtle.getDirection());

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == originTurtle.posX && turtlePosition.posY == (originTurtle.posY + 1));
    }

    @Test
    void moveTurtleNextPositionWestInsideBoardChangeTurtlePosition() {
        Board board = new Board(3, 3);

        Position originTurtle = new Position(1, 1);
        board.addTurtle(originTurtle, Direction.WEST);

        Turtle turtle = board.getTurtle();
        board.moveTurtleToNextPosition(turtle.getPosition(), turtle.getDirection());

        Position turtlePosition = turtle.getPosition();
        assert(turtlePosition.posX == originTurtle.posX && turtlePosition.posY == (originTurtle.posY - 1));
    }

    @Test
    void negativePositionDoesNotBelongToTheBoard() {
        Board board = new Board(7, 5);
        assert(!board.positionBelongsTheBoard(new Position(-1,0)));
        assert(!board.positionBelongsTheBoard(new Position(4,-2)));
        assert(!board.positionBelongsTheBoard(new Position(-3,-3)));
        assert(!board.positionBelongsTheBoard(new Position(-1,-1)));
    }

    @Test
    void borderPositionAndInsidePositionBelongsToTheBoardButTotalRowOrColumnDoesNot() {
        Board board = new Board(7, 5);

        assert(board.positionBelongsTheBoard(new Position(0,0)));

        assert(board.positionBelongsTheBoard(new Position(0,1)));
        assert(board.positionBelongsTheBoard(new Position(0,4)));
        assert(board.positionBelongsTheBoard(new Position(6,0)));
        assert(board.positionBelongsTheBoard(new Position(6,3)));

        assert(board.positionBelongsTheBoard(new Position(6,4)));

        assert(board.positionBelongsTheBoard(new Position(3,2)));

        assert(!board.positionBelongsTheBoard(new Position(7,0)));
        assert(!board.positionBelongsTheBoard(new Position(3,5)));
        assert(!board.positionBelongsTheBoard(new Position(7,5)));
    }

    @Test
    void nullPositionDoesNotBelongToTheBoard() {
        Board board = new Board(7, 5);
        assert(!board.positionBelongsTheBoard(null));
    }

    @Test
    void doRotateChangesTurtleOrientation() {
        Board board = new Board(3, 3);

        Direction originDirection = Direction.WEST;
        board.addTurtle(new Position(1, 1), originDirection);
        board.doRotateAction();

        Turtle turtle = board.getTurtle();
        assert(turtle.getDirection() != originDirection);
    }

    @Test
    void getNextElementInsideBoardGetTheElement() {
        Board board = new Board(3, 3);

        board.addBoardElement(new Position(0,2), new Mine());
        board.addBoardElement(new Position(2,1), new Mine());

        board.addBoardElement(new Position(1,2), new Exit());

        Position origin = new Position(1,1);

        assert(board.getNextBoardElementFromPosition(origin, Direction.NORTH).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(origin, Direction.SOUTH).getClass() == Mine.class);
        assert(board.getNextBoardElementFromPosition(origin, Direction.EAST).getClass() == Exit.class);
        assert(board.getNextBoardElementFromPosition(origin, Direction.WEST).getClass() == BlankSpace.class);
    }

    @Test
    void getNextElementBorderBoardGetAWall() {
        Board board = new Board(3, 3);

        board.addBoardElement(new Position(1,0), new Mine());
        board.addBoardElement(new Position(1,2), new Exit());

        Position origin = new Position(0,0);

        assert(board.getNextBoardElementFromPosition(origin, Direction.NORTH).getClass() == Wall.class);
        assert(board.getNextBoardElementFromPosition(origin, Direction.WEST).getClass() == Wall.class);

        assert(board.getNextBoardElementFromPosition(origin, Direction.SOUTH).getClass() == Mine.class);
        assert(board.getNextBoardElementFromPosition(origin, Direction.EAST).getClass() == BlankSpace.class);
    }
}