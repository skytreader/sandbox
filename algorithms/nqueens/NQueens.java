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
        System.out.println("getNextState: for " + Arrays.toString(queenConfiguration)); 
        int backtrackCell = queenConfiguration.length - 1;

        for(int i = 0; i < queenConfiguration.length; i++){
            if(queenConfiguration[i] == 0){
                backtrackCell = i;
                break;
            }
        }

        System.out.println("getNextState: backtrack on index " + backtrackCell);

//        if(queenConfiguration[backtrackCell] == queenConfiguration.length){
//            return null;
//        }

        queenConfiguration[backtrackCell]++;

        //System.out.println("getNextState: Entering MEGA loop now...");

        for(int i = 0; i < backtrackCell; i++){
            while(isCheckingPosition(queenConfiguration[backtrackCell], (backtrackCell + 1),
              queenConfiguration[i], (i + 1)) && queenConfiguration[backtrackCell] <= queenConfiguration.length){
                    queenConfiguration[backtrackCell]++;
            }

            while(queenConfiguration[backtrackCell] > queenConfiguration.length){
                System.out.println("getNextState: column-wise backtrack.");
                queenConfiguration[backtrackCell] = 0;
                backtrackCell--;
                if(backtrackCell >= 0){
                    queenConfiguration[backtrackCell]++;
                } else{
                    break;
                }
                // To account for the i++ the for loop automagically does.
                i = -1;
            } 
        }

        System.out.println("getNextState: next state is " + Arrays.toString(queenConfiguration));

        return queenConfiguration;
    }

    static boolean isDiagonalChecking(int r1, int c1, int r2, int c2){
        //System.out.println("isDiagonalChecking: " + r1 + " " + c1 + " " + r2 + " " + c2);
        //System.out.println("isDiagonalChecking: " + (Math.abs(c2 - c1) == Math.abs(r2 - r1)));
        return Math.abs(c2 - c1) == Math.abs(r2 - r1);
    }

    static boolean isCheckingPosition(int r1, int c1, int r2, int c2){
        System.out.println("isCheckingPosition: " + r1 + " " + c1 + " " + r2 + " " + c2);
        System.out.println("isCheckingPosition: same row? " + (r1 == r2));
        System.out.println("isCheckingPosition: diagonal checking? " + isDiagonalChecking(r1, c1, r2, c2));
        return (r1 == r2) || isDiagonalChecking(r1, c1, r2, c2);
    }

    /**
    A configuration is valid if and only if all queens are in the board and none
    of them threaten another.
    */
    static boolean isValidConfiguration(int[] queenConfiguration){
        if(queenConfiguration[0] == 0) return false;
        for(int i = 0; i < queenConfiguration.length; i++){
            for(int j = i + 1; j < queenConfiguration.length; j++){
                if(isCheckingPosition(queenConfiguration[i], (i + 1), queenConfiguration[j], (j + 1)) ||
                  queenConfiguration[j] == 0){
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
            System.out.println("PUSH " + Arrays.toString(queenConfiguration));

            while(!backtracker.empty()){
                int[] config = autoUnbox(backtracker.pop());
                System.out.println("POP " + Arrays.toString(config));
                if(isValidConfiguration(config)){
                    System.out.println("Popped a valid configuration!");
                    validCount++;
                }

                if(canProceed(config)){
                    int[] nextStates = getNextState(config);
                    
                    if(nextStates != null){
                        System.out.println("PUSH: " + Arrays.toString(nextStates));
                        backtracker.push(autobox(nextStates));
                    }
                }
            }

            System.out.println(validCount);
            count = br.readLine();
        }
    }
}
