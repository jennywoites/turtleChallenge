package models.loaders;

import java.io.FileReader;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import models.board.boardElements.Exit;
import models.board.boardElements.Mine;
import models.board.Board;
import models.board.Direction;
import models.board.Position;

public final class BoardLoader {

    private static final Map<String, Direction> ORIENTATIONS_MAP;
    static {
        Map<String, Direction> aMap = new HashMap<String, Direction>();
        aMap.put("N", Direction.NORTH);
        aMap.put("S", Direction.SOUTH);
        aMap.put("E", Direction.EAST);
        aMap.put("W", Direction.WEST);
        ORIENTATIONS_MAP = Collections.unmodifiableMap(aMap);
    }

    private BoardLoader() {}

    private static Board generateBoardFromJSON(JSONObject jsonObject) throws ValueException {
        JSONObject board_size = (JSONObject) jsonObject.get("board_size");

        int rows = ((Long)board_size.get("rows")).intValue();
        int columns = ((Long)board_size.get("columns")).intValue();
        if (rows <= 0 || columns <= 0)
            throw new ValueException("The size of the board should be greater than 0 for rows and columns");

        return new Board(rows,columns);
    }

    private static void addExitFromJSON(Board board, JSONObject exit) throws ValueException {
        Position pos = getPositionFromJSON((JSONObject) exit.get("position"), board);
        board.addBoardElement(pos, new Exit());
    }

    private static Position getPositionFromJSON(JSONObject position, Board board) throws ValueException {
        int row = ((Long)position.get("row")).intValue();
        int column = ((Long)position.get("column")).intValue();

        if (row < 0 || column < 0)
            throw new ValueException("Row and Column must be positive integers for every position.");

        Position pos = new Position(row, column);

        if (!board.positionBelongsTheBoard(pos))
            throw new ValueException("The position (" + pos.posX + "," + pos.posY + ") does not belong to the board");

        return pos;
    }

    private static void updateBoardWithElementsFromJSON(Board board, JSONObject board_elements) throws ValueException {
        addExitFromJSON(board, (JSONObject) board_elements.get("exit"));

        JSONArray mines = (JSONArray) board_elements.get("mines");
        for(int i=0; i<mines.size(); i++) {
            JSONObject mine = (JSONObject) mines.get(i);
            Position pos = getPositionFromJSON((JSONObject) mine.get("position"), board);
            board.addBoardElement(pos, new Mine());
        }
    }

    private static void addTurtleFromJSON(Board board, JSONObject turtle) throws ValueException {
        Position pos = getPositionFromJSON((JSONObject) turtle.get("position"), board);
        String direction = ((String) turtle.get("orientation")).toUpperCase();
        if (ORIENTATIONS_MAP.containsKey(direction)) {
            board.addTurtle(pos, ORIENTATIONS_MAP.get(direction));
        } else {
            throw new ValueException("The direction %s is invalid", direction);
        }
    }

    public static Board loadBoardFromFile(String path) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(path));
        JSONObject jsonObject = (JSONObject) obj;

        Board board = generateBoardFromJSON(jsonObject);
        updateBoardWithElementsFromJSON(board, (JSONObject) jsonObject.get("board_elements"));

        addTurtleFromJSON(board, (JSONObject) jsonObject.get("turtle"));

        return board;
    }
}
