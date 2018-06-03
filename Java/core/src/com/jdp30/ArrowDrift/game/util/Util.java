package com.jdp30.ArrowDrift.game.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jack Patrick on 02/06/2018.
 * <p>
 * Last Edit: 02/06/2018
 */
public class Util {
    public static String[] reverse(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray(new String[0]);
    }
}
