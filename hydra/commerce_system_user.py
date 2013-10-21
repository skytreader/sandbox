class CommerceSystemUser(object):
    """
    See also: CommerceSystemUser.java .
    """

    def __init__(self):
        self.__userId__ = -1
        self.__userName__ = ""
        self.password = ""
        self. address = ""
        self.firstName = ""
        self.lastName = ""
    
    @property
    def userId(self):
        return self.__userId__

    @property
    def userName(self):
        return self.__userName__
