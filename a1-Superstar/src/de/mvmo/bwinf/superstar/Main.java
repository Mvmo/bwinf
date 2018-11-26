package de.mvmo.bwinf.superstar;

import de.mvmo.bwinf.superstar.util.ArrayUtils;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

/**
 * Description
 *
 * @author Maurice Schmidt (@Mvmo)
 * @since 1.0
 */
public class Main {

    private static Map<String, List<String>> data = new HashMap<>();

    private static String[] userArray;
    private static List<String> possibleStarList;
    private static Map<String, List<String>> collectedData = new HashMap<>();

    private static int price = 0;

    private static Scanner scanner;

    public static void main(String[] args) throws Exception {
        preLoad(args);
        loadUserData();

        int pointer = 0;

        while (possibleStarList.size() > 1) {
            String personA = possibleStarList.get(pointer);
            String personB = possibleStarList.get(pointer + 1);

            if (follows(personA, personB)) {
                possibleStarList.remove(personA);

                if (!collectedData.containsKey(personB))
                    collectedData.put(personB, new ArrayList<>());

                List<String> followerList = collectedData.get(personB);
                followerList.add(personA);

                collectedData.replace(personB, followerList);
            } else
                possibleStarList.remove(personB);

            pointer += 2;
            if (pointer + 1 >= possibleStarList.size())
                pointer = 0;
        }

        for (String username : userArray) {
            if (username.equals(possibleStarList.get(0)))
                continue;

            if (follows(possibleStarList.get(0), username) || !follows(username, possibleStarList.get(0))) {
                System.out.println("Es gibt keinen Superstar!");
                System.out.println("Die Anfragen haben " + price + "€ gekostet");
                System.out.print("Die Gruppe hat " + userArray.length + " Nutzer");
                System.exit(-1);
            }

        }

        System.out.println(possibleStarList.get(0) + " ist der Superstar");
        System.out.println("Die Anfragen haben " + price + "€ gekostet");
        System.out.print("Die Gruppe hat " + userArray.length + " Nutzer");

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
    }

    private static void loadUserData() {
        userArray = scanner.nextLine().split(" ");
        possibleStarList = new ArrayList<>(Arrays.asList(userArray));

        while (scanner.hasNextLine()) {
            String[] splittedLine = scanner.nextLine().split(" ");

            if (!data.containsKey(splittedLine[0]))
                data.put(splittedLine[0], new ArrayList<>());

            List<String> followerList = data.get(splittedLine[0]);
            followerList.add(splittedLine[1]);

            data.replace(splittedLine[0], followerList);
        }
    }

    private static boolean follows(String a, String b) {
        if (collectedData.containsKey(b) && collectedData.get(b).contains(a))
            return true;
        if (!data.containsKey(a))
            data.put(a, new ArrayList<>());

        price++;
        return data.get(a).contains(b);
    }

}
