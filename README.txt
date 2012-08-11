Overview:

WiFi Direct File Transfer is a simple utility that will allow sharing of data between Android devices running Android 4.0 or 
higher using a WiFi direct connection without the use of a WiFi access point. This will enable data transfer between devices 
without relying on any existing network infrastructure. This application is intended to provide a much higher speed alternative 
to data transfer over Bluetooth.


Known Issues:

*A client can only begin a file transfer if it is not the P2P group owner. If the client is the P2P owner, it cannot open a TCP socket 
since the Android WiFi direct API does not provide a mechanism to retrieve the InetAddress (IP address) of any peer other than the P2P 
group owner. This issue can be resolved upon finding a means to determine the IP address of any given peer in the P2P group.  

*Every incoming WiFi direct requires explicit user authorization. This seems to be a limitation on the Android WiFi direct API which 
does not allow programmatic means of accepting incoming WiFi direct connection requests. 

*Scanning for WiFi direct peers sometimes causes random reboots on the Galaxy S2. This is not an issue on the Nexus 7. It is unclear
if this is caused by a fault in the program or if it is specific to the device. 

*Scanning for WiFi peers randomly takes an unusually long time.

*Transferred files do not match the original files name or extension

*The service service cannot be shut off via the stop server button

