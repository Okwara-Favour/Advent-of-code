import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8a {
    static class Direction {
        Dictionary<String, ArrayList<String>> node;
        String n;

        public Direction() {
            node = new Hashtable<>();
        }

        public void setLeftRight(String l, String r) {
            this.node.get(this.n).add(l);
            this.node.get(this.n).add(r);
        }

        public void setNode(String n) {
            this.node.put(n, new ArrayList<>());
            this.n = n;
        }

        public String getLeft(String n) {
            return this.node.get(n).get(0);
        }

        public String getRight(String n) {
            return this.node.get(n).get(1);
        }

        public Dictionary<String, ArrayList<String>> getNode() {
            return this.node;
        }
    }

    public static int countSteps(String start, Direction dir, String sequence) {
        String end = start;

        int x = 0;
        int count = 0;
        while(!end.equals("ZZZ")) {
            count++;
            if(x > sequence.length()-1) {
                x = 0;
            }
            if(sequence.charAt(x) == 'L') {
                end = dir.getLeft(end);
            }
            if(sequence.charAt(x) == 'R') {
                end = dir.getRight(end);
            }
            x++;
        }
        return count;
    }

    public static void qualityCheck(String s) {
        // Specify the pattern (checking if the string contains only 'A' or 'B' and no other characters)
        Pattern pattern = Pattern.compile("^[LR]+$");

        // Create a matcher
        Matcher matcher = pattern.matcher(s);

        // Check if the string matches the pattern
        if (!matcher.matches()) {
            System.out.println(s);
            System.out.println("String does not meet the specified criteria.");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        String filePath = "Smalltest8.txt";

        Direction dir = new Direction();
        String sequence = "";
        int sum = 0;
        int x = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                if(x == 0) {
                    qualityCheck(line);
                    sequence = line;
                }

                String[] word_split = {};
                word_split = line.split(" +=");
                String[] node = {};
                if(word_split.length == 2) {
                    node = word_split[1].trim().split(" +");

                    dir.setNode(word_split[0]);
                    dir.setLeftRight(node[0].replaceAll("[^a-zA-Z0-9]", ""), node[1].replaceAll("[^a-zA-Z0-9]", ""));
                }
                x++;
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(dir.getNode());
        System.out.println(countSteps("AAA", dir, sequence));
    }
}
