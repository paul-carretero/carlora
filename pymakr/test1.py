import pycom
from network import Bluetooth
import time
import binascii
bt = Bluetooth()
bt.start_scan(10)

for x in range(1, 10000):
    adv = bt.get_adv()
    if adv:
        print (binascii.hexlify(adv.mac))
        print("------------------------")
    time.sleep(0.01)
