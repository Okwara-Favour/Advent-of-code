import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Day10a {
    static class PathFinder {
        ArrayList<String> pipes;
        ArrayList<ArrayList<Integer>> nextPipeLoc; 
        char start;
        int finalVal;
        
        public PathFinder() {
            this.pipes = new ArrayList<>();
            this.nextPipeLoc = new ArrayList<>();
            this.start = '\0';
        }

        public void setStartChar(char c) {
            this.start = c;
        }
        public void addToPipes(String word) {
            this.pipes.add(word);
        }

        public ArrayList<String> getPipes() {
            return this.pipes;
        }

        public void resetNextPipeLoc() {
            //this.nextPipeLoc.clear();
            this.nextPipeLoc = new ArrayList<>();
        }

        public char getCurrentPipe(int x, int y) {
            return this.pipes.get(x).charAt(y);
        }

        public void setFinalVal(int val) {
            this.finalVal = val;
        }
        public boolean checkPipe(int x, int y) {
            if(x > this.getPipes().size() - 1) {
                return false;
            }
            else if(x < 0) {
                return false;
            }
            else if(y > this.pipes.get(x).length() - 1) {
                return false;
            }
            else if(y < 0) {
                return false;
            }
            else if(Character.isDigit(this.pipes.get(x).charAt(y))) {
                return false;
            }
            else if(this.pipes.get(x).charAt(y) == '.') {
                return false;
            }
            return true;
        }

        public void incPipeVal(int x, int y, int val) {
            if(this.checkPipe(x, y)) {
                //int val = Integer.parseInt(this.pipes.get(x).charAt(y) + "");
                val = val + 1;
                //this.finalVal = val;
                char c = (char)((val % 10) + '0');
                String s = this.pipes.get(x);
                char[] sArray = s.toCharArray();
                sArray[y] = c;
                s = String.valueOf(sArray);
                //String s = this.pipes.get(x).replace(this.pipes.get(x).charAt(y), c);
                this.pipes.set(x, s);
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(x);
                temp.add(y);
                this.nextPipeLoc.add(temp);
            }
        }
        public void modPipe(char c, int x, int y, int val) {
            switch(c) {
                case 'F':
                    incPipeVal(x+1, y, val);
                    incPipeVal(x, y+1, val);
                    return;
                case 'G':
                    incPipeVal(x+1, y, val);
                    incPipeVal(x, y-1, val);
                    return;
                case 'L':
                    incPipeVal(x-1, y, val);
                    incPipeVal(x, y+1, val);
                    return;
                case 'J':
                    incPipeVal(x-1, y, val);
                    incPipeVal(x, y-1, val);
                    return;
                case '-':
                    incPipeVal(x, y-1, val);
                    incPipeVal(x, y+1, val);
                    return;
                case '|':
                    incPipeVal(x+1, y, val);
                    incPipeVal(x-1, y, val);
                    return;
                case 'S':
                    String s = this.pipes.get(x).replace(this.pipes.get(x).charAt(y), '0');
                    this.pipes.set(x, s);
                    modPipe(this.start, x, y, val);
                default:
                    return;
            }
        }
    }

    public static int[] storeStartLoc(String word, int x) {
        int[] temp = new int[2];
        temp[0] = x;
        for(int i = 0; i<word.length(); i++) {
            if(word.charAt(i) == 'S') {
                temp[1] = i;
                return temp;
            }
        }
        return null;
    } 

    public static void traversePipe(PathFinder path, PathFinder history, ArrayList<ArrayList<Integer>> pathStore, int val, int visited) {
        while(pathStore.size() != 0) {
            
            ArrayList<ArrayList<Integer>> temp = new ArrayList<>();
            temp.addAll(path.nextPipeLoc);
            path.resetNextPipeLoc();
            for(int i=0; i<temp.size(); i++) {
                char n = history.getCurrentPipe(temp.get(i).get(0), temp.get(i).get(1));
                path.modPipe(n, temp.get(i).get(0), temp.get(i).get(1), val);
                visited = visited + 1;
            }
            path.setFinalVal(visited);
            val = val + 1;
            pathStore = path.nextPipeLoc;
        }
        
        //traversePipe(path, history, path.nextPipeLoc, val);
    }
    public static void main(String[] args) {
        String filePath = "Bigtest10.txt";

        PathFinder path = new PathFinder();
        PathFinder history = new PathFinder();

        int[] startloc = {};
        int sum = 0;
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String modLine = line.replace('7', 'G');
                if(storeStartLoc(modLine, index) != null) {
                    startloc = storeStartLoc(modLine, index);
                }
                
                path.addToPipes(modLine);
                history.addToPipes(modLine);
                //System.out.println(modLine);
                index++;
            }
        } catch (IOException e) {
            // Handle any exceptions that may occur during file reading
            e.printStackTrace();
        }

        //System.out.println(path.getPipes());
        path.setStartChar('F');

        path.modPipe('S', startloc[0], startloc[1], 0);
        traversePipe(path, history, path.nextPipeLoc, 1, 1);

        System.out.println(path.finalVal/2);
        
    }
}
