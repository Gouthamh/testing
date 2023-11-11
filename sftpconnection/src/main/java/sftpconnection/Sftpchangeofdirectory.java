package sftpconnection;

import java.util.Vector;

import com.jcraft.jsch.*;

public class Sftpchangeofdirectory {
	public static void main(String[] args) {
		String sftpHost = "172.20.21.227";
		int sftpPort = 22;
		String sftpUserName = "tecnotree";
		String sftpPassword = "tecnotree@123";

		Session session = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			session.setConfig("StrictHostKeyChecking", "no");
			session.setPassword(sftpPassword);
			session.connect();

			channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();

			System.out.println("Connected to SFTP server.");

			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/home/tecnotree/");
			for (ChannelSftp.LsEntry entry : ls) {
				if (entry.getAttrs().isDir()) {
					System.out.println(entry);
				}
			}

			channelSftp.pwd();
			System.out.println("-----------------------------");
			System.out.println(channelSftp.pwd());

			System.out.println("=============================================");

			channelSftp.cd("/");
			channelSftp.pwd();
			System.out.println(channelSftp.pwd());

			System.out.println("___________++++++++++++___________");

			channelSftp.cd("/data/");
			channelSftp.pwd();
			System.out.println(channelSftp.pwd());

		} catch (JSchException | SftpException e) {
			e.printStackTrace();
		} finally {
			if (channelSftp != null && channelSftp.isConnected()) {
				channelSftp.disconnect();
			}
			if (session != null && session.isConnected()) {
				session.disconnect();
			}
		}
	}
}
