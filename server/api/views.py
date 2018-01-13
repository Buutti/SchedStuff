from rest_framework import generics, viewsets
from . import serializers, models
from django.contrib.auth.models import User, Group


class UserViewSet(viewsets.ModelViewSet):
    queryset = User.objects.all()
    serializer_class = serializers.UserSerializer


class GroupViewSet(viewsets.ModelViewSet):
    queryset = Group.objects.all()
    serializer_class = serializers.GroupSerializer


class CreateEventView(generics.ListCreateAPIView):
    queryset = models.Event.objects.all()
    serializer_class = serializers.EventSerializer

    def perform_create(self, serializer):
        serializer.save()


class EventDetailsView(generics.RetrieveUpdateDestroyAPIView):
    queryset = models.Event.objects.all()
    serializer_class = serializers.EventSerializer
