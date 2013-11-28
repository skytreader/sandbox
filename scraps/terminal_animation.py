import os
import random
import string
import sys
import time

def random_string(l):
    return "\n".join([random.choice(string.letters) for i in range(l)])

for i in range(100):
    os.system("clear")
    sys.stdout.write(random_string(28) + "\r")
    sys.stdout.flush()
    time.sleep(1)
