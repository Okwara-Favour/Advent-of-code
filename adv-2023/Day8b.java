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

public class Day8b {
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
    
    //Use at your own risk, its okay, just takes a lifetime to run
    public static int countSteps(ArrayList<String> start, Direction dir, String sequence) {
        ArrayList<String> end = start;

        int x = 0;
        int count = 0;
        while(!allEndWith(end, 'Z')) {
            System.out.println(end);
            count++;
            if(x > sequence.length()-1) {
                x = 0;
            }
            if(sequence.charAt(x) == 'L') {
                for(int i = 0; i < end.size(); i++) {
                    end.set(i, dir.getLeft(end.get(i)));
                }
            }
            if(sequence.charAt(x) == 'R') {
                for(int i = 0; i < end.size(); i++) {
                    end.set(i, dir.getRight(end.get(i)));
                }
            }
            x++;
        }
        return count;
    }

    //solution
    public static int countSingleSteps(String start, Direction dir, String sequence) {
        String end = start;

        int x = 0;
        int count = 0;
        while(end.charAt(2) != 'Z') {
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

    public static boolean allEndWith (ArrayList<String> array, char c) {
        for(int i=0; i<array.size(); i++) {
            if(array.get(i).charAt(2) != c) {
                return false;
            }
        }
        return true;
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to calculate LCM (Least Common Multiple)
    private static long lcm(long a, long b) {
        return (a * b) / gcd(a, b);
    }

    // Function to calculate LCM of an array of numbers
    public static long calculateLCM(int[] numbers) {
        long result = 1;
        for (int number : numbers) {
            result = lcm(result, number);
        }
        return result;
    }
    public static void main(String[] args) {
        String filePath = "Bigtest8.txt";

        Direction dir = new Direction();
        String sequence = "";
        int sum = 0;
        int x = 0;
        ArrayList<String> endWithA = new ArrayList<>();
        ArrayList<String> endWithZ = new ArrayList<>();

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

                    if(word_split[0].charAt(2) == 'A') {
                        endWithA.add(word_split[0]);
                    }
                    if(word_split[0].charAt(2) == 'Z') {
                        endWithZ.add(word_split[0]);
                    }

                    dir.setNode(word_split[0]);
                    dir.setLeftRight(node[0].replaceAll("[^a-zA-Z0-9]", ""), node[1].replaceAll("[^a-zA-Z0-9]", ""));
                }
                x++;
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        //System.out.println(dir.getNode());
        int[] array = new int[endWithA.size()];

        for(int i=0; i<endWithA.size(); i++) {
            array[i] = countSingleSteps(endWithA.get(i), dir, sequence);
        }
        System.out.println(calculateLCM(array));

    }
}
