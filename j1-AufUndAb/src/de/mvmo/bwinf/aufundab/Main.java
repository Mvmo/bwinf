package de.mvmo.bwinf.aufundab;

import de.mvmo.bwinf.aufundab.misc.Ladder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Main {

    private static List<Ladder> ladderList = new ArrayList<>();
    private static final int GOAL = 100;

    static {
        ladderList.add(new Ladder(6, 27));
        ladderList.add(new Ladder(14, 19));
        ladderList.add(new Ladder(21, 53));
        ladderList.add(new Ladder(31, 42));
        ladderList.add(new Ladder(33, 38));
        ladderList.add(new Ladder(46, 62));
        ladderList.add(new Ladder(51, 59));
        ladderList.add(new Ladder(57, 96));
        ladderList.add(new Ladder(65, 85));
        ladderList.add(new Ladder(68, 80));
        ladderList.add(new Ladder(70, 76));
        ladderList.add(new Ladder(92, 98));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int diceRoll = 0;

        try {
            diceRoll = Integer.valueOf(scanner.nextLine());
        } catch (ClassCastException exception) {
            System.out.println("Du musst ja wohl");
            System.exit(-1);
        }

        if (diceRoll > 6 || diceRoll < 1) {
            System.out.println("Bitte gebe eine Zahl zwischen 1 und 6 ein");
            System.exit(-1);
        }

        check(diceRoll);
    }

    private static void check(int diceRoll) {
        int pointerA = 0;
        int pointerB = 0;

        int steps = 0;

        whileLoop:
        while (true) {
            for (int i = 0; i < 2; i++) {
                pointerA = getNextField(pointerA, diceRoll);
                steps++;

                if (pointerA == pointerB || pointerA == GOAL)
                    break whileLoop;
            }

            pointerB = getNextField(pointerB, diceRoll);

            if (pointerA == pointerB)
                break;
        }

        if (pointerA != GOAL)
            System.out.println("Geht nicht");
        else
            System.out.println("Braucht " + steps + " Schritte");
    }

    private static int getNextField(int currentField, int diceRoll) {
        int newField = currentField + diceRoll;

        if (newField > GOAL)
            newField = GOAL - (newField - GOAL);

        for (Ladder ladder : ladderList)
            if (ladder.hasField(newField))
                newField = ladder.getField(newField);

        return newField;
    }

}
