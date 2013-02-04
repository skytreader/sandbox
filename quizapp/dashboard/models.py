from django.db import models

# Create your models here.
class UserInfo(models.Model):
    """
    All the user info to be displayed in the dashboard.
    """
    username = models.CharField(max_length=255)

class UserQuizStats(models.Model):
    """
    All the quiz stats for the user that we will display on
    the dashboard.
    """
    user = models.ForeignKey(UserInfo)
    total_score = models.IntegerField()
    # TODO Note that you can define your own fields...might be
    # worthwhile looking into this for composite data such as
    # the info for best categories...
    quiz_fin_count = models.IntegerField()
    global_rank = models.IntegerField()
    best_cat_rank = models.IntegerField()
    best_cat_name = models.CharField(max_length=255)
    best_cat_score = models.IntegerField()
