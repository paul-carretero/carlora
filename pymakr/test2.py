import pycom
from network import Bluetooth
import time
import binascii

bt = Bluetooth()
bt.start_scan(10)
conn = bt.connect('08b5d69c1d67')
print(conn)
services = conn.services()
print(services)
for service in services:
    print(service.uuid())
