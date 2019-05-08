package ru.kachkovsky.utils;

import java.nio.CharBuffer;

public class StringUtils {

    public static String spaces(int spaces, char toReplace) {
        return CharBuffer.allocate(spaces).toString().replace('\0', toReplace);
    }
}
