import gevent
import sys
import time

from gevent.queue import Queue

operations = Queue()
name_pool = ("heero", "duo", "trowa", "quatre", "chang", "treize", "romefeller", "raven")

def compute_worker(worker_name):
    while not operations.empty():
        operands = operations.get()
        parse = operands.split(" ")
        print worker_name + ": " + str(int(parse[0]) + int(parse[1]))

def lazyloader(filename):
    print "Adding boss..."
    with open(filename) as operand_source:
        for operands in operand_source:
            operations.put_nowait(operands)
    print "done."

if __name__ == "__main__":
    if len(sys.argv) != 3:
        print "Usage: python " + str(sys.argv[0]) + " <operands file> <worker_count>"
        exit(1)
    
    worker_count = int(sys.argv[2])

    if worker_count < 1 or worker_count > 8:
        print "worker_count should be between 1 and 8, inclusive."
        exit(1)
    
    greenlets = []
    greenlets.append(gevent.spawn(lazyloader, sys.argv[1]))
    
    for i in range(worker_count):
        greenlets.append(gevent.spawn(compute_worker, name_pool[i]))

    start_time = time.time()
    gevent.joinall(greenlets)
    end_time = time.time()
    print "Start time: " + str(start_time)
    print "End time: " + str(end_time)
