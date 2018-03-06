from django.conf.urls import url, include
from rest_framework.urlpatterns import format_suffix_patterns
from rest_framework.routers import DefaultRouter
from . import views

router = DefaultRouter()
router.register(r'events', views.EventViewSet)
router.register(r'users', views.UserViewSet)
router.register(r'possible_time', views.PossibleDateViewSet)

urlpatterns = [
    url(r'^', include(router.urls))
]
