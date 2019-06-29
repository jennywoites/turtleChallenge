package models.board;

import models.board.boardElements.BoardElement;
import models.board.boardElements.Wall;
import models.board.character.Turtle;
import models.board.boardElements.BlankSpace;

public class Board {

    BoardElement[][] cells;
    Turtle originalTurtle;
    Turtle turtle;

    public Board(int rows, int columns) throws NegativeArraySizeException {
        if (rows <=0 || columns <=0 )
            throw new NegativeArraySizeException("Rows and columns must be greater than 0.");

        cells = new BoardElement[rows][columns];
        setInitialBlankBoard();
    }

    public Turtle getTurtle() {
        return turtle;
    }

    private void setInitialBlankBoard() {
        for(int row=0; row < cells.length; row++) {
            for(int column=0; column < cells[row].length; column++) {
                addBoardElement(new Position(row,column), new BlankSpace());
            }
        }
    }

    public void addTurtle(Position position, Direction direction) {
        // If you add the turtle before adding more elements to the board the
        // turtle will not hit with them.

        turtle = new Turtle(position, direction);

        /* if the turtle start in a element like the exit or a mine,
        it should interact with it.
        */
        BoardElement element = cells[position.posX][position.posY];
        element.hitTurtle(turtle);

        originalTurtle = turtle.copy();
    }

    public void reset() {
        turtle = originalTurtle.copy();
    }

    public void addBoardElement(Position position, BoardElement element) {
        cells[position.posX][position.posY] = element;
    }

    public boolean gameHasEnded() {
        return !turtle.isAlive() || turtle.isSafe();
    }

    public void doMoveAction() {
        turtle.move(this);
    }

    public void doRotateAction() {
        turtle.rotate(this);
    }

    public void moveTurtleToNextPosition(Position position, Direction orientation) {
        Position newPosition = position.add(orientation.getValue());

        // Only change position if the new position is in the board, if not, the turtle keeps its position
        if (positionBelongsTheBoard(newPosition)) {
            turtle.updatePosition(newPosition);
        }
    }

    public boolean positionBelongsTheBoard(Position pos) {
        if (pos == null)
            return false;

        return (0 <= pos.posX && pos.posX < cells.length && 0 <= pos.posY && pos.posY < cells[0].length);
    }

    public BoardElement getNextBoardElementFromPosition(Position position, Direction orientation) {
        Position newPosition = position.add(orientation.getValue());

        if (positionBelongsTheBoard(newPosition)) {
            return cells[newPosition.posX][newPosition.posY];
        }

        // It's trying to go outside the board
        return new Wall();
    }

    public String getStringState() {
        if (turtle.isSafe())
            return "Success!";
        if (!turtle.isAlive())
            return "Mine hit!";
        return "Still in danger!";
    }

}
