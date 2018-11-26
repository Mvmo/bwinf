package de.mvmo.bwinf.twist;

import de.mvmo.bwinf.twist.crypt.Decryptor;
import de.mvmo.bwinf.twist.crypt.Encryptor;
import de.mvmo.bwinf.twist.misc.Dictionary;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Main {

    private static Dictionary dictionary;

    private static Encryptor encryptor;
    private static Decryptor decryptor;

    private static Function<String, String> cryptingMethod;
    private static Scanner fileScanner;

    static {
        dictionary = new Dictionary();

        encryptor = new Encryptor(dictionary);
        decryptor = new Decryptor(dictionary);
    }

    public static void main(String[] args) throws IOException {
        preLoad(args);

        dictionary.load(new Scanner(new URL("https://git.bwinf.de/bwinf/bwinf37-runde1/raw/master/a2-Twist/beispieldaten/woerterliste.txt").openStream()));
        StringBuilder resultBuilder = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            System.out.println("LOL");
            for (String str : split(fileScanner.nextLine())) {
                System.out.println("LOL");
                if (!str.isEmpty() && Character.isAlphabetic(str.charAt(0)))
                    resultBuilder.append(cryptingMethod.apply(str));
                else
                    resultBuilder.append(str);
            }

            resultBuilder.append("\n");
        }

        System.out.println(resultBuilder.toString());
    }

    private static void preLoad(String[] args) throws IOException {
        if (args.length < 2)
            exitWrongArguments();

        switch (args[0].toLowerCase()) {
            case "encrypt":
                cryptingMethod = encryptor::encrypt;
                break;
            case "decrypt":
                cryptingMethod = decryptor::decrypt;
                break;
            default:
                exitWrongArguments();
        }

        if (args[1].startsWith("https://"))
            fileScanner = new Scanner(new URL(args[1]).openStream());
        else if (args[1].endsWith(".txt"))
            fileScanner = new Scanner(new File(args[1]));
        else
            exitWrongArguments();
    }

    private static void exitWrongArguments(String... extraMessages) {
        Stream.of(extraMessages).forEach(System.out::println);

        System.out.println("java -jar " + new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() + ".jar [encrypt/decrypt] [url/path]");
        System.exit(-1);
    }

    private static String[] split(String str) {
        String[] splitted = new String[0];

        int pointerA = 0;
        int pointerB = 0;

        while (pointerB < str.length()) {
            while (pointerB < str.length() && Character.isAlphabetic(str.charAt(pointerA)) == Character.isAlphabetic(str.charAt(pointerB)))
                pointerB++;

            splitted = Arrays.copyOf(splitted, splitted.length + 1);
            splitted[splitted.length - 1] = str.substring(pointerA, pointerB);

            pointerA = pointerB++;
        }

        splitted = Arrays.copyOf(splitted, splitted.length + 1);
        splitted[splitted.length - 1] = str.substring(pointerA);

        return splitted;
    }

}
