from django.db import models


class PossibleDate(models.Model):
    id = models.AutoField(primary_key=True)
    date = models.DateTimeField()
    verified_users = models.ManyToManyField('auth.User')


class Event(models.Model):
    id = models.AutoField(primary_key=True)
    locked = models.BooleanField(default=False)

    name = models.CharField(max_length=200)

    deadline = models.DateTimeField()
    possible_dates = models.ManyToManyField(PossibleDate, blank=True, related_name="possible_on")
    must_start_after = models.TimeField()
    must_start_before = models.TimeField()
    length = models.DurationField()

    organizer = models.ForeignKey('auth.User', on_delete=models.CASCADE, related_name='events')
    participants = models.ManyToManyField('auth.User', blank=True, related_name="participated_events")
