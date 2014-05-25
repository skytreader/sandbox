import math
import unittest

def merge_by(numlist, skip_count):
    i = 0
    limit = len(numlist)

    while i < limit:
        # list 1 is the range [i, i + skip_count]
        # list 2 is the range [i + skip_count + 1, i + 2 * skip_count]
        l1 = numlist[i:i + skip_count + 1]
        l2 = numlist[i + skip_count + 1:i + (2 * skip_count) + 1]

        j = i
        sublimit = i + (2 * skip_count) + 1

        while j < sublimit and len(l1) and len(l2):
            if l1[0] <= l2[0]:
                numlist[j] = l1.pop(0)
            else:
                numlist[j] = l2.pop(0)

            j += 1

        while j < sublimit and len(l1):
            numlist[j] = l1.pop(0)
            j += 1

        while j < sublimit and len(l2):
            numlist[j] = l2.pop(0)
            j += 1

        i = sublimit

def merge_sort(numlist):
    """
    Keep merging til a skip count of half the numlist size.
    """
    # TODO How about lists with an odd length
    limit = math.floor(len(numlist) / 2) + 1
    
    for i in range(limit):
        merge_by(numlist, i)


class FunctionsTest(unittest.TestCase):
    
    def test_sort(self):
        one = [1]
        merge_sort(one)
        self.assertEqual(one, [1])
        pi = [1, 4, 1 ,5 ,9, 2]
        merge_sort(pi)
        self.assertEqual(pi, [1, 1, 2, 4, 5, 9])

if __name__ == "__main__":
    unittest.main()
