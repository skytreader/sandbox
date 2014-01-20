#! /usr/bin/env python3

import unittest

def simul_minmax(numlist):
    """
    From the given list of numbers, simulatneously pick the minimum and maximum.
    The return value is a tuple pair with the minimum as the first element and
    the maximum as the second element.
    """
    curmindex = 0
    curmaxdex = 0

    for i, v in enumerate(numlist):
        if v < numlist[curmindex]:
            curmindex = i
        elif v > numlist[curmaxdex]:
            curmaxdex = i

    return (numlist[curmindex], numlist[curmaxdex])

class FunctionsTest(unittest.TestCase):
    
    def test_simul_minmax(self):
        singleton = [1]
        self.assertEqual(simul_minmax(singleton), (1, 1))

        odd_count = [3, 1, 4, 1, 5]
        self.assertEqual(simul_minmax(odd_count), (1, 5))

        even_count = [3, 1, 4, 1, 5, 9]
        self.assertEqual(simul_minmax(even_count), (1, 9))

if __name__ == "__main__":
    unittest.main()
