#! /usr/bin/env python3

import unittest

def naive_inversion_count(numlist):
    limit = len(numlist)
    inversion_count = 0

    for i in range(limit):
        j = i + 1

        while j < limit:
            if numlist[i] > numlist[j]:
                inversion_count += 1

            j += 1

    return inversion_count

def smart_inversion_count(numlist):
    """
    The idea: Perform quicksort on numlist (tho we only really care about
    building the "less than pivot" partition). Each time we send an item to the
    LTP, compute how much did it jump (original index - partition index or
    maybe original index - pivot index?).

    Always start from left. Do not randomize.
    [Why]
    """
    count = 0
    pivot_index = 0
    limit = len(numlist)

    while pivot_index < (limit - 1):
        pivot_element = numlist[pivot_index]
        loop_through_index = pivot_index + 1
        first_gt_pivot = -1

        while loop_through_index < limit:
            if first_gt_pivot != -1 and \
              numlist[loop_through_index] > pivot_element:
                first_gt_pivot = loop_through_index
            else:
                numlist[loop_thtough_index], numlist[first_gt_pivot] = \
                numlist[first_gt_pivot], numlist[loop_through_index]
                last_gt_pivot = loop_through_index

            loop_through_index += 1
                
    print(numlist)

class FunctionsTest(unittest.TestCase):
    
    def test_naive(self):
        self.assertEqual(5, naive_inversion_count([2, 3, 8, 6, 1]))
        self.assertEqual(5, naive_inversion_count([1, 4, 1, 5, 9, 2, 6]))
 
if __name__ == "__main__":
    unittest.main()
