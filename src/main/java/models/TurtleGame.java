package models;

import com.sun.media.sound.InvalidFormatException;
import models.actions.Action;
import models.board.Board;
import models.loaders.BoardLoader;
import models.loaders.MovementsLoader;

import java.util.ArrayList;

public class TurtleGame {

    Board board;
    ArrayList<ArrayList<Action>> sequences;

    public TurtleGame(String filePathBoard, String filePathMovements) throws InvalidFormatException {
        try {
            board = BoardLoader.loadBoardFromFile(filePathBoard);
        } catch (Exception e) {
            String msg = "The board configuration file " + filePathBoard + " has a bad format";
            if (e.getMessage() != null && e.getMessage() != "")
                msg = e.getMessage();

            throw new InvalidFormatException(msg);
        }

        try {
            sequences = MovementsLoader.loadSequencesOfActionsFromFile(filePathMovements);
        } catch (Exception e) {
            throw new InvalidFormatException("The sequence of movements file " + filePathBoard + " has a bad format");
        }
    }

    public void startGame() {
        if (sequences.size() == 0)
            System.out.printf("There are no sequences of movements");

        for(int i=0; i<sequences.size(); i++) {
            executeActionsToBoard(sequences.get(i), i+1);
        }
    }

    private void executeActionsToBoard(ArrayList<Action> actions, int sequenceNumber){
        while (!board.gameHasEnded() && actions.size() > 0) {
            Action action = actions.remove(0);
            action.doAction(board);
        }

        System.out.printf("Sequence %d: %s%n", sequenceNumber,  board.getStringState());
        board.reset();
    }
}