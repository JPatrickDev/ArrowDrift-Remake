package com.jdp30.ArrowDrift.game.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Jack Patrick on 02/06/2018.
 * <p>
 * Last Edit: 02/06/2018
 */
public class Util {

    public static final Random random = new Random();

    public static String[] reverse(String[] arr) {
        List<String> list = Arrays.asList(arr);
        Collections.reverse(list);
        return list.toArray(new String[0]);
    }

    public static String randHex(){
        int nextInt = random.nextInt(256*256*256);
        return String.format("#%06x", nextInt);
    }

}
