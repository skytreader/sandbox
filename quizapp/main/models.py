from django.db import models

# Can I create a default model and have all models subclass from that ala CI?

class Users(models.Model):
    username = models.CharField(max_length=20)
    password = models.CharField(max_length=255)
    total_score = models.IntegerField()
    create_time = models.DateTimeField(auto_now_add=True)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    # Apparently, Django can't FK with itself?
    # last_updater = models.ForeignKey(Users)
    can_read = models.BooleanField()
    can_write = models.BooleanField()
    can_exec = models.BooleanField()

class QuizCategories(models.Model):
    category_name = models.CharField(max_length=20, unique=True)
    top_scorer = models.IntegerField()
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

class Quizzes(models.Model):
    quizcatid = models.ForeignKey(QuizCategories)
    quiz_title = models.CharField(max_length=30)
    quiz_desc = models.CharField(max_length=255)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

class tags(models.Model):
    taglabel = models.CharField(max_length=20)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)

class QuizTags(models.Model):
    quizid = models.ForeignKey(Quizzes)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(Users)
