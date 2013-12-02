import random
import sys

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print "Usage: python " + sys.argv[0] + " <range limit> <data length>"
        exit(1)

    pool = range(int(sys.argv[1]))
    limit = int(sys.argv[2])

    for i in xrange(limit):
        a = random.choice(pool)
        b = random.choice(pool)

        print str(a) + " " + str(b)
