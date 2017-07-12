# JavaUACExample
This example shows how to grant a java program admin rights on windows using the jna library, and tests the capabilities by editing (more like replacing) the hosts system file in order to modify outgoing internet traffic.

## This release  (located in the release section of this repository) 
contains the code to execute the jar as administrator in windows with UAC enabled (it will automatically detect whatever admin rights are already present or not). It will then use the rights it gained to modify the "hosts" system file, replacing it with the hosts file in the path C:\mytempfolder\hosts.

jna files are also included separately for comfort, but the jar file contains a copy of them.

## JNA link:
https://github.com/java-native-access/jna/releases
