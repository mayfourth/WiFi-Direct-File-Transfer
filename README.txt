 WiFi Direct File Transfer is an open source application that will enable sharing 
 of data between Android devices running Android 4.0 or higher using a WiFi direct
 connection without the use of a separate WiFi access point.This will enable data 
 transfer between devices without relying on any existing network infrastructure. 
 This application is intended to provide a much higher speed alternative to Bluetooth
 file transfer. 

 Copyright (C) 2012  Teja R. Pitla
 Contact: teja.pitla@gmail.com

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>




Known Issues:

*A client can only begin a file transfer if it is not the P2P group owner. If the client 
is the P2P owner, it cannot open a TCP socket since the Android WiFi direct API does not 
provide a mechanism to retrieve the InetAddress (IP address) of any peer other than the 
P2P group owner. This issue can be resolved upon finding a means to determine the IP 
address of any given peer in the P2P group.  

*Every incoming WiFi direct requires explicit user authorization. This seems to be a 
limitation on the Android WiFi direct API which does not allow programmatic means of 
accepting incoming WiFi direct connection requests. 

*Transferred files do not match the original files name or extension.

*Scanning for WiFi direct peers sometimes causes random reboots on the Galaxy S2. 
This is not an issue on the Nexus 7. It is unclearif this is caused by a fault in 
the program or if it is specific to the device. 

*Scanning for WiFi peers randomly takes an unusually long time.

*The server service cannot be shut off via the stop server button.



Upcoming Updates:

*Fix known issues.

*Add progress bar for downloads.

*Revamped GUI.

*Support for file sharing with more than 1 peer. 





