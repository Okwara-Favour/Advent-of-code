import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;

public class Day4a {

    public static int countMatchNum(String[] array1, String[] array2) {
        int count = 0;
        for(int i=0; i<array1.length; i++) {
            if(i == 0) {
                continue;
            }
            for(int j=0; j<array2.length; j++) {
                if(j == 0) {
                    continue;
                }
                if(array2[j].equals(array1[i])) {
                    count += 1;
                }
            }
        }
        return count;
    }
    public static void main(String[] args) {
        String filePath = "Bigtest4.txt";

        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                //ArrayList<Character> array = new ArrayList<>();
                String[] word_split = line.split(":");
                try {
                    String[] rounds = word_split[1].split("\\|");
                    String[] win_num = rounds[0].split(" +");
                    String[] can_num = rounds[1].split(" +");

                    int val = countMatchNum(win_num, can_num) - 1;
                    int result = 0;
                    if(val >= 0) {
                        result = (int)Math.pow(2, val);
                    }
                    else {
                        result = 0;
                    }
                    sum += result;
                }
                catch(Exception e) {
                    System.out.println(e);
                }

                //int value = Integer.parseInt(array.get(0) + "" + array.get(array.size()-1));
                //sum += value;
                //System.out.println(line);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
