from django.db import models


class User(models.Model):
    name = models.CharField(max_length=200)
    identifier = models.CharField(max_length=200, unique=True, primary_key=True)


class TimeInterval(models.Model):
    interval = models.DurationField()


class Event(models.Model):
    locked = models.BooleanField(default=False)

    name = models.CharField(max_length=200)
    identifier = models.CharField(max_length=200, unique=True, primary_key=True)

    deadline = models.DateTimeField()
    possible_times = models.ManyToManyField(TimeInterval)
    date = models.DateTimeField()
    length = models.TimeField()

    # organizer = models.ForeignKey(User, on_delete=models.CASCADE)
    participants = models.ManyToManyField(User)
