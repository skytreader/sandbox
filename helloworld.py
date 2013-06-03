import tornado.ioloop
import tornado.web

"""
TODO:
 - View templates
 - Database connection
"""

class MainHandler(tornado.web.RequestHandler):
    def get(self):
        self.write("This was from a get method.")

    def post(self):
        self.write("This is from a post method.")

app = tornado.web.Application([(r"/", MainHandler)])

if __name__ == "__main__":
    app.listen(8888)
    tornado.ioloop.IOLoop.instance().start()
