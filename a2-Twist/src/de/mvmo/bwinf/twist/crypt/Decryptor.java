package de.mvmo.bwinf.twist.crypt;

import de.mvmo.bwinf.twist.misc.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Decryptor {

    private Dictionary dictionary;

    public Decryptor(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String decrypt(String str) {
        if (!dictionary.canFind(str))
            return str;

        List<String> possibleWords = dictionary.getPossibleWords(str);
        Map<Character, Integer> givenStringCharCounterMap = getCharCounterMap(str);

        possibleWordsLoop:
        for (String word : possibleWords) {
            Map<Character, Integer> wordCharCounterMap = getCharCounterMap(word);

            for (Character c : wordCharCounterMap.keySet())
                if (!wordCharCounterMap.get(c).equals(givenStringCharCounterMap.get(c)))
                    continue possibleWordsLoop;

            return word;
        }

        return str;
    }

    public Map<Character, Integer> getCharCounterMap(String str) {
        Map<Character, Integer> charCounterMap = new HashMap<>();

        for (char c : str.toCharArray()) {
            if (charCounterMap.containsKey(c))
                charCounterMap.replace(c, charCounterMap.get(c) + 1);
            else
                charCounterMap.put(c, 1);
        }

        return charCounterMap;
    }

}
