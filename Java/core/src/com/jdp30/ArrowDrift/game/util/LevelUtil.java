package com.jdp30.ArrowDrift.game.util;

import com.jdp30.ArrowDrift.game.ArrowDriftGame;
import storage.Node;

/**
 * Created by Jack on 02/05/2018.
 */
public class LevelUtil {

    public static void updateMoves(String levelName,String category, int moves){
        Node userdata = ArrowDriftGame.userdata.getRoot();
        if(!userdata.hasChild("levels")){
            Node n = new Node("levels");
            userdata.addChild(n);
        }
        Node levels = userdata.getChild("levels");
        if(!levels.hasChild(category)){
            levels.addChild(new Node(category));
        }
        Node cat = levels.getChild(category);
        if(!cat.hasChild(levelName)){
            cat.addChild(new Node(levelName));
        }
        Node level = cat.getChild(levelName);
        level.addValue("moves",moves + "");
        ArrowDriftGame.notifyStorageChanged();
    }
}
