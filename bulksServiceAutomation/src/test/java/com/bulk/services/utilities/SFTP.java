package com.bulk.services.utilities;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

public class SFTP {

	public static long SFTPCount(String sftpHost, int sftpPort, String sftpUserName, String sftpPassword, String sshkey,
			String sfthpath) {

		int fileCount = -1;
		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
			Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			session.setPassword(sftpPassword);
			session.connect();
			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd(sfthpath);
			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sfthpath);
			fileCount = ls.size() - 2; // subtract 2 for '.' and '..' entries
			System.out.println("File count: " + fileCount);
			channelSftp.exit();
			session.disconnect();
			return ls.size() - 2;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileCount;

	}

	public static void sftpfileexections(String sftpHost, int sftpPort, String sftpUserName, String sftpPassword,
			String sshkey, String sfthpath, String file) {
		{
			try {
				List<String> filesexc = new ArrayList<String>();
				String fileExtension = null;
				JSch jsch = new JSch();
				jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
				Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);

				session.setPassword(sftpPassword);
				session.connect();

				ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
				channelSftp.connect();
				channelSftp.cd(sfthpath);

				Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sfthpath);

				for (ChannelSftp.LsEntry entry : ls) {
					SftpATTRS attrs = entry.getAttrs();
					if (attrs.getPermissionsString().equals("-rw-r--r--")) {
						String filename = entry.getFilename();
						fileExtension = filename.substring(filename.lastIndexOf("."));
						filesexc.add(fileExtension);
					}
				}
				System.out.println("filesexc-->" + filesexc);

				if (filesexc.stream().distinct().count() == 1 && !filesexc.isEmpty()
						&& file.equals(filesexc.toString())) {
					System.out.println("true--->");

				} else {
					System.out.println("false--->");
				}

				channelSftp.disconnect();
				session.disconnect();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void sftpremove(String sftpHost, int sftpPort, String sftpUserName, String sftpPassword, String sshkey,
			String sfthpath) {
		
		 try {
	            JSch jsch = new JSch();
	            jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));

	            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
	            session.setPassword(sftpPassword);
	            session.connect();

	            System.out.println("Connected to SFTP server");

	            Channel channel = session.openChannel("sftp");
	            channel.connect();

	            System.out.println("Connected to SFTP channel");

	            ChannelSftp channelSftp = (ChannelSftp) channel;
	            channelSftp.cd(sfthpath);

	            System.out.println("Changed directory to " + sfthpath);

	            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sfthpath);

	            for (ChannelSftp.LsEntry entry : ls) {
	                SftpATTRS attrs = entry.getAttrs();
	                String permissions = attrs.getPermissionsString();

	                if (!permissions.equals("drwxrwxrwx")) {
	                    String filename = entry.getFilename();
	                    System.out.println("Deleting file: " + filename);
	                    channelSftp.rm(sfthpath + filename);
	                }
	            }

	            channelSftp.exit();
	            session.disconnect();
	            System.out.println("Disconnected from SFTP server");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}

	public static void main(String[] args) {

//		String sftpHost = "172.20.21.57";
//		int sftpPort = 31703;
//		String sftpUserName = "admin";
//		String sftpPassword = "admin";
//		String sshkey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
//		String sfthpath = "/bulk-store/";
//		String file = 
		
		String sftpHost = "172.20.21.57";
		int sftpPort = 31704;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
		String sftppath = "/bulk-store/ProccessedFiles/";
		String file = "[.gz]";

		long count = SFTPCount(sftpHost, sftpPort, sftpUserName, sftpPassword, sshKey, sftppath);
		System.out.println("---" + count);
		
		String sfthpath;
		//sftpfileexections(sftpHost, sftpPort, sftpUserName, sftpPassword, sshKey, sftppath,file);
		
		sftpfileexections( sftpHost,  sftpPort,  sftpUserName,  sftpPassword,sshKey,  sftppath,  file);
		System.out.println("---" + count);
		
		sftpremove( sftpHost,  sftpPort,  sftpUserName,  sftpPassword,sshKey,  sftppath);

		// TODO Auto-generated method stub

	}

}
