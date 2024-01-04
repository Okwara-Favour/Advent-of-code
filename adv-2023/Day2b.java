import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day2b {
    public static void fewestGame(int[] maxColor, String[] rounds) {
        for (int i=0; i<rounds.length; i++) {
            String[] ballsColor = rounds[i].split(",");
            for (int j = 0; j < ballsColor.length; j++) {
                String[] data = ballsColor[j].split(" ");
                int ball_amount = Integer.parseInt(data[1]);
                if  (data[2].equals("red")) {
                    if(maxColor[0] < ball_amount) {
                        maxColor[0] = ball_amount;
                    }
                }
                if  (data[2].equals("green")) {
                    if(maxColor[1] < ball_amount) {
                        maxColor[1] = ball_amount;
                    }
                }
                if  (data[2].equals("blue")) {
                    if(maxColor[2] < ball_amount) {
                        maxColor[2] = ball_amount;
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        String filePath = "Bigtest2.txt";

        int sum = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                //String test = line;
                String[] word_split = line.split(":");
                try {
                    String[] rounds = word_split[1].split(";");
                    //r, g, b
                    int[] maxColor = {1,1,1};
                    fewestGame(maxColor, rounds);
                    System.out.println(maxColor[0] + "," + maxColor[1] +","+maxColor[2]);
                    int res = maxColor[0] * maxColor[1] * maxColor[2];
                    sum += res;
                }
                catch(Exception e) {
                    System.out.println(e);
                }
                //sum += value;
                //System.out.println(array);
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        System.out.println(sum);
    }
}
