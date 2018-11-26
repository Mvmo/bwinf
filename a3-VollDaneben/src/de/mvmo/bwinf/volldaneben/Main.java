package de.mvmo.bwinf.volldaneben;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Main {

    private static final int MIN_VAL = 1;
    private static final int MAX_VAL = 1000;
    private static final int PLAY_USE = 25;
    private static final int AMOUNT = 10;
    private static final int FACTOR = 50;

    private static List<Integer> numberList = new ArrayList<>();
    private static List<Integer> alsNumberList = new ArrayList<>();

    private static List<Integer> steps = new ArrayList<>();

    private static Scanner scanner;

    public static void main(String[] args) throws IOException {
        preLoad(args);

        while (scanner.hasNextInt())
            numberList.add(scanner.nextInt());

        for (int i = 0; i < AMOUNT; i++) {
            alsNumberList.add(MIN_VAL);
            steps.add((i + 1) * FACTOR);
        }

        Integer currentWin = calcWin(alsNumberList);

        List<Integer> tempList;
        Integer tempWin;

        whileLoop:
        while (true) {
            boolean moved = false;

            for (int i = AMOUNT - 1; i >= 0; i--) {
                if (alsNumberList.get(i) + steps.get(i) > MAX_VAL || alsNumberList.get(i) + steps.get(i) < MIN_VAL)
                    continue;

                tempList = exchangeNumberAtIndex(alsNumberList, alsNumberList.get(i) + steps.get(i), i);
                tempWin = calcWin(tempList);

                if (tempWin > currentWin) {
                    alsNumberList = tempList;
                    currentWin = tempWin;
                    moved = true;
                }
            }

            if (!moved) {
                if (steps.get(AMOUNT - 1).equals((-AMOUNT - 1) * FACTOR))
                    break whileLoop;

                for (int i = AMOUNT - 1; i >= 0; i--) {
                    int newVal = steps.get(i) - 1;
                    newVal = newVal == 0 ? -1 : newVal;
                    steps.set(i, newVal);
                }
            } else
                for (int i = 0; i < AMOUNT; i++)
                    steps.set(i, (i + 1) * FACTOR);
        }

        System.out.println("Al macht einen Gewinn von " + currentWin + "€");
        System.out.println("Zahlen: " + alsNumberList);
    }

    private static List<Integer> exchangeNumberAtIndex(List<Integer> oldList, Integer number, int index) {
        List<Integer> copy = new ArrayList<>();
        for (int i = 0; i < oldList.size(); i++) {
            if (i == index) {
                copy.add(number);
            } else {
                copy.add(oldList.get(i));
            }
        }
        return copy;
    }

    private static int calcWin(List<Integer> potAlNumbers) {
        int use = 0;
        int loss = 0;
        for (Integer player : numberList) {
            use += PLAY_USE;
            Integer minDistance = null;
            for (Integer alNumber : potAlNumbers) {
                Integer distance = Math.abs(alNumber - player);
                if (minDistance == null || distance < minDistance) {
                    minDistance = distance;
                }
            }
            loss += minDistance;
        }
        int win = use - loss;
        return win;
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
