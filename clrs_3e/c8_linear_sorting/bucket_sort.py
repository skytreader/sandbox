#! /usr/bin/env python3

import unittest

"""
Implementation of bucket sort from CORMEN.
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
    pass
