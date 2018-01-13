from django.db import models
from django.contrib.auth.models import User
from django.conf import settings


# class User(models.Model):
#    name = models.CharField(max_length=200)
#    identifier = models.CharField(max_length=200, unique=True, primary_key=True)


class TimeInterval(models.Model):
    interval = models.DurationField()


class Event(models.Model):
    locked = models.BooleanField(default=False)

    name = models.CharField(max_length=200)
    identifier = models.IntegerField(unique=True, primary_key=True)

    deadline = models.DateTimeField()
    possible_times = models.ManyToManyField(TimeInterval, blank=True)
    date = models.DateTimeField()
    length = models.TimeField()

    organizer = models.ForeignKey(settings.AUTH_USER_MODEL, on_delete=models.CASCADE)
    # participants = models.ManyToManyField(User)
