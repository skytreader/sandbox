from django.db import models

# Can I create a default model and have all models subclass from that ala CI?

class User(models.Model):
    username = models.CharField(max_length=20)
    password = models.CharField(max_length=255)
    total_score = models.IntegerField(default=0)
    create_time = models.DateTimeField(auto_now_add=True)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    # Apparently, Django can't FK with itself?
    # last_updater = models.ForeignKey(User)
    can_read = models.BooleanField(default=True)
    can_write = models.BooleanField(default=True)
    can_exec = models.BooleanField(default=True)

    def __unicode__(self):
        return str(self.id) + " " + self.username

class QuizCategory(models.Model):
    category_name = models.CharField(max_length=20, unique=True)
    top_scorer = models.IntegerField()
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(User)

    class Meta:
        verbose_name_plural = "QuizCategories"

    def __unicode__(self):
        return str(self.id) + " " + self.category_name

class Quiz(models.Model):
    quizcatid = models.ForeignKey(QuizCategory)
    quiz_title = models.CharField(max_length=30)
    quiz_desc = models.CharField(max_length=255)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(User)

    class Meta:
        verbose_name_plural = "Quizzes"

    def __unicode__(self):
        return str(self.id) + " " + self.quiz_title

class Tag(models.Model):
    taglabel = models.CharField(max_length=20)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(User)

    def __unicode__(self):
        return str(self.id) + " " + self.taglabel

class QuizTag(models.Model):
    tagid = models.ForeignKey(Tag, primary_key=True)
    quizid = models.ForeignKey(Quiz)
    last_update = models.DateTimeField(auto_now=True, auto_now_add=True)
    last_updater = models.ForeignKey(User)
    unique_together = ("tagid", "quizid")

    def __unicode__(self):
        return str(tagid) + " " + str(quizid)
