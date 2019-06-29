package models.loaders;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;
import models.actions.Move;
import models.actions.Rotate;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import models.actions.Action;

public final class MovementsLoader {

    private static final Map<Character, Action> ACTIONS_MAP;
    static {
        Map<Character, Action> aMap = new HashMap<Character, Action>();
        aMap.put('M', new Move());
        aMap.put('R', new Rotate());
        ACTIONS_MAP = Collections.unmodifiableMap(aMap);
    }

    private MovementsLoader() {}

    private static ArrayList<ArrayList<Action>> loadSequencesOfActionsFromJSON(JSONArray sequences) throws Exception {
        ArrayList<ArrayList<Action>> listOfSequences = new ArrayList<ArrayList<Action>>();
        for(int i=0; i<sequences.size(); i++) {
            ArrayList<Action> currentSequence = new ArrayList<Action>();
            JSONObject sequence = (JSONObject) sequences.get(i);
            String actions = ((String) sequence.get("actions")).toUpperCase();
            for (char charAction: actions.toCharArray()) {
                if (ACTIONS_MAP.containsKey(charAction)) {
                    currentSequence.add(ACTIONS_MAP.get(charAction));
                } else {
                    throw new ValueException("The action " + charAction + " is invalid");
                }

            }
            listOfSequences.add(currentSequence);
        }
        return listOfSequences;
    }

    public static ArrayList<ArrayList<Action>> loadSequencesOfActionsFromFile(String path) throws Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(path));
        JSONObject jsonObject = (JSONObject) obj;

        JSONArray sequences = (JSONArray) jsonObject.get("sequences");
        return loadSequencesOfActionsFromJSON(sequences);
    }
}
