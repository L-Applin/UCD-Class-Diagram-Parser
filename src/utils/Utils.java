package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static class Log {

        public static final boolean logTest = false;
        public static final boolean logAll = false;


        public static void all(String... logs) {
            if (logAll) {
                logInput(logs);
            }
        }

        public static void test(String... logs) {
            if (logTest) {
                logInput(logs);
            }
        }

        private static void logInput(String... logs) {
            StringBuilder sb = new StringBuilder();
            for (String log : logs) {
                sb.append(log);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString());
        }

    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> concatArraysToList(T[] first, T[] second) {
        List<T> myList = new ArrayList<>(Arrays.asList(first));
        myList.addAll(Arrays.asList(second));
        return myList;
    }

    public static boolean containsAny(String txt, List<String> illegal) {
        for (String c : illegal) {
            if (txt.contains(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAny(String txt, String... illegal) {
        for (String c : illegal) {
            if (txt.contains(c)) {
                return true;
            }
        }
        return false;
    }


}
