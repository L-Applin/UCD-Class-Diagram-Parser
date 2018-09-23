package utils;

public class Utils {

    public static class Log {

        public static void all(String... logs) {
        /*
        StringBuilder sb = new StringBuilder();
        for (String log : logs){
            sb.append(log);
            sb.append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        */
        }


        public static void test(String... logs) {
            StringBuilder sb = new StringBuilder();
            for (String log : logs) {
                sb.append(log);
                sb.append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(sb.toString());

        }
    }

}
