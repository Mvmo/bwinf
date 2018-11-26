package de.mvmo.bwinf.twist.misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Dictionary {

    private HashMap<String, List<String>> data;

    public Dictionary() {
        this.data = new HashMap<>();
    }

    public void load(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String key = getKey(line);

            if (!data.containsKey(key))
                data.put(key, new ArrayList<>());

            data.get(key).add(line);
        }
    }

    public List<String> getPossibleWords(String str) {
        if (!canFind(str))
            return null;

        return data.get(getKey(str));
    }

    public boolean canFind(String str) {
        return data.containsKey(getKey(str));
    }

    private String getKey(String str) {
        return str.charAt(0) + str.charAt(str.length() - 1) + String.valueOf(str.length());
    }

}
