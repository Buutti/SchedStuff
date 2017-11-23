from rest_framework import serializers
from . import models


class UserSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.User
        fields = ('identifier', 'name')


class EventSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.Event
        fields = ('identifier', 'name', 'locked', 'deadline', 'possible_times', 'date', 'length', 'participants')
