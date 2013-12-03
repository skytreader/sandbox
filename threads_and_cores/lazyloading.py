import gevent
import sys
import time

from gevent.queue import Queue

operations = Queue()
name_pool = ("heero", "duo", "trowa", "quatre", "chang", "treize", "romefeller", "raven")

boss_is_done = False

def compute_worker(worker_name, sleeptime):
    while not operations.empty() and not boss_is_done:
        operands = operations.get()
        parse = operands.split(" ")
        print worker_name + ": " + str(int(parse[0]) + int(parse[1]))
        gevent.sleep(sleeptime)

def lazyloader(filename, sleeptime):
    print "Adding boss..."
    with open(filename) as operand_source:
        for operands in operand_source:
            operations.put_nowait(operands)
            gevent.sleep(sleeptime)
    boss_is_done = True
    print "Done adding boss."

def numeric_parse(n):
    try:
        return int(n)
    except ValueError:
        return float(n)

if __name__ == "__main__":
    if len(sys.argv) != 4:
        print "Usage: python " + str(sys.argv[0]) + " <operands file> <worker_count> <sleeptime>"
        exit(1)
    
    worker_count = int(sys.argv[2])

    if worker_count < 1 or worker_count > 8:
        print "worker_count should be between 1 and 8, inclusive."
        exit(1)

    sleeptime = numeric_parse(sys.argv[3])
    
    greenlets = []
    greenlets.append(gevent.spawn(lazyloader, sys.argv[1], sleeptime))
    
    for i in range(worker_count):
        greenlets.append(gevent.spawn(compute_worker, name_pool[i], sleeptime))

    start_time = time.time()
    gevent.joinall(greenlets)
    end_time = time.time()
    print "Start time: " + str(start_time)
    print "End time: " + str(end_time)
    print "Worker count: " + sys.argv[2]
