from javax.swing import JFrame

"""
Important note:

When using Jython, favor the import form used above. Upon invocation, Jython
tries to scan for Java packages in your classpath. However, it may fail to find
some packages for any number of reasons (permissions, configuration, etc.). The
import form used above is always guaranteed to work.

See also:
https://wiki.python.org/jython/PackageScanning
"""

if __name__ == "__main__":
    w = JFrame("Jython!")
    w.size = (200, 200)
    w.visible = True
