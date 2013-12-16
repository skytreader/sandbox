import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
Each line is indicated the number of queens; output the number of safe
configurations for that number of queens.
*/
public class NQueens{
    
    static int[] getNextState(int[] queenConfiguration){
        /*Find the first nonzero cell in queenConfigurations. If there is none.
        return the index of the last cell.*/
        int backtrackCell = queenConfigurations.length - 1;

        for(int i = 0; i < queenConfigurations.length; i++){
            if(queenConfigurations[i] == 0){
                backtrackCell = i;
                break;
            }
        }

        while(queenConfiguration[backtrackCell] <= queenConfiguration.length){
            if(isValidConfiguration(queenConfiguration)){
                break;
            }

            queenConfiguration[backtrackCell]++;
        }

        return nextConfigurations;
    }

    static boolean isDiagonalChecking(int r1, int c1, int r2, int c2){
        return Math.abs(c2 - c1) == Math.abs(r2 - r1);
    }

    static boolean isCheckingPosition(int r1, int c1, int r2, int c2){
        return (r1 != 0 && r2 != 0) && ((r1 == r2) || isDiagonalChecking(r1, c1, r2, c2));
    }

    static boolean isValidConfiguration(int[] queenConfiguration){
        for(int i = 0; i < queenConfiguration.length; i++){
            for(int j = i + 1; j < queenConfiguration.length && queenConfiguration[j] != 0; j++){
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

    /**
    You can only proceed from a given configuration if not all queens are in
    the last row.
    */
    static boolean canProceed(int[] configuration){ 
        for(int i = 0; i < configuration.length; i++){
            if(configuration[i] != configuration.length){
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
            Set<Integer[]> checkedConfigurations = new HashSet<Integer[]>();
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
                    int[] nextStates = getNextState(config);
                    backtracker.push(autobox(nextStates[i]));
                }
            }

            System.out.println(validCount);
            count = br.readLine();
        }
    }
}
