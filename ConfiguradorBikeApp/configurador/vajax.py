from django.http import HttpResponse, HttpResponseRedirect
from django.views.decorators.csrf import csrf_exempt
from django.shortcuts import render, redirect
from .models import *
import subprocess
from subprocess import *
from django.contrib.auth import authenticate,logout
from django.contrib.auth import login as log
from django.contrib import auth

@csrf_exempt
def seleccionar(request):
    if(request.method=="POST"):
        nombre=request.POST.get('nombre')
        try:
            f = open("configuradorBikeApp.conf", "w+")
            if(nombre=="BiciGov"):
                registro=" registro{ \n celular = false,\n facebook= true, \n correo = true\n},\n"
                f.write(registro)
                prestamo=" prestamo{ \n Gratuito = true,\n Efectivo = false, \n Tarjeta = false\n},\n"
                f.write(prestamo)
                gamification=" gamification{\n kilometraje =false,\n recorrido = true,\n recomendaciones=false\n}"
                f.write(gamification)
                f.close()
                sh = open("pruebash.txt", "w+")
                #process=subprocess.call('../prueba.sh' ,)
                process=Popen('prueba.sh', stdout=PIPE, stderr=STDOUT)
                process.wait()  # Wait for process to complete.
                return HttpResponse("Archivo BiciGov Creado")
            elif(nombre=="BiciClub"):
                registro = " registro{ \n celular = true,\n facebook= false, \n correo = true\n},\n"
                f.write(registro)
                prestamo =  " prestamo{ \n Gratuito = false,\n Efectivo = true, \n Tarjeta = false\n},\n"
                f.write(prestamo)
                gamification =" gamification{\n kilometraje =false,\n recorrido = false,\n recomendaciones=false\n}"
                f.write(gamification)
                f.close()
                return HttpResponse("Archivo BiciClub Creado")
            else:
                registro = " registro{ \n celular = true,\n facebook= true, \n correo = false\n},\n"
                f.write(registro)
                prestamo = " prestamo{ \n Gratuito = false,\n Efectivo = false, \n Tarjeta = true\n},\n"
                f.write(prestamo)
                gamification =" gamification{\n kilometraje =true,\n recorrido = false,\n recomendaciones=true\n}"
                f.write(gamification)
                f.close()
                return HttpResponse("Archivo BiciCity Creado")

            return HttpResponse("Error al crear la configuracion, intente de nuevo")

        except:
            return HttpResponse("Error al crear la configuracion")

    return HttpResponse("Metodo no valido")