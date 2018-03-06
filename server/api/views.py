from rest_framework import viewsets, permissions
from . import serializers, models
from django.contrib.auth.models import User
from api.permissions import IsOwnerOrReadOnly


class UserViewSet(viewsets.ReadOnlyModelViewSet):
    queryset = User.objects.all()
    serializer_class = serializers.UserSerializer


class EventViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides 'list', 'create', 'retrieve', 'update' and 'destroy' actions.
    """

    queryset = models.Event.objects.all()
    serializer_class = serializers.EventSerializer
    permission_classes = (permissions.IsAuthenticatedOrReadOnly, IsOwnerOrReadOnly)

    def perform_create(self, serializer):
        serializer.save(organizer=self.request.user)


class PossibleDateViewSet(viewsets.ModelViewSet):
    """
    This viewset automatically provides 'list', 'create', 'retrieve', 'update' and 'destroy' actions.
    """

    queryset = models.PossibleDate.objects.all()
    serializer_class = serializers.PossibleTimeSerializer

