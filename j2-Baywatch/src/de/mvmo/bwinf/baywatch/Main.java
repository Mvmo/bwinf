package de.mvmo.bwinf.baywatch;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Main {

    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        preLoad(args);

        String encryptedUnsorted = scanner.nextLine().replace(" ", "");
        String crypted = scanner.nextLine().replace(" ", "");

        if (crypted.replace("?", "").length() == 0) {
            System.out.println("Diese Aufgabe ist nicht möglich!");
            System.exit(-1);
        }

        long startTime = System.nanoTime();

        Map<Integer, Integer> rotations = new HashMap<>();
        int charCounter = 0;

        int length = crypted.length();
        for (int i = 0; i < length; i++) {
            char c = crypted.charAt(i);
            if (c == '?')
                continue;

            charCounter++;
            for (int j = 0; j < length; j++) {
                char ec = encryptedUnsorted.charAt(j);
                if (ec != c)
                    continue;

                int rotation = posMod(j - i, length);
                if (rotations.containsKey(rotation)) {
                    int amount = rotations.get(rotation);
                    if ((charCounter - 1) == amount)
                        rotations.put(rotation, amount + 1);
                    else
                        rotations.remove(rotation);
                } else if (charCounter == 1)
                    rotations.put(rotation, 1);
            }
        }

        int rotation = 0;

        for (int num : rotations.keySet()) {
            if (rotations.get(num).equals(charCounter)) {
                if (rotation == 0)
                    rotation = num;
                else {
                    System.out.println("Ergbenis nicht Eindeutig");
                    System.exit(-1);
                }
            }
        }

        String newString = encryptedUnsorted.substring(rotation);
        newString += encryptedUnsorted.substring(0, rotation);

        long endTime = System.nanoTime();
        long time = endTime - startTime;
        double timePerChar = (double) time / (double) length;

        System.out.println("Zeit                    : " + time + " ns");
        System.out.println("In ms                   : " + (time * Math.pow(10, -6)) + " ms");
        System.out.println("Zeit pro Buchstabe      : " + timePerChar + " ns");
        System.out.println("Verschlüsselte Rotation : Richtung = '->': " + rotation);
        System.out.println("Gleiche wie             : Richtung = '<-': " + (length - rotation));
        System.out.println(newString);
        System.out.println(crypted);
    }

    private static int posMod(int num, int mod) {
        num %= mod;

        return num >= 0 ? num : num + mod;
    }

    private static void preLoad(String[] args) throws IOException {
        if (args.length < 1)
            exitWrongArguments();

        if (args[0].startsWith("https://"))
            scanner = new Scanner(new java.net.URL(args[0]).openStream());
        else if (args[0].endsWith(".txt"))
            scanner = new Scanner(new File(args[0]));
        else
            exitWrongArguments();
    }

    private static void exitWrongArguments(String... extraMessages) {
        Stream.of(extraMessages).forEach(System.out::println);

        System.out.println("java -jar " + new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() + ".jar [url/path]");
        System.exit(-1);
    }

}
