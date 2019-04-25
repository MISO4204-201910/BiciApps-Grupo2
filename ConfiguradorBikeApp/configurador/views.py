from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt

def index(request):
    context = {}
    return render(request, "index.html", context)

def gamificationBiciGov(request):
    context ={}
    return render(request, "gamificationBiciGov.html", context)

def gamificationBiciCity(request):
    context ={}
    return render(request, "gamificationBiciCity.html", context)

def catalogoPremios(request):
    context ={}
    return render(request, "catalogoPremios.html", context)

# Create your views here.
