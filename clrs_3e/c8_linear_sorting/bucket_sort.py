#! /usr/bin/env python3

import math
import unittest

"""
Implementation of bucket sort from CORMEN.

Algorithm self-description:
The input is assumed to be numbers in the half-open interval [0, 1). They are
assumed to be uniformly-distributed.

Bucket sort works by creating n buckets where n is the number of input items.
This will equally divide the range [0, 1) into smaller ranges. By the assumption
that the input is uniformly distributed, we place each number into their
respective bucket. We sort each bucket (shouldn't contain too many items)
individually and then concatenate their results.

Note that if the uniform distribution assumption does not hold, the algorithm
degenerates to the runtime of the sorting algorithm we use to sort each bucket.
"""

def insertion_sort(numlist):
    """
    Sorts the list using insertion sort. Needed as an auxiliary procedure in
    bucket_sort.
    """
    for i in range(1, len(numlist)):
        insert(numlist, i)

    return numlist

def insert(numlist, sorted_limit):
    """
    sorted_limit is the first index of the unsorted part of the list. So if
    sorted_limit == len(numlist), the whole list is assumed to be sorted.
    """
    if sorted_limit < len(numlist):
        for i in range(sorted_limit):
            if numlist[sorted_limit] < numlist[i]:
                for j in range(i, sorted_limit):
                    numlist[j], numlist[sorted_limit] = numlist[sorted_limit], numlist[j]
    
    return numlist

def bucket_sort(numlist):
    """
    Implementation of bucket sort.
    """
    limit = len(numlist)
    buckets = [[] for i in range(limit)]

    for val in numlist:
        buckets[math.floor(limit * val)].append(val)

    ordered_list = []

    for b in buckets:
        insertion_sort(b)
        ordered_list.extend(b)

    return ordered_list

class FunctionsTest(unittest.TestCase):
    
    def test_bucket_sort(self):
        self.assertEqual(bucket_sort([0.78, 0.17, 0.39, 0.26, 0.72, 0.94, 0.21, 0.12, 0.23, 0.68]),
          [0.12, 0.17, 0.21, 0.23, 0.26, 0.39, 0.68, 0.72, 0.78, 0.94])

if __name__ == "__main__":
    unittest.main()
