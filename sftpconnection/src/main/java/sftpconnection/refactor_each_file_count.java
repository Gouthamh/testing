package sftpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

public class refactor_each_file_count {
	public static void main(String[] args) {
		String sftpHost = "10.43.221.95";
		int sftpPort = 22;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "192.168.32.241 AAAAC3NzaC1lZDI1NTE5AAAAIPbgFE9oGSEAQg0NKO2mba061PTH8kD5ParfIUmPZFRA";
		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));

			Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			session.setPassword(sftpPassword);
			session.connect();

			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			
			System.out.println("-----pwd---"+channelSftp.pwd());
			//channelSftp.mkdir("/data/");

			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/bulk-store/");

			List<Integer> lineCounts = new ArrayList<>();
			for (ChannelSftp.LsEntry entry : ls) {
				SftpATTRS attrs = entry.getAttrs();
				String sftpPermission = attrs.getPermissionsString();
				if (sftpPermission.equals("-rw-r--r--")) {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(channelSftp.get("/bulk-store/" + entry.getFilename())));
					int lines = 0;
					while (reader.readLine() != null) {
						lines++;
					}
					lineCounts.add(lines);
				}
			}

			System.out.println(lineCounts.toString());
			System.out.println("-------"+lineCounts.size());

			channelSftp.exit();
			session.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}