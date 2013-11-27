import random
import string
import sys
import time

def random_string(l):
    return "".join([random.choice(string.letters) for i in range(l)])

for i in range(100):
    sys.stdout.write(random_string(28) + "\r")
    sys.stdout.flush()
    time.sleep(1)
