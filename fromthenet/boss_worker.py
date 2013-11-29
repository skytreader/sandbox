import gevent
import time
from gevent.queue import Queue

tasks = Queue()

def worker(n):
    while not tasks.empty():
        task = tasks.get()
        print "Worker " + n + " got task " + task
        gevent.sleep(1)
        time.sleep(1)
    
    print n + "'s day is done."

def boss():
    for i in xrange(1, 25):
        tasks.put_nowait(str(i))

gevent.spawn(boss).join()

gevent.joinall((gevent.spawn(worker, "steve"), gevent.spawn(worker, "bruce"),
  gevent.spawn(worker, "tony")))
