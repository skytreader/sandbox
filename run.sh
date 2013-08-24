cd classes
# For ZMQ
#java -Djava.library.path=/usr/local/lib -classpath .:/usr/local/share/java/zmq.jar net.skytreader.battleship.Main ../config/sample.cfg
# For plain head-on networking
java net.skytreader.battleship.Main ../config/sample.cfg
