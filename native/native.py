#! /usr/bin/env python

import sys
import threading

class NumPrinter(threading.Thread):

    def __init__(self, x):
        self.x = x
        threading.Thread.__init__(self)

    def run(self):
        print str(self.x)

if __name__ == "__main__":
    print sys.argv[0]
    print "The original. Semi-straight out of http://stackoverflow.com/questions/31340/how-do-threads-work-in-python-and-what-are-common- python-threading-specific-pit"

    for i in range(20):
        NumPrinter(i).start()
