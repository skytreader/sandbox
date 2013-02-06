from django.db import models

# Can I create a default model and have all models subclass from that ala CI?

class Users(models.Model):
    username = models.CharField(max_length=20)
    password = models.CharField(max_length=255)
    total_score = models.IntegerField(default=0)
    create_time = models.DateTimeField(auto_now_add=True)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    # Apparently, Django can't FK with itself?
    # last_updater = models.ForeignKey(Users)
    can_read = models.BooleanField(default=True)
    can_write = models.BooleanField(default=True)
    can_exec = models.BooleanField(default=True)

    def __unicode__(self):
        return str(self.id) + " " + self.username

class QuizCategories(models.Model):
    category_name = models.CharField(max_length=20, unique=True)
    top_scorer = models.IntegerField()
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

    def __unicode__(self):
        return str(self.id) + " " + self.category_name

class Quizzes(models.Model):
    quizcatid = models.ForeignKey(QuizCategories)
    quiz_title = models.CharField(max_length=30)
    quiz_desc = models.CharField(max_length=255)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

    def __unicode__(self):
        return str(self.id) + " " + self.quiz_title

class Tags(models.Model):
    taglabel = models.CharField(max_length=20)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

    def __unicode__(self):
        return str(self.id) + " " + self.taglabel

class QuizTags(models.Model):
    tagid = models.ForeignKey(Tags, primary_key=True)
    quizid = models.ForeignKey(Quizzes)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)
    unique_together = ("tagid", "quizid")

    def __unicode__(self):
        return str(tagid) + " " + str(quizid)
