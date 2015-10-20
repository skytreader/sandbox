package net.skytreader.kode.npuzzle.utils;

import java.util.Arrays;
import java.util.Stack;

import net.skytreader.kode.npuzzle.NPuzzle;

/**
Some handy methods that may be of use.
*/
public class Algorithms{
    public static int countInversions(int[] numlist){
        int limit = numlist.length;
        int[] listClone = Arrays.copyOf(numlist, limit);
        int inversionCount = 0;
        
        for(int i = 1; i < limit; i++){
            inversionCount += mockMerge(listClone, i);
            i *= 2;
        }

        return inversionCount;
    }
    
    private static Stack<Integer> stackify(int[] num){
        Stack<Integer> stackified = new Stack<Integer>();
        for(int i = num.length - 1; i >= 0; i--){
            stackified.push(num[i]);
        }
        return stackified;
    }

    private static int mockMerge(int[] num, int skipCount){
        int limit = num.length;
        int l2Count = 0;

        for(int i = 0; i < limit;){
            Stack<Integer> partition1 = stackify(Arrays.copyOfRange(num, i, i + skipCount));
            int p2Limit = i + (2 * skipCount);

            if(p2Limit > limit){
                p2Limit = limit;
            }

            Stack<Integer> partition2 = new Stack<Integer>();

            if((i + skipCount) < limit){
                partition2 = stackify(Arrays.copyOfRange(num, i + skipCount, p2Limit));
            }

            int j = i;

            // Perform merge sort.
            while(j < p2Limit && !partition1.empty() && !partition2.empty()){
                if(partition1.peek() <= partition2.peek()){
                    num[j] = partition1.pop();
                } else{
                    num[j] = partition2.pop();
                    l2Count++;
                }
                j++;
            }

            for(;j < p2Limit && !partition1.empty(); j++){
                num[j] = partition1.pop();
            }

            for(;j < p2Limit && !partition2.empty(); j++){
                num[j] = partition2.pop();
            }

            i = p2Limit;
        }

        int leftover = limit % skipCount;

        if(leftover > 0){
            int cutpoint = limit = leftover;
            Stack<Integer> partition1 = stackify(Arrays.copyOfRange(num, 0, cutpoint));
            Stack<Integer> partition2 = stackify(Arrays.copyOfRange(num, cutpoint, limit));

            for(int i = 0; i < limit && !partition1.empty() && !partition2.empty(); i++){
                if(partition1.peek() < partition2.peek()){
                    num[i] = partition1.pop();
                } else{
                    num[i] = partition2.pop();
                    l2Count = partition1.size();
                }
            }
        }

        return l2Count;
    }

    public static boolean isSolvable(NPuzzle np){
        return true;
    }
}
