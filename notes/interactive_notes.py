class InteractiveNote(object):
    """
    An interactive note displays a text and then runs some computations
    (simulations, illustrations, sample runs, etc.), ideably with visual
    output.

    TODO: How can we make interactive notes for concurrency topics?
    """

    def __init__(self, title, description, process):
        """
        title - the title of this note, string.
        description - the description for this note (a.k.a, _the_ notes),
            string. Suggested that you use docstrings for this.
        process - a function demonstrating something. _Anything_.
        """
        self.title = title
        self.desc = description
        self.process = process

    def __str__(self):
        return "Note title: " + self.title + "\n" \
        "Description:\n" + self.desc

def __note_divider():
    return "#" * 20

def note_divider():
    return "=" * 10

def interactive_show(note_list):
    for note in note_list:
        print(__note_divider())
        print(str(note))
        note.process()
        print(__note_divider())
