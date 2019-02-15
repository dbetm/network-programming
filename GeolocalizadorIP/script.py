# Tutorial de 'Mi diario Python'
import requests
import json

# URL de la API
api_url = "http://ip-api.com/json/"

# Se definen los parámetros de respuesta que se quieren obtener
param = 'status,country,countryCode,region,regionName,city,zip,lat,lon,timezone,'
param += 'isp,org,as,query'

data = {"fields":param}

def ip_scraping(ip=""):
    # Conectarse a la API
    res = requests.get(api_url+ip, data=data)
    # Obtener y procesar la respuesta JSON
    api_json_res = json.loads(res.content)
    return api_json_res

if __name__ == '__main__':
    # Solicitamos la entrada
    ip = input("Ingresa la dirección IP: ")

    # Llamamos a la función ip_scraping y mostramos los resultados
    par = param.split(",")
    for x in par:
        print(x.upper(), ":")
        print(ip_scraping(ip)[x])
        print("\n")
