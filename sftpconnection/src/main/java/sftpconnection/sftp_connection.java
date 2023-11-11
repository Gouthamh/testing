package sftpconnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class sftp_connection {

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {

		System.out.println(false);
		// String files = null;
		String localPath = "";

		String sftpHost = "172.20.21.57";
		String sftpPort = "31704";
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshkey = "172.20.21.227 ssh-rsa AAAAB3NzaC1yc2EAAAADAQhgcvhbjsdscsadcABAAABgQDBOFACNqqkQKK8o4LuaOCiogpO0xwTdFpSqB8394dBJWu0LtrYA9LxjMCwBu7oGB4pcRfLvFBV0g+dVDMuq+2PDkvmyXLntnjZkTLQlH2B2dmxenRfB0QZVEqh86fmrUxbhbI0hDYJLx9YFs49vdYorg7zYOMu2fhnpzFoc8Icw907rJP1PVMQWpK0SeMWYLXhCajWm0oq6GmB0Gf0qwb+1SzaHmFyuhTd5jdK6Yk25rTR67R5WT5ElNW2ZoyI2kK/ILBKyvS1DyuhiHm/UJ4AHkA6DKLjfSPgE2JgOHZQ01+EeYYghyjbzeyv+fOehhajE6kqAYjGakrMB4FmGeY/wXgSmaUpNms3phBDNsgEwOi+UinfYxrauhNDWDMZWw/Fl0Lw17rUyczf8I5wwxvAffMUvaFwMVnR5Z3bkb7+iTPxpvRWmQfrlyhU9J+8tM9vUBWLxeg0dIRuWC1TJnh4vG4EHasDa17UFpsdC+vRluYfFeciHHMFKKHXQwJD860=";

		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
			
			Session session = jsch.getSession(sftpUserName, sftpHost, 22);
			session.setPassword(sftpPassword);
			
			
			System.out.println("------------");
			// jsch.setKnownHosts("knownhost");
			jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
			// jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
		
			// session.getConfig(sshkey.getBytes);
			//session.setPassword(sftpPassword);
			session.connect();
			System.out.println("------------>>>>>>");
			Channel channel = session.openChannel("sftp");
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.connect();
			//channelSftp.exit();
			System.out.println("------------connect-----------" + channelSftp.isConnected());
			ChannelSftp channelsftpconnection = (ChannelSftp) channelSftp;
			System.out.println("channelsftpconnection----------" + channelsftpconnection);
			channelsftpconnection.cd("/bulk-store/");
			System.out.println("remove----------");
			// channelsftpconnection.rm("/bulk-store/mysql3817660005.csv");
			// channelsftpconnection.rmdir("/bulk-store/mysql3817660005.csv");
			System.out.println("remove----------");
			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/bulk-store/");
			System.out.println("------------ls.size()-----------" + (ls.size() - 2));
			System.out.println("------------files-----------");

			for (ChannelSftp.LsEntry entry : ls) {
				// System.out.println("<--------->"+"/bulk-store/"+entry.getFilename());
				SftpATTRS attrs = channelSftp.lstat("/bulk-store/" + entry.getFilename());
				String sftppermission = attrs.getPermissionsString();
				System.out.println("sftppermission-----" + sftppermission);
				InputStream inputStream = channelSftp.get("/bulk-store/" + entry.getFilename());
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
				ArrayList<String> array = new ArrayList<String>();
				array.add(attrs.getPermissionsString());
				/*
				 * array.forEach((e) -> {
				 * 
				 * 
				 * if (e.equals("-rw-r--r--")) { System.out.println("<----fullpath----->");
				 * String files = null; try { while (((files = reader.readLine()) != null)) {
				 * 
				 * System.out.println("<----fullpath----->" + files);
				 * 
				 * } } catch (IOException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); }
				 * 
				 * } else { System.out.println("<----fullpath11----->"); }
				 * 
				 * 
				 * if (e.equals("-rw-r--r--")) { System.out.println("------" +
				 * entry.getFilename()); try { channelsftpconnection.rm("/bulk-store/" +
				 * entry.getFilename()); System.out.println("------" + entry.getFilename()); }
				 * catch (SftpException e1) { // TODO Auto-generated catch block
				 * e1.printStackTrace(); }
				 * 
				 * }
				 * 
				 * });
				 */
				
				for(String e : array) {
					System.out.println("<----fullpath----->");
					if(e.equals("-rw-r--r--")) {
						System.out.println("<----fullpath insdie----->");
						try {
							channelsftpconnection.rm("/bulk-store/" + entry.getFilename());
							System.out.println("------" + entry.getFilename());
						} catch (SftpException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
				}
				System.out.println("length"+array.size());

			}

			/*
			 * InputStream inputStream=channelSftp.get(
			 * "/bulk-store/name2023-04-101-05-19-47148000019.csv"); BufferedReader reader =
			 * new BufferedReader(new InputStreamReader(inputStream));
			 * //System.out.println("-------reader-----"+reader.readLine()); String files =
			 * null; while((files=reader.readLine()) != null) {
			 * System.out.println("files---------"+files);
			 * 
			 * }
			 */

			ls.size();
			System.out.println("------------ls.size()-----------" + ls.size());
			ls.clear();
			System.out.println("------------ls.removeAllElements()-----------" + ls.size());
			System.out.println("------------ls.removeAllElements()-----------" + ls.isEmpty());
			System.out.println();

			for (ChannelSftp.LsEntry entry : ls) {

				System.out.println("-------");

				System.out.println("entry.getFilename()------>" + entry.getFilename());
//				SftpATTRS attrs = channelSftp.lstat("/bulk-store/" + entry.getFilename());
//
//				String sftppermission = attrs.getPermissionsString();
//				InputStream inputStream = channelSftp.get("/bulk-store/" + entry.getFilename());
//				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); // //
//				System.out.println("-------reader-----" + reader.readLine());
//
//				/*
//				 * while (((files = reader.readLine()) != null)) {
//				 * System.out.println("files---------" + files);
//				 * 
//				 * }
//				 */
//
//				ArrayList<String> array = new ArrayList<String>();
//				array.add(attrs.getPermissionsString());
//
//				array.forEach((e) -> {
//					 System.out.println("Freak---->" + e);
//
//					if (e.equals("-rw-r--r--")) {
//						String files = null;
//						try {
//							while (((files = reader.readLine()) != null)) {
//								// System.out.println("entry.filename"+entry.getFilename());
//								while (reader.readLine().isEmpty() == false)
//								{
//									int lines = 0;
//									lines = lines + 1;
//									System.out.println("lines-------->" + lines);
//								}
//
//								System.out.println(files);
//
//							}
//						} catch (IOException e1) { // TODO Auto-generated catch block
//							e1.printStackTrace();
//						}
//
//						// System.out.println("else if ---->-rw-r--r--"); } // String test = e;
//
//						/*
//						 * if (test == "-rw-r--r--") {
//						 * 
//						 * System.out.println("inside the if condition---->"+test); } else {
//						 * System.out.println("inside the else condition---->"+test);
//						 * 
//						 * try { String files = null; while (((files = reader.readLine()) != null))
//						 * 
//						 * { } System.out.println("files---------" + files);
//						 * 
//						 * }
//						 * 
//						 * } catch (IOException e1) { // TODO Auto-generated catch block
//						 * e1.printStackTrace(); }
//						 */
//
//					}
//
//				});
//
//				Iterator<String> permission = array.iterator();
//
//				while (permission.hasNext()) {
//					System.out.println(
//							"-----------------------enter to arry while loop---------------------------------------------");
//
//				}
//
//			}
//
//			Vector filelist = channelSftp.ls("/bulk-store/ProccessedFiles/");
//			for (int i = 0; i < filelist.size(); i++) {
//				System.out.println("filelist.get(i).toString()---->" + filelist.get(i).toString());
			}
			channelSftp.exit();

		} catch (Exception e) {
			e.printStackTrace();

		}

		// TODO Auto-generated method stub

	}

}
