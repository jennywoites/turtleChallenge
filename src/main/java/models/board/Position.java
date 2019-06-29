package models.board;

public class Position {
    public int posX;
    public int posY;

    public Position(int x, int y) {
        posX = x;
        posY = y;
    }

    public Position add(Position pos) {
        int x = posX + pos.posX;
        int y = posY + pos.posY;
        return new Position(x, y);
    }
}
