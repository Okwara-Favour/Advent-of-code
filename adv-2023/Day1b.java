import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1b {
    
    public static void decrypt_alphaNumeric_code(String line, ArrayList<Character> array) {
        String[] wordNum_array = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        char[] num_array = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        int[] countNum_array = {3, 3, 5, 4, 4, 3, 5, 5, 4};
        int[] trackNum_array = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        for (int i = 0; i < line.length(); i++) {
            for(int j = 0; j < wordNum_array.length; j++) {
                if(wordNum_array[j].charAt(trackNum_array[j]) == line.charAt(i)) {
                    trackNum_array[j] += 1;
                }
                else if(line.charAt(i) == wordNum_array[j].charAt(0)) {
                    trackNum_array[j] = 1;
                }
                else {
                    trackNum_array[j] = 0;
                }

                if(trackNum_array[j] >= countNum_array[j]) {
                    array.add(num_array[j]);
                    trackNum_array[j] = 0;
                }
            }
            if(Character.isDigit(line.charAt(i))) {
                array.add(line.charAt(i));
            }
        }
    }
    public static void main(String[] args) {
        String filePath = "Bigtest.txt";

        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
          //  int x = 1;
            while ((line = reader.readLine()) != null) {
                ArrayList<Character> array = new ArrayList<>();
                decrypt_alphaNumeric_code(line, array);

                int value = Integer.parseInt(array.get(0) + "" + array.get(array.size()-1));
                sum += value;
                //System.out.println(x + " " + array);
                //x++;
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
