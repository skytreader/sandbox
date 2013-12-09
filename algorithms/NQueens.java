import java.io.*;
import java.util.Arrays;
import java.util.Stack;

/**
Each line is indicated the number of queens; output the number of safe
configurations for that number of queens.
*/
public class NQueens{
    
    static int[][] getNextStates(int[] queenConfiguration){
        //System.out.println("Input: " + Arrays.toString(queenConfiguration));
        int limit = queenConfiguration.length;
        int[][] nextConfigurations = new int[limit][];

        for(int i = 0; i < limit; i++){
            if(queenConfiguration[i] < (limit - 1)){
                nextConfigurations[i] = Arrays.copyOf(queenConfiguration, limit);
                nextConfigurations[i][i]++;
                //System.out.println("Next state: " + Arrays.toString(nextConfigurations[i]));
            }
        }

        return nextConfigurations;
    }

    static int getSlope(int r1, int c1, int r2, int c2){
        //System.out.println("Get slope of " + r1 + " " + c1 + " " + r2 + " " + c2);
        int slope = ((r2 + 1) - (r1 + 1)) / ((c2 + 1) - (c1 + 1));
        if(slope < 0){
            slope *= -1;
        }
        //System.out.println("Slope is " + slope);

        return slope;
    }

    static boolean isCheckingPosition(int r1, int c1, int r2, int c2){
        return (r1 == r2) || (getSlope(r1, c1, r2, c2) == 1);
    }

    static boolean isValidConfiguration(int[] queenConfiguration){
        for(int i = 0; i < queenConfiguration.length; i++){
            for(int j = i + 1; j < queenConfiguration.length; j++){
                if(isCheckingPosition(queenConfiguration[i], i, queenConfiguration[j], j)){
                    return false;
                }
            }
        }

        return true;
    }

    static int[] autoUnbox(Integer[] ibox){
        int[] unboxed = new int[ibox.length];

        for(int i = 0; i < ibox.length; i++){
            unboxed[i] = ibox[i];
        }

        return unboxed;
    }

    static Integer[] autobox(int[] ibox){
        Integer[] autoboxed = new Integer[ibox.length];

        for(int i = 0; i < ibox.length; i++){
            autoboxed[i] = ibox[i];
        }

        return autoboxed;
    }

    static boolean canProceed(int[] configuration){ 
        for(int i = 0; i < configuration.length; i++){
            if(configuration[i] != (configuration.length - 1)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String count = br.readLine();

        while(count != null){
            int validCount = 0;
            int queenCount = Integer.parseInt(count);
            int[] queenConfiguration = new int[queenCount];
            Stack<Integer[]> backtracker = new Stack<Integer[]>();
            backtracker.push(autobox(queenConfiguration));
            //System.out.println("PUSH " + Arrays.toString(queenConfiguration));

            while(!backtracker.empty()){
                int[] config = autoUnbox(backtracker.pop());
                //System.out.println("POP " + Arrays.toString(config));
                if(isValidConfiguration(config)){
                    //System.out.println("Popped a valid configuration!");
                    validCount++;
                }

                if(canProceed(config)){
                    int[][] nextStates = getNextStates(config);
    
                    for(int i = 0; i < nextStates.length; i++){
                        if(nextStates[i] != null){
                            //System.out.println("PUSH " + Arrays.toString(nextStates[i]));
                            backtracker.push(autobox(nextStates[i]));
                        }
                    }
                }
            }

            System.out.println(validCount);
            count = br.readLine();
        }
    }
}
