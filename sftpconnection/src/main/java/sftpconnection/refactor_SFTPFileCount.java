package sftpconnection;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class refactor_SFTPFileCount {

    public static void main(String[] args) {
    	
    	String sftpHost = "172.20.43.208";
		int sftpPort = 31721;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshkey = "10.43.221.95 b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAAAMwAAAAtzc2gtZW QyNTUxOQAAACCxsjOgh9zLWBLH7/Df/7sxhwVOgF1lSSZhKiOxcgEewAAAAKBSNfhJUjX4 SQAAAAtzc2gtZWQyNTUxOQAAACCxsjOgh9zLWBLH7/Df/7sxhwVOgF1lSSZhKiOxcgEewA AAAECiFC1GmW6xvi/6C55qD27MT2ltEEh0dlSgvD/4AD1Uk7GyM6CH3MtYEsfv8N//uzGH BU6AXWVJJmEqI7FyAR7AAAAAG3RlY25vdHJlZUBjYzg0OS1uZ2ItbHhjLTIxMwEC";
		
        try {
        	 System.out.println("--------");
            JSch jsch = new JSch();
            ////
            System.out.println("--------1");
            
            jsch.setConfig("kex", "diffie-hellman-group-exchange-sha1");
            jsch.setConfig("cipher.s2c", "aes256-ctr");
            jsch.setConfig("cipher.c2s", "aes256-ctr");
            jsch.setConfig("mac.s2c", "hmac-md5");
            jsch.setConfig("mac.c2s", "hmac-md5");
            
      
         
            
            ///
            jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
            System.out.println("--------2");
            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
            System.out.println("--------3");
            session.setPassword(sftpPassword);
            System.out.println("--------4");
            
            
            
            session.connect();
            System.out.println("--------5");
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            System.out.println("--------6");
            channelSftp.connect();
            System.out.println("--------7");
            ////
            jsch.setConfig("kex", "diffie-hellman-group-exchange-sha1");
            jsch.setConfig("cipher.s2c", "aes256-ctr");
            jsch.setConfig("cipher.c2s", "aes256-ctr");
            jsch.setConfig("mac.s2c", "hmac-md5");
            jsch.setConfig("mac.c2s", "hmac-md5");
         
            ////
            channelSftp.cd("/UsageEvents/");
            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/UsageEvents");
            int fileCount = ls.size() - 2; // subtract 2 for '.' and '..' entries
            System.out.println("File count: " + fileCount);
            channelSftp.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
