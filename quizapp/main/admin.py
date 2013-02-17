from main.models import User, QuizCategory, Quiz, Tag, QuizTag
from django.contrib import admin

admin.site.register(User)
admin.site.register(QuizCategory)
admin.site.register(Quiz)
admin.site.register(Tag)
admin.site.register(QuizTag)
