package models.board;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    void addZeroBothCoordinatesKeepOriginalCoordinates() {
        Position origin = new Position(2,3);
        Position positionToAdd = new Position(0,0);
        Position result = origin.add(positionToAdd);
        assert(result.posX == origin.posX);
        assert(result.posY == origin.posY);
    }

    @Test
    void addPositiveNumberXCoordinatesKeepYAddInX() {
        Position origin = new Position(2,3);
        Position positionToAdd = new Position(1,0);
        Position result = origin.add(positionToAdd);
        assert(result.posX == (origin.posX + 1));
        assert(result.posY == origin.posY);
    }

    @Test
    void addNegativeNumberXCoordinatesKeepYSubstractInX() {
        Position origin = new Position(2,3);
        Position positionToAdd = new Position(-5,0);
        Position result = origin.add(positionToAdd);
        assert(result.posX == (origin.posX -5));
        assert(result.posY == origin.posY);
    }

    @Test
    void addPositiveNumberYCoordinatesKeepXAddInY() {
        Position origin = new Position(2,3);
        Position positionToAdd = new Position(0,3);
        Position result = origin.add(positionToAdd);
        assert(result.posX == origin.posX);
        assert(result.posY == (origin.posY + 3));
    }

    @Test
    void addNegativeNumberYCoordinatesKeepXSubstractInY() {
        Position origin = new Position(2,3);
        Position positionToAdd = new Position(0,-6);
        Position result = origin.add(positionToAdd);
        assert(result.posX == origin.posX);
        assert(result.posY == (origin.posY - 6));
    }

    @Test
    void addNullPositionThrowNullPointerException() {
        Position origin = new Position(2,3);
        Position positionToAdd = null;

        boolean exceptionHappened = false;
        try{
            Position result = origin.add(positionToAdd);
        } catch (NullPointerException e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }
}
