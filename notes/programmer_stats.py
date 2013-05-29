#! /usr/bin/env python3

from interactive_notes import InteractiveNote, interactive_show, note_divider
import operator

import random

"""
Python code for the R code in http://zedshaw.com/essays/programmer_stats.html
"""

desc1 = """
Construct two sets of 100 random samples from the normal distribution, differing
only in the standard deviation. Keep the mean at 30. Notice that the mean will
always be close to 30. (So what's the standard deviation for?).

a -> standard dev = 5
b -> standard dev = 20
"""

def normal_distribution_process():
    limit = 100
    a_greater = 0
    b_greater = 0
    ever_equal = 0
    meandiff_mean = 0
    meandiff_median = []
    meandiff_mode = {}
    
    for i in range(limit):
        a = [random.normalvariate(30, 5) for j in range(100)]
        b = [random.normalvariate(30, 20) for j in range(100)]
        a_mean = sum(a) / len(a)
        b_mean = sum(b) / len(b)
        meandiff = abs(a_mean - b_mean)
        
        meandiff_mean += meandiff
        meandiff_median.append(meandiff)
        meandiff_mode[meandiff] = meandiff_mode[meandiff] if meandiff in meandiff_mode.keys() else 1

        print("Generated means: a = " + str(a_mean) + ", b = " + str(b_mean))
        print("Abs difference: " + str(meandiff))

        if a_mean > b_mean:
            a_greater += 1
        elif b_mean > a_mean:
            b_greater += 1
        else:
            ever_equal += 1

        print(note_divider())
    
    # Compute the mean stats, pardon the pun!
    meandiff_mean = meandiff_mean / limit
    
    # slacky
    meandiff_median.sort()
    meandiff_median = meandiff_median[int(limit / 2)]

    # TODO Try to get this better!
    meandiff_mode = sorted(meandiff_mode.items(), key = lambda x: x[1], reverse = True)
    meandiff_mode = meandiff_mode[0]

    # Print out some stats, pardon the pun!
    print("Number of sample sets: " + str(limit))
    print("mean(a) > mean(b) : " + str(a_greater))
    print("mean(a) < mean(b) : " + str(b_greater))
    print("mean(a) = mean(b) : " + str(ever_equal))
    print(note_divider())
    print("Mean statistics :P")
    print("Mean : " + str(meandiff_mean))
    print("Median : " + str(meandiff_median))
    print("Mode : " + str(meandiff_mode))

normal_distribution = InteractiveNote("Normal Distribution Demo", desc1, normal_distribution_process)

notes = [normal_distribution]

if __name__ == "__main__":
    interactive_show(notes)
