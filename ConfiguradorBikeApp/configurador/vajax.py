import webbrowser

from django.http import HttpResponse, HttpResponseRedirect
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render, redirect
from .models import *
import os
import subprocess
import easygui
from subprocess import *
from django.contrib.auth import authenticate,logout
from django.contrib.auth import login as log
from django.contrib import auth

@csrf_exempt
def seleccionar(request):
    if(request.method=="POST"):
        nombre=request.POST.get('nombre')
        try:
            wd = os.getcwd()
            os.chdir("/Maestria Uniandes/2do Semestre/Fabricas/BiciApps-Grupo2/conf")
            f = open("configuraciones.conf", "w+")
            url = "http://localhost:9000/login"
            if(nombre=="BiciGov"):
                registro=" registro{ \n Celular = false\n Facebook= true \n Correo = true\n}\n"
                f.write(registro)
                prestamo=" prestamo{ \n Gratuito = true\n Efectivo = false \n Tarjeta = false\n}\n"
                f.write(prestamo)
                gamification=" gamification{\n Kilometraje =false\n Recorrido = true\n Recomendaciones=false\n}"
                f.write(gamification)
                f.close()
                ##wd = os.getcwd()
                os.chdir("/Maestria Uniandes/2do Semestre/Fabricas/BiciApps-Grupo2")
                print("Creando aplicación BiciGov")
                easygui.msgbox("Creando aplicación BiciGov", title="BikeApp")
                result = subprocess.run("sbt run &", shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                print(str(result))
                easygui.msgbox("Aplicación BiciGov creada", title="BikeApp")
                os.chdir(wd)
                webbrowser.open_new_tab(url)
                return HttpResponse("Archivo BiciGov Creado")

            elif(nombre=="BiciClub"):
                registro = " registro{ \n Celular = true\n Facebook= false \n Correo = true\n}\n"
                f.write(registro)
                prestamo =  " prestamo{ \n Gratuito = false\n Efectivo = true \n Tarjeta = false\n}\n"
                f.write(prestamo)
                gamification =" gamification{\n Kilometraje = false\n Recorrido = false\n Recomendaciones=false\n}"
                f.write(gamification)
                f.close()
                os.chdir("/Maestria Uniandes/2do Semestre/Fabricas/BiciApps-Grupo2")
                print("Creando aplicación BiciClub")
                easygui.msgbox("Creando aplicación BiciClub", title="BikeApp")
                result = subprocess.run("sbt run &", shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                print(str(result))
                easygui.msgbox("Aplicación BiciClub creada", title="BikeApp")
                os.chdir(wd)
                webbrowser.open_new_tab(url)
                return HttpResponse("Archivo BiciClub Creado")
            else:
                registro = " registro{ \n Celular = true\n Facebook= true \n Correo = false\n}\n"
                f.write(registro)
                prestamo = " prestamo{ \n Gratuito = false\n Efectivo = false \n Tarjeta = true\n}\n"
                f.write(prestamo)
                gamification =" gamification{\n Kilometraje = true\n Recorrido = false\n Recomendaciones=true\n}"
                f.write(gamification)
                f.close()
                os.chdir("/Maestria Uniandes/2do Semestre/Fabricas/BiciApps-Grupo2")
                print("Creando aplicación BiciCity")
                easygui.msgbox("Creando aplicación BiciCity", title="BikeApp")
                result = subprocess.run("sbt run &", shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
                print(str(result))
                easygui.msgbox("Aplicación BiciCity creada", title="BikeApp")
                os.chdir(wd)
                webbrowser.open_new_tab(url)
                return HttpResponse("Archivo BiciCity Creado")

            return HttpResponse("Error al crear la configuracion, intente de nuevo")
        except Exception as e:
            return HttpResponse(e)

    return HttpResponse("Metodo no valido")