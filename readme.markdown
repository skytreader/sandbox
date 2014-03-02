# Cython tutorial (sort of)

For PyCon Philippines 2014.

The official tutorial link [here](http://docs.cython.org/src/tutorial/cython_tutorial.html).

## What's happening here?

Cython converts your Python source code into machine code. Moreover, this converted
machine code is invokeable from your other Python modules!

## Differences in process

 1. You save your files with a `.pyx` extension.
 2. Instead of a plain compile/interpret command, you have to build even the
    simplest modules (with the Python equivalent to make files, `setup.py`).

It even generated an equivalent C source code listing! (Can you make heads or tails
of that?)

### Ok, that can get easier...

Just use `pyximport`!

    import pyximport; pyximport.install()
    import helloworld
