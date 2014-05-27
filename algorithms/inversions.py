#! /usr/bin/env python3

import math
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
                numlist[loop_through_index], numlist[first_gt_pivot] = \
                numlist[first_gt_pivot], numlist[loop_through_index]
                last_gt_pivot = loop_through_index

            loop_through_index += 1
                
    print(numlist)

def merge_by(numlist, skip_count):
    i = 0
    limit = len(numlist)
    l2_count = 0

    while i < limit:
        # list 1 is the range [i, i + skip_count]
        # list 2 is the range [i + skip_count + 1, i + 2 * skip_count]
        l1 = numlist[i:i + skip_count]
        l2 = numlist[i + skip_count:i + (2 * skip_count)]

        j = i
        sublimit = i + (2 * skip_count)

        while j < sublimit and len(l1) and len(l2):
            if l1[0] <= l2[0]:
                numlist[j] = l1.pop(0)
            else:
                numlist[j] = l2.pop(0)
                l2_count += len(l1)

            j += 1

        while j < sublimit and len(l1):
            numlist[j] = l1.pop(0)
            j += 1

        while j < sublimit and len(l2):
            numlist[j] = l2.pop(0)
            j += 1

        i = sublimit

    leftover = limit % skip_count

    if leftover:
        # merge the leftover with the rest of the sorted part
        cutpoint = limit - leftover
        l1 = numlist[0:cutpoint]
        l2 = numlist[cutpoint:limit]

        i = 0
        while i < limit and len(l1) and len(l2):
            if l1[0] < l2[0]:
                numlist[i] = l1.pop(0)
            else:
                numlist[i] = l2.pop(0)
                l2_count = len(l1)

            i += 1
        
        # Since we are sure that l1 is longer than l2
        while i < limit and len(l1):
            numlist[i] = l1.pop(0)
            i += 1
    
    return l2_count

def merge_inversion_count(numlist):
    """
    The idea is to walk through the steps of merge sort. Each time we pick
    an item from pile 2 during the merge operation, we add n to the inversion
    count, where n is the number of items left in pile 1.
    """
    list_clone = [num for num in numlist]
    limit = len(list_clone)
    inversion_count = 0
    i = 1

    while i < limit:
        inversion_count += merge_by(list_clone, i)
        i *= 2

    return inversion_count


class FunctionsTest(unittest.TestCase):
    
    def test_naive(self):
        self.assertEqual(5, naive_inversion_count([2, 3, 8, 6, 1]))
        self.assertEqual(5, naive_inversion_count([1, 4, 1, 5, 9, 2, 6]))

    def test_merge_inversion_count(self):
        self.assertEqual(5, merge_inversion_count([2, 3, 8, 6, 1]))
        self.assertEqual(5, merge_inversion_count([1, 4, 1, 5, 9, 2, 6]))


if __name__ == "__main__":
    unittest.main()
