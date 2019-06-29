package models.board;

public enum Direction {

    NORTH(new Position(-1,0)),
    SOUTH(new Position(1,0)),
    EAST(new Position(0,1)),
    WEST(new Position(0,-1));

    private Position value;

    Direction(Position position) {
        this.value = position;
    }

    public Position getValue() {
        return value;
    }
}