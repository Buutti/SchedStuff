from django.conf.urls import url
from rest_framework.urlpatterns import format_suffix_patterns
from . import views

urlpatterns = {
    url(r'^users/$', views.CreateUserView.as_view(), name="create user"),
    url(r'^users/(?P<pk>[0-9]+)/$', views.UserDetailsView.as_view(), name="user details"),


    url(r'^events/$', views.CreateEventView.as_view(), name="create event"),
    url(r'^events/(?P<pk>[0-9]+)/$', views.EventDetailsView.as_view(), name="event details"),
}

urlpatterns = format_suffix_patterns(urlpatterns)
