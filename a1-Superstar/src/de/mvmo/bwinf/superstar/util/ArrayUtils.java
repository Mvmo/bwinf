package de.mvmo.bwinf.superstar.util;

import java.util.Arrays;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class ArrayUtils {

    public static <T> T[] append(T[] array, T valueToAppend) {
        array = Arrays.copyOf(array, array.length + 1);
        array[array.length - 1] = valueToAppend;

        return array;
    }

    public static <T> boolean contains(T[] array, T toCheck) {
        for (T t : array)
            if (t != null && t.equals(toCheck))
                return true;

        return false;
    }

}
