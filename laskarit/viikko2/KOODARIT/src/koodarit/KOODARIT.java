package koodarit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class KOODARIT {

    public static void main(String[] args) {
        start();

    }

    private static void start() {
        String input = "5 5\n"
                + "0 2\n"
                + "F 1 T 3 F 2 T 1 F 1 T 1 F 4 T 3 F 1 T 3 F 2 T 1 F 1\n"
                + "1.0\n"
                + "2\n"
                + "2\n"
                + "0\n"
                + "5\n"
                + "9 0\n"
                + "2 0\n"
                + "9 1\n"
                + "12 0\n"
                + "10 1";

        String[] rows = input.split("\n");
        int[] board = stringArrayToIntArray(rows[0].split(" "));
        int[] pos = stringArrayToIntArray(rows[1].split(" "));
        String[] commands = rows[2].split(" ");
        double speed = Double.parseDouble(rows[3]);
        int aliens = Integer.parseInt(rows[4]);
        int[] alienSpawningTimes = new int[aliens];
        for (int i = 0; i < alienSpawningTimes.length; i++) {
            alienSpawningTimes[i] = Integer.parseInt(rows[5 + i]);
        }
        int amountOfQueries = Integer.parseInt(rows[5 + aliens]);
        String[] queries = new String[amountOfQueries];
        for (int i = 0; i < queries.length; i++) {
            queries[i] = rows[6 + aliens + i];
        }
        int deg = 90;
        ArrayList<String> route = new ArrayList<>();

        for (int i = 0; i < commands.length; i += 2) {
            int[] oldpos = pos.clone();

            String command = commands[i];
            int amount = Integer.parseInt(commands[i + 1]);
            if (command.equals("F")) {
                switch (deg) {
                    case 0:
                        break;
                    case 90:
                        break;
                    case 180:
                        break;
                    case 270:
                        break;
                    default:
                        break;
                }
            } else {
                deg += (amount * 90) % 360;
                if (deg >= 360) {
                    deg = 0;
                }
                System.out.println(deg);
            }

        }
    }

    private static int[] stringArrayToIntArray(String[] stringarray) {
        int[] intarray = new int[stringarray.length];

        for (int i = 0; i < stringarray.length; i++) {
            intarray[i] = Integer.parseInt(stringarray[i]);
        }

        return intarray;
    }

}
