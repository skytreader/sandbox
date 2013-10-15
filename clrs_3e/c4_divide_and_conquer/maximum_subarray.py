#! /usr/bin/env python3

"""
Python port of maximum subarray problem in _Introduction to Algorithms 3e_. This
uses divide and conquer techniques. Can be found in Chpater 4 Section 1 of the
book.

Running time is O(n lg(n)). I think I can do this faster at O(n) with dynamic
programming.

@author Chad Estioco
"""

def find_max_crossing_subarray(a, low, mid, high):
    """
    Parameter assumptions:
    a - the array
    low - the lowest index in the subarray we are considering (inclusive)
    mid - the midpoint. The subarray described by the returned tuple must cross
          this index.
    high - the highest index in the subarray we are considering (exclusive)

    Returns a tuple with three elements containing the index where the crossing
    subarray starts (inclusive), the index where the crossing subarray ends
    (exclusive), and the total sum of the crossing subarray, in that order.
    """
    max_left_sum = float("-inf")
    left_sum = 0
    max_left_index = mid
    i = mid

    while i >= low:
        left_sum += a[i]
        if left_sum > max_left_sum:
            max_left_sum = left_sum
            max_left_index = i

        i -= 1

    max_right_sum = float("-inf")
    right_sum = 0
    max_right_index = mid + 1
    i = mid + 1

    while i < high:
        right_sum += a[i]
        if right_sum > max_right_sum:
            max_right_sum = right_sum
            max_right_index = i

        i += 1

    return (max_left_index, max_right_index + 1, max_left_sum + max_right_sum)
