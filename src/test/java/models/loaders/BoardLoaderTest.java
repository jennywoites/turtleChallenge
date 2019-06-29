package models.loaders;

import models.board.Board;
import models.board.Direction;
import models.board.Position;
import models.board.boardElements.BlankSpace;
import models.board.boardElements.Exit;
import models.board.boardElements.Mine;
import org.junit.jupiter.api.Test;

public class BoardLoaderTest {

    private final String PATH_TEST_FILES = "board_files/test/board_config_test/";

    private void createBoardAndAssertExceptionHappend(String path) {
        boolean exceptionHappened = false;
        try{
            BoardLoader.loadBoardFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void loadBoardFileDoesNotExistThrowsException() {
        createBoardAndAssertExceptionHappend("fileDoesNotExist.json");
    }

    @Test
    void loadBoardWithMinesAndExitLoadIt() {
        Board board = null;
        boolean exceptionHappened = false;
        try{
            board = BoardLoader.loadBoardFromFile(PATH_TEST_FILES + "loadBoardWithMinesAndExitLoadIt.json");
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(!exceptionHappened);

        assert(board.getNextBoardElementFromPosition(new Position(0,1), Direction.WEST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(0,0), Direction.EAST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(0,1), Direction.EAST).getClass() == Exit.class);

        assert(board.getNextBoardElementFromPosition(new Position(1,1), Direction.WEST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(1,0), Direction.EAST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(1,1), Direction.EAST).getClass() == BlankSpace.class);

        assert(board.getNextBoardElementFromPosition(new Position(2,1), Direction.WEST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(2,0), Direction.EAST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(2,1), Direction.EAST).getClass() == Mine.class);

        assert(board.getNextBoardElementFromPosition(new Position(3,1), Direction.WEST).getClass() == Mine.class);
        assert(board.getNextBoardElementFromPosition(new Position(3,0), Direction.EAST).getClass() == BlankSpace.class);
        assert(board.getNextBoardElementFromPosition(new Position(3,1), Direction.EAST).getClass() == BlankSpace.class);

        Position turtlePos = board.getTurtle().getPosition();
        assert(turtlePos.posX == 1 && turtlePos.posY == 1);
    }

    @Test
    void loadBoardWithoutAKeyThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithoutAKeyThrowsException.json");
    }

    @Test
    void loadBoardWithZeroRowsAsSizeThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithZeroRowsAsSizeThrowsException.json");
    }

    @Test
    void loadBoardWithNegativeRowsAsSizeThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithNegativeRowsAsSizeThrowsException.json");
    }

    @Test
    void loadBoardWithZeroColumnsAsSizeThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithZeroColumnsAsSizeThrowsException.json");
    }

    @Test
    void loadBoardWithNegativeColumnsAsSizeThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithNegativeColumnsAsSizeThrowsException.json");
    }

    @Test
    void loadBoardWithBoardElementOutsideTheBoardThrowsException() {
        createBoardAndAssertExceptionHappend("loadBoardWithBoardElementOutsideTheBoardThrowsException.json");
    }
}
