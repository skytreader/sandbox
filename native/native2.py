#! /usr/bin/env python

import sys
import threading

class NumPrinter(threading.Thread):

    def __init__(self, x):
        super(NumPrinter, self).__init__()
        self.x = x

    def run(self):
        print str(self.x)

if __name__ == "__main__":
    print sys.argv[0]
    print "Same as original but revised the call to threading.Thread's constructor."

    for i in range(20):
        NumPrinter(i).start()
