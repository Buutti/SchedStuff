from rest_framework import serializers
from . import models
from django.contrib.auth.models import User


class UserSerializer(serializers.HyperlinkedModelSerializer):
    events = serializers.HyperlinkedRelatedField(many=True, view_name='event', read_only=True)

    class Meta:
        model = User
        fields = ('url', 'username', 'email', 'events')


class EventSerializer(serializers.HyperlinkedModelSerializer):
    organizer = serializers.ReadOnlyField(source="organizer.username")

    class Meta:
        model = models.Event
        fields = ('name', 'locked', 'id', 'deadline', 'possible_dates', 'must_start_after', 'must_start_before',
                  'length', 'organizer', 'participants')


class PossibleTimeSerializer(serializers.ModelSerializer):
    class Meta:
        model = models.PossibleDate
        fields = ('id', 'date', 'verified_users')
