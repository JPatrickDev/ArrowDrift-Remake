package com.jdp30.ArrowDrift.game.util;

import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import com.jdp30.ArrowDrift.game.Level.Level;
import storage.Node;

/**
 * Created by Jack on 02/05/2018.
 */
public class LevelUtil {

    public static void updateMoves(String levelName, String category, int moves) {
        Node userdata = ArrowDriftGame.userdata.getRoot();
        if (!userdata.hasChild("levels")) {
            Node n = new Node("levels");
            userdata.addChild(n);
        }
        Node levels = userdata.getChild("levels");
        if (!levels.hasChild(category)) {
            levels.addChild(new Node(category));
        }
        Node cat = levels.getChild(category);
        if (!cat.hasChild(levelName)) {
            cat.addChild(new Node(levelName));
        }
        Node level = cat.getChild(levelName);
        level.addValue("moves", moves + "");
        ArrowDriftGame.notifyStorageChanged();
    }

    public static int getMovesTaken(String levelName, String category) {
        Node userdata = ArrowDriftGame.userdata.getRoot();
        if (!userdata.hasChild("levels")) {
            return -1;
        }
        Node levels = userdata.getChild("levels");
        if (!levels.hasChild(category)) {
            return -1;
        }
        Node cat = levels.getChild(category);
        if (!cat.hasChild(levelName)) {
            return -1;
        }
        Node level = cat.getChild(levelName);
        if (level.hasKey("moves")) {
           return Integer.parseInt(level.getValue("moves"));
        }
        return -1;
    }

    public static int getMinMoves(String levelName,String category){
        return Level.fromNode(ArrowDriftGame.getCurrentPack().getRoot().getChild(category).getChild(levelName)).getMinMoves();
    }
}
