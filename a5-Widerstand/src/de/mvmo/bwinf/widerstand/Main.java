package de.mvmo.bwinf.widerstand;

import de.mvmo.bwinf.widerstand.resistance.IResistance;
import de.mvmo.bwinf.widerstand.resistance.MultipleResistance;
import de.mvmo.bwinf.widerstand.resistance.SingleResistance;
import de.mvmo.bwinf.widerstand.resistance.connection.ConnectionType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static IResistance best;
    private static double searchedResistance;
    private static int k;

    private static List<SingleResistance> singleResistors = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        preLoad(args);

        loop:
        for (SingleResistance w : singleResistors) {
            if (k == 1) {
                calculate(w);
                continue;
            }
            for (SingleResistance x : singleResistors) {
                if (w == x)
                    continue;

                if (k == 2) {
                    calculate(w, x);
                    continue loop;
                }

                for (SingleResistance y : singleResistors) {
                    if (w == y || x == y)
                        continue;

                    if (k == 3) {
                        calculate(w, x, y);
                        continue loop;
                    }

                    for (SingleResistance z : singleResistors) {
                        if (w == z || x == z || y == z)
                            continue;
                        calculate(w, x, y, z);
                    }
                }
            }
        }

        System.out.println(best.getString());
        System.out.println();
        System.out.println("Gesuchter Widerstand   : " + searchedResistance);
        System.out.println("Bester Wert            : " + best.getValue());
        System.out.println("Unterschied            : " + getDistance(best));
        System.out.println("Anzahl an Widerständen : " + k);
    }

    private static void calculate(SingleResistance... resistors) {
        List<SingleResistance> resistorsList = Arrays.asList(resistors);

        SingleResistance resistance = resistorsList.get(0);
        checkDistance(resistance);

        permutate(resistorsList, resistance, 1);
    }

    private static void permutate(List<SingleResistance> singleResistors, IResistance last, int i) {
        if (i >= singleResistors.size())
            return;

        SingleResistance singleResistance = singleResistors.get(i);
        // Serial
        MultipleResistance serial = new MultipleResistance(last, singleResistance, ConnectionType.SERIAL);
        checkDistance(serial);
        permutate(singleResistors, serial, i + 1);
        // Parallel
        MultipleResistance parallel = new MultipleResistance(last, singleResistance, ConnectionType.PARALLEL);
        checkDistance(parallel);
        permutate(singleResistors, parallel, i + 1);
    }

    private static void checkDistance(IResistance resistance) {
        if (best == null || getDistance(resistance) < getDistance(best))
            best = resistance;
    }

    private static double getDistance(IResistance resi) {
        return Math.abs(resi.getValue() - searchedResistance);
    }

    private static void preLoad(String[] args) throws IOException {
        if (args.length < 1)
            exitWrongArguments();

        Scanner scanner = null;
        if (args[0].startsWith("https://"))
            scanner = new Scanner(new java.net.URL(args[0]).openStream());
        else if (args[0].endsWith(".txt"))
            scanner = new Scanner(new File(args[0]));
        else
            exitWrongArguments();

        while (scanner.hasNextInt())
            singleResistors.add(new SingleResistance(scanner.nextInt()));

        Scanner consoleScanner = new Scanner(System.in);

        System.out.print("Gebe eine den gewünschten Widerstand an: ");
        searchedResistance = consoleScanner.nextInt();

        System.out.print("Wie viele Widerstände möchtest du nutzen?: ");
        boolean valueOk = false;
        while (!valueOk) {
            k = consoleScanner.nextInt();
            if (k > 4 || k < 1) {
                System.out.println("Bitte gebe eine Zahl zwischen 1 und 4 an");
                continue;
            }

            valueOk = true;
        }
    }

    private static void exitWrongArguments(String... extraMessages) {
        Stream.of(extraMessages).forEach(System.out::println);

        System.out.println("java -jar " + new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName() + ".jar [url/path]");
        System.exit(-1);
    }

}
