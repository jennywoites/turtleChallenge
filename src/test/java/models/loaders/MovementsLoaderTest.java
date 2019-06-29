package models.loaders;

import models.actions.Action;
import models.actions.Move;
import models.actions.Rotate;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class MovementsLoaderTest {

    private final String PATH_TEST_FILES = "board_files/test/sequences_test/";

    @Test
    void loadNoSequencesResultsAnEmptyArray() {
        ArrayList<ArrayList<Action>> sequences = null;

        boolean exceptionHappened = false;
        try{
            String path = PATH_TEST_FILES + "loadNoSequencesResultsAnEmptyArray.json";
            sequences = MovementsLoader.loadSequencesOfActionsFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(!exceptionHappened);
        assert(sequences.size() == 0);
    }

    @Test
    void loadSequencesWithEmptyStringOfActionsResultsAnArrayWithOneSequenceWithoutActions() {
        ArrayList<ArrayList<Action>> sequences = null;

        boolean exceptionHappened = false;
        try{
            String path = PATH_TEST_FILES + "loadSequencesWithEmptyStringOfActionsResultsAnArrayWithOneSequenceWithoutActions.json";
            sequences = MovementsLoader.loadSequencesOfActionsFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(!exceptionHappened);
        assert(sequences.size() == 1);
        assert(sequences.get(0).size() == 0);
    }

    @Test
    void loadSequencesKeeMissingThrowsException() {
        boolean exceptionHappened = false;
        try{
            String path = PATH_TEST_FILES + "loadSequencesKeeMissingThrowsException.json";
            MovementsLoader.loadSequencesOfActionsFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void loadSequencesWrongCharForActionThrowsException() {
        boolean exceptionHappened = false;
        try{
            String path = PATH_TEST_FILES + "loadSequencesWrongCharForActionThrowsException.json";
            MovementsLoader.loadSequencesOfActionsFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(exceptionHappened);
    }

    @Test
    void loadSequencesCompleteLoadCorrectActionsInOrder() {
        ArrayList<ArrayList<Action>> sequences = null;

        boolean exceptionHappened = false;
        try{
            String path = PATH_TEST_FILES + "loadSequencesCompleteLoadCorrectActionsInOrder.json";
            sequences = MovementsLoader.loadSequencesOfActionsFromFile(path);
        } catch (Exception e) {
            exceptionHappened = true;
        }
        assert(!exceptionHappened);
        assert(sequences.size() == 1);

        ArrayList<Action> actions = sequences.get(0);

        assert(actions.get(0).getClass() == Move.class);
        assert(actions.get(1).getClass() == Rotate.class);
        assert(actions.get(2).getClass() == Rotate.class);
        assert(actions.get(3).getClass() == Rotate.class);
        assert(actions.get(4).getClass() == Move.class);
        assert(actions.get(5).getClass() == Move.class);
        assert(actions.get(6).getClass() == Move.class);
        assert(actions.get(7).getClass() == Rotate.class);
        assert(actions.get(8).getClass() == Rotate.class);
        assert(actions.get(9).getClass() == Move.class);
    }
}