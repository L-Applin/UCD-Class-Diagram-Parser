package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {

    public static class Log {

        public static final boolean logTest = false;
        public static final boolean logAll = false;


        /**
         * used to log infos only if the "logAll" level is set to true
         * @param logs info to be printed to the console
         */
        public static void all(String... logs) {
            if (logAll) {
                logInput(logs);
            }
        }


        /**
         * used to log infos only if the "logTest" level is set to true
         * @param logs info to be printed to the console
         */
        public static void test(String... logs) {
            if (logTest) {
                logInput(logs);
            }
        }


        /**
         * Always log infos to the console
         * @param logs info to be printed to the console
         */
        public static void log(String... logs) {
            logInput(logs);
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

    /**
     * takes two array and join them into a new List
     * @param first first array to concatenate
     * @param second second array to concatenate
     * @param <T> the type of Elements of the List and Array
     * @return the new List of all elements.
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> concatArraysToList(T[] first, T[] second) {
        List<T> myList = new ArrayList<>(Arrays.asList(first));
        myList.addAll(Arrays.asList(second));
        return myList;
    }

    /**
     * Utility method to check if a string is contained within a List
     * @param txt to check
     * @param illegal elements that can't be within txt
     * @return if txt contains any element in illegal
     */
    public static boolean containsAny(String txt, List<String> illegal) {
        for (String c : illegal) {
            if (txt.contains(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utility method to check if a string is contained within a List
     * @param txt to check
     * @param illegal elements that can't be within txt
     * @return if txt contains any element in illegal
     */
    public static boolean containsAny(String txt, String... illegal) {
        for (String c : illegal) {
            if (txt.contains(c)) {
                return true;
            }
        }
        return false;
    }


}
