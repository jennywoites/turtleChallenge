import java.io.*;

import com.sun.media.sound.InvalidFormatException;
import models.TurtleGame;

public class TurtleChallenge {

    public static final int COUNT_FILES = 2;

    public static final int POS_FILE_BOARD = 0;
    public static final int POS_FILE_SEQUENCES = 1;

    private static boolean inputFilesAreValid(String[] args) {
        if (args.length != COUNT_FILES) {
            System.out.println("You need to provide the board file and the sequences of movements files, in that order.");
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            File f = new File(args[i]);
            if (!f.exists() || f.isDirectory()) {
                System.out.printf("The file %s does not exist%n", args[i]);
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        if (!inputFilesAreValid(args))
            return;

        try {
            TurtleGame game = new TurtleGame(args[POS_FILE_BOARD], args[POS_FILE_SEQUENCES]);
            game.startGame();
        } catch (InvalidFormatException except) {
            System.out.println(except.getMessage());
        }
    }
}