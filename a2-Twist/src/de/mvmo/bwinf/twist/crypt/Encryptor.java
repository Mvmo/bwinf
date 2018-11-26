package de.mvmo.bwinf.twist.crypt;

import de.mvmo.bwinf.twist.Constants;
import de.mvmo.bwinf.twist.misc.Dictionary;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Encryptor {

    private Dictionary dictionary;

    public Encryptor(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String encrypt(String str) {
        if (str.length() < 4)
            return str;
        String stringWithoutFirstAndLastChar = str.substring(1, str.length() - 1);

        char[] cArr = stringWithoutFirstAndLastChar.toCharArray();

        for (int i = 0; i < cArr.length; i++) {
            char c = cArr[i];

            int randomInt = Constants.SECURE_RANDOM.nextInt(stringWithoutFirstAndLastChar.length() - 1);

            cArr[i] = cArr[randomInt];
            cArr[randomInt] = c;

            stringWithoutFirstAndLastChar = String.valueOf(cArr);
        }

        return str.charAt(0) + stringWithoutFirstAndLastChar + str.charAt(str.length() - 1);
    }

}
