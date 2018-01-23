#!/usr/bin/env python
import requests
import os
import datetime

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')
sensor_path = '/sys/bus/w1/devices/28-0416a4f7ffff/w1_slave'
url = 'http://130.229.163.46:8080/temperatures/'
def tempRead():
    t = open(sensor_path, 'r')
    lines = t.readlines()
    t.close()

    temp_output = lines[1].find('t=')
    if temp_output != -1:
        temp_string = lines[1].strip()[temp_output+2:]
        temp_c = float(temp_string)/1000.0
    return round(temp_c,1)
while True:
    temp = tempRead()
    print temp
    date = datetime.datetime.now().isoformat()
    print date
    query = {'date':date, 'temperature':temp, 'key':'79b7a1a99e4d3799fcfa2fc77ea52782'}
    res = requests.post(url,json=query)
    break