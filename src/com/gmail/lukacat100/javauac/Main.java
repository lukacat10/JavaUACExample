package com.gmail.lukacat100.crackedpremium;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.JOptionPane;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.Kernel32Util;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShellAPI;
import com.sun.jna.platform.win32.ShellAPI.SHELLEXECUTEINFO;
import com.sun.jna.win32.W32APIOptions;

public class Main {
	
	
	
	private static boolean checkPrivileges() {
	    File testPriv = new File("C:\\Program Files\\");
	    if (!testPriv.canWrite()) return false;
	    File fileTest = null;
	    try {
	        fileTest = File.createTempFile("test", ".dll", testPriv);
	    } catch (IOException e) {
	        return false;
	    } finally {
	        if (fileTest != null)
	            fileTest.delete();
	    }
	    return true;
	}
	
	public static void main(String[] args) {
		
		if(!checkPrivileges()){
			//Path to this jar (might not work inside ide...
			String jarPath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String decodedPath;
			try {
				
				decodedPath = URLDecoder.decode(jarPath, "UTF-8");
				decodedPath = decodedPath.substring(1, decodedPath.length());
	            //Command for shell
	            String CMD = System.getProperty("java.home") + "\\bin\\java";//java path...
	            String argsForCMD = "-jar " + "\"" + decodedPath + "\"";//Arguments required to execute the jar file.
	            JOptionPane.showMessageDialog(null, CMD + "|" + argsForCMD);//Optional...
	            //Execute as admin
				Shell32 INSTANCE = (Shell32) Native.loadLibrary("shell32", Shell32.class, W32APIOptions.UNICODE_OPTIONS);//loading the system shell (kind of similar to the Command Prompt)
				ShellAPI.SHELLEXECUTEINFO shellinfo = new SHELLEXECUTEINFO();
				//Info for execution:
				shellinfo.lpFile = CMD;
				shellinfo.lpParameters = argsForCMD;
				//Those infos dont matter much, except maybe disabling the dark windows which appears for a sec...
				shellinfo.nShow = 10;
				shellinfo.fMask = 0x00000040;
				shellinfo.lpVerb = "runas";
				boolean result = INSTANCE.ShellExecuteEx(shellinfo);
				if (!result)
		        {
		            int lastError = Kernel32.INSTANCE.GetLastError();
		            String errorMessage = Kernel32Util.formatMessageFromLastErrorCode(lastError);
		            throw new RuntimeException("Error performing elevation: " + lastError + ": " + errorMessage + " (apperror=" + shellinfo.hInstApp + ")");
		        }
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
		File yourHostsFile = new File("\\mytempfolder\\hosts");
		File systemHostsFile = new File("\\Windows\\system32\\drivers\\etc\\hosts");
		
		try {
			
			Files.copy(yourHostsFile.toPath(), systemHostsFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block	
			e.printStackTrace();
		}
	}

}
