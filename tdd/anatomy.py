import unittest

class TestTheTest(unittest.TestCase):
    
    def setUp(self):
        print "Set-up method called!"

    def test_something(self):
        print "This is a test"
        self.assertEquals(1, 1)

    def test_something_else(self):
        print "This is another test"
        self.assertTrue(1 == 1)

    def tearDown(self):
        print "This is where it ends..."


if __name__ == "__main__":
    unittest.main()
