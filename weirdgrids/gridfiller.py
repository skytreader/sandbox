import gevent
import os
import random
import sys
import time

from gevent import Greenlet
from gevent.lock import Semaphore

chargrid = None
semaphore_lock = None
flags = None

def grid_join():
    return "\n\r".join(["".join(ss) for ss in chargrid])

def fill_grid(row, duration):
    for i in range(duration):
        random_index = random.choice(range(len(chargrid[row])))
        random_bit = random.choice(("1", "0"))
    
        semaphore_lock.acquire()
        chargrid[row][random_index] = random_bit
        semaphore_lock.release()
        #time.sleep(1)
        gevent.sleep(1)

    print str(row) + " is done."
    flags[row] = True

def all_true():
    for b in flags:
        if not b:
            return False
    
    return True

def animator():
    while not all_true():
        os.system("clear")
        sys.stdout.write(grid_join() + "\r")
        sys.stdout.flush()
        #print "Animator will draw..."
        #time.sleep(1)
        gevent.sleep(1)

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print "Usage: python " + sys.argv[0] + " <grid width> <grid height> <duration>"
        exit(1)
    
    width_cols = int(sys.argv[1])
    height_rows = int(sys.argv[2])
    duration = int(sys.argv[3])

    chargrid = [[' ' for i in range(width_cols)] for j in range(height_rows)]
    flags = [False for j in range(height_rows)]
    semaphore_lock = Semaphore()

    draw_threads = []

    for i in range(height_rows):
        artist = gevent.spawn(fill_grid, i, duration)
        draw_threads.append(artist)
    
    draw_threads.append(gevent.spawn(animator))
    gevent.joinall(draw_threads)
