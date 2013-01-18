#! /usr/bin/env python3

import unittest

"""
An O(n lg n) solution to the maximum sequence subarray problem
(taken from [CORMEN3e]).
"""

def find_max_crossing_subarray(numseq, low, mid, high):
    """
    Find the maximum subarray crossing the midpoint. The halves
    of the array are defined as [0, midpoint] to [midpoint + 1, len(numseq) - 1].

    Returns a tuple containing the index of the start of the
    crossing subarray (which is at the first half) and the
    end index (which is at the second half) and then the sum
    over that subarray.
    """
    left_side = find_biased_max(numseq, mid, low)
    right_side = find_biased_max(numseq, mid, high)

    return (left_side[0], right_side[0], left_side[1] + right_side[1])

def find_biased_max(numseq, origin_index, dest_index):
    """
    Find the maximum subarray that starts at origin_index
    and ends towards (but not exactly on) dest_index.

    Returns a tuple containing the index where the biased
    maximum subarray _ends_ and the total sum over that
    array, in that order.
    """
    updater = lambda x: x + 1 if origin_index < dest_index else lambda x: x - 1

    i = origin_index
    max_end_index = i - 1 if origin_index < dest_index else i + 1
    max_sum = float("-inf")
    run_sum = 0

    while i != dest_index:
        run_sum += numseq[i]
        
        if run_sum > max_sum:
            max_sum = run_sum
            max_end_index = i
        
        i = updater(i)

    return (max_end_index, max_sum)

def find_max_on(sequence, max_criteria):
    """
    Returns the item in the sequence which is maximum based
    on the given criteria. max_criteria must be a function
    that gets the "comparison point" for an item in sequence.
    This comparison point must be comparable to all the other
    comparison points in the sequence and it must always be
    greater than None.
    """
    max_item = None

    for item in sequence:
        item_comparator = max_criteria(item)

        if item_comparator > max_item:
            max_item = item_comparator
    
    return max_item

def find_max_subarray(numseq, low, high):
    """
    Recursive function (hence the method signature). low
    and high are indices.

    Returns a tuple containing the start index of the max
    subarray, the end index of the max subarray, and the
    sum over that subarray, in that order.

    Challenge: Write an iterative version.
    """
    if low == (high + 1): # Base case: we need to consider only one element
        # Deviation from CORMEN. Consider the case when low = 0, high = 1.
        # Left recursion will terminate with low = 0, high = 0 but right
        # recursion will loop indefinitely with low = 0, high = 1. FIXME
        return (low, high, numseq[low])
    else:
        # Determine the midpoint.
        midpoint = int((low + high) / 2)
        
        # Get the max subarray of the left side
        lmax = find_max_subarray(numseq, low, midpoint)

        # Get the max subaray of the right side
        rmax = find_max_subarray(numseq, midpoint, high)

        # Get the maximum crossing subarray
        cmax = find_max_crossing_subarray(numseq, low, midpoint, high)
        
        max_criteria = lambda x: x[2]
        return find_max_on((lmax, rmax, cmax), max_criteria)

def max_subarray(numseq):
    """Driver method. Call this not find_max_subarray"""
    return find_max_subarray(numseq, 0, len(numseq) - 1)

class FunctionsTest(unittest.TestCase):
    
    def test_max_subarray(self):
        self.assertEqual((0, 1, 20), max_subarray((5, 15, -30, 10)))

if __name__ == "__main__":
    unittest.main()
