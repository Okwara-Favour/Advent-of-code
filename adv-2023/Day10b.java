import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Day10b {
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

        public void setCurrentPipe(int x, int index, char c) {
            String s = this.pipes.get(x);
            char[] sArray = s.toCharArray();
            sArray[index] = c;
            s = String.valueOf(sArray);
            this.pipes.set(x, s);
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
    }

    public static void modUnvisited(PathFinder path, PathFinder history) {
        for(int i=0; i<path.getPipes().size(); i++) {
            for(int j=0; j<path.getPipes().get(i).length(); j++) {
                if(!Character.isDigit(path.getCurrentPipe(i, j))) {
                    history.setCurrentPipe(i, j, '.');
                }
                String btn = history.getPipes().get(i).replace('G', '7');
                history.getPipes().set(i, btn);
            }
        }
    }

    public static int checkHoriWall(PathFinder history, char[] array, int i, int j, int count) {
        if(history.getCurrentPipe(i, j) == array[count]) {
            count++;
        }
        else {
            if(history.getCurrentPipe(i, j) != '-') {
                count = 0;
            }
        }
        return count;
    }

    public static int checkVertWall(PathFinder history, char[] array, int i, int j, int count) {
        if(history.getCurrentPipe(i, j) == array[count]) {
            count++;
        }
        else {
            if(history.getCurrentPipe(i, j) != '|') {
                count = 0;
            }
        }
        return count;
    }

    public static boolean checkHoriBoundary(PathFinder history, int i, int j, char c, int which) {
        if(which == 0) {
            for(int k = i; k<history.getPipes().size(); k++) {
                char cc = history.getPipes().get(k).charAt(j);
                if(cc != '|') {
                    if(cc == c) {
                        return true;
                    }
                    return false;
                }
            }
        }
        if(which == 1) {
            for(int k = i; k>=0; k--) {
                char cc = history.getPipes().get(k).charAt(j);
                if(cc != '|') {
                    if(cc == c) {
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean scanL(PathFinder history, int i, int j) {
        if(history.getPipes().get(i).charAt(j) == '0') {
            return true;
        }

        char[][] possibleWall = {{'L','7'}, {'F','J'}, {'L','J'}, {'F','7'}};
        int[] countArr = {0,0,0,0};
        int leftCount = 0;
        int rightCount = 0;
        for(int l=j; l<history.getPipes().get(i).length(); l++) {
            char c = history.getPipes().get(i).charAt(l);
            for(int z = 0; z < countArr.length; z++) {
                countArr[z] = checkHoriWall(history, possibleWall[z], i, l, countArr[z]);
            }
            
            if((c == '|')) {
                leftCount++;
            }
            for(int z = 0; z < countArr.length; z++) {
                if(countArr[z] == 2 && z<= 1) {
                    leftCount++;
                    countArr[z] = 0;
                }
                if(countArr[z] == 2 && z>= 2) {
                    leftCount+=2;
                    countArr[z] = 0;
                }
            }
        }

        char[][] possibleWall2 = {{'7','L'}, {'J','F'}, {'J','L'}, {'7','F'}};
        int[] countArr2 = {0,0,0,0};
        
        for(int l=j; l>=0; l--) {
            char c = history.getPipes().get(i).charAt(l);
            for(int z = 0; z < countArr2.length; z++) {
                countArr2[z] = checkHoriWall(history, possibleWall2[z], i, l, countArr2[z]);
            }
            
            if((c == '|')) {
                rightCount++;
            }
            for(int z = 0; z < countArr2.length; z++) {
                if(countArr2[z] == 2 && z<= 1) {
                    rightCount++;
                    countArr2[z] = 0;
                }
                if(countArr2[z] == 2 && z>= 2) {
                    rightCount+=2;
                    countArr2[z] = 0;
                }
            }
        }
        return ((leftCount%2)==1 && (rightCount%2)==1);
    }

    public static boolean scanD(PathFinder history, int i, int j) {
        if(history.getPipes().get(i).charAt(j) == '0') {
            return true;
        }
        char[][] possibleWall2 = {{'7','L'}, {'F','J'}, {'F','L'}, {'7','J'}};
        int[] countArr2 = {0,0,0,0};
        int downCount = 0;
        int upCount = 0;
        for(int k = i; k<history.getPipes().size(); k++) {
            char c = history.getPipes().get(k).charAt(j);
            for(int z = 0; z < countArr2.length; z++) {
                countArr2[z] = checkVertWall(history, possibleWall2[z], k, j, countArr2[z]);
            }
            
            if((c == '-')) {
                downCount++;
            }
            for(int z = 0; z < countArr2.length; z++) {
                if(countArr2[z] == 2 && z<= 1) {
                    downCount++;
                    countArr2[z] = 0;
                }
                if(countArr2[z] == 2 && z>= 2) {
                    downCount+=2;
                    countArr2[z] = 0;
                }
            }
        }

        char[][] possibleWall = {{'L','7'}, {'J','F'}, {'L','F'}, {'J','7'}};
        int[] countArr = {0,0,0,0};
        for(int k = i; k>=0; k--) {
            char c = history.getPipes().get(k).charAt(j);
            for(int z = 0; z < countArr.length; z++) {
                countArr[z] = checkVertWall(history, possibleWall[z], k, j, countArr[z]);
            }
            
            if((c == '-')) {
                upCount++;
            }
            for(int z = 0; z < countArr.length; z++) {
                if(countArr[z] == 2 && z<= 1) {
                    upCount++;
                    countArr[z] = 0;
                }
                if(countArr[z] == 2 && z>= 2) {
                    upCount+=2;
                    countArr[z] = 0;
                }
            }
        }

        
        return ((downCount%2)==1 && (upCount%2)==1);
    }
    public static void checkInsideLoop(PathFinder history) {
        for(int i=0; i<history.getPipes().size(); i++) {
            for(int j=0; j<history.getPipes().get(i).length(); j++) {
                char c = history.getPipes().get(i).charAt(j);
                if(c == '.' || c == 'O') {
                    if(scanL(history, i, j)==false || scanD(history, i, j)==false) {
                        history.setCurrentPipe(i, j, 'O');
                        adjustHiddenVert(history, i, j);
                        adjustHiddenHori(history, i, j);
                    }
                }
            }
        }
    }

    public static void adjustHiddenHori(PathFinder history, int i, int j) {
        for(int m = j; m < history.getPipes().get(i).length(); m++) {
            char d = history.getPipes().get(i).charAt(m);
            if(d == 'O') {
                continue;
            } 
            if(d == '.') {
                history.setCurrentPipe(i, m, 'O');
            } 
            else {
                break;
            }
        }

        for(int m = j; m >- 0; m--) {
            char d = history.getPipes().get(i).charAt(m);
            if(d == 'O') {
                continue;
            } 
            if(d == '.') {
                history.setCurrentPipe(i, m, 'O');
            } 
            else {
                break;
            }
        }
    }

    public static void adjustHiddenVert(PathFinder history, int i, int j) {
        for(int n = i; n < history.getPipes().size(); n++) {
            char d = history.getPipes().get(n).charAt(j);
            if(d == 'O') {
                continue;
            } 
            if(d == '.') {
                history.setCurrentPipe(n, j, 'O');
            } 
            else {
                break;
            }
        }

        for(int n = i; n >= 0; n--) {
            char d = history.getPipes().get(n).charAt(j);
            if(d == 'O') {
                continue;
            } 
            if(d == '.') {
                history.setCurrentPipe(n, j, 'O');
            } 
            else {
                break;
            }
        }
    }
    
    public static int countUnvisited(PathFinder history) {
        int count = 0;
        for(int i=0; i<history.getPipes().size(); i++) {
            for(int j = 0; j<history.getPipes().get(i).length(); j++) {
                if(history.getCurrentPipe(i, j) == '.') {
                    count++;
                }
            } 
        }
        return count;
    }
    public static void main(String[] args) {
        String filePath = "Smalltest10e.txt";

        PathFinder path = new PathFinder();
        PathFinder history = new PathFinder();

        int[] startloc = {};
        int sum = 0;
        int index = 0;
        int cs = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read each line from the file until there are no more lines
            while ((line = reader.readLine()) != null) {
                String modLine = line.replace('7', 'G');
                if(storeStartLoc(modLine, index) != null) {
                    startloc = storeStartLoc(modLine, index);
                    cs++;
                }
                
                if(cs >= 2) {
                    System.out.println("Not fond of multiple start location yet");
                    System.exit(1);
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

        path.setStartChar('F');

        path.modPipe('S', startloc[0], startloc[1], 0);
        traversePipe(path, history, path.nextPipeLoc, 1, 1);

        modUnvisited(path, history);

        String btn = history.getPipes().get(startloc[0]).replace('S', 'F');
        history.getPipes().set(startloc[0], btn);
        checkInsideLoop(history);

        System.out.println(countUnvisited(history));
        
    }
}
