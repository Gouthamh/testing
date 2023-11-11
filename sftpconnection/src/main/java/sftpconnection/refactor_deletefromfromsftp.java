package sftpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class refactor_deletefromfromsftp {

	private static final String SFTP_HOST = "172.20.21.57";
	private static final String SFTP_PORT = "31702";
	private static final String SFTP_USERNAME = "admin";
	private static final String SFTP_PASSWORD = "admin";
	private static final String SFTP_DIRECTORY = "/bulk-store/";
	private static final String FILE_PERMISSIONS = "-rw-r--r--";
	private static final String FILE_PERMISSIONS1 = "rwxrwxrwx";
	//private static final String FILE_PERMISSIONS2 = "rw-r--r--";
	public static void main(String[] args) throws NumberFormatException, JSchException {
		try {
			ChannelSftp channelSftp = connectToSftpServer();
			if (channelSftp != null) {
				int deletedFilesCount = deleteFiles(channelSftp);
				System.out.println("Deleted " + deletedFilesCount + " files.");
				disconnectFromSftpServer(channelSftp);
			}
		} catch (IOException | SftpException e) {
			e.printStackTrace();
		}
	}

	private static ChannelSftp connectToSftpServer()
			throws IOException, SftpException, NumberFormatException, JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession(SFTP_USERNAME, SFTP_HOST, Integer.valueOf(SFTP_PORT));
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw=="; // TODO:
																																																																																																																																																																																																// Replace
																																																																																																																																																																																																// with
																																																																																																																																																																																																// actual
																																																																																																																																																																																																// SSH
																																																																																																																																																																																																// key"
		jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
		session.setPassword(SFTP_PASSWORD);
		session.connect();
		Channel channel = session.openChannel("sftp");
		channel.connect();
		if (channel.isConnected()) {
			System.out.println("Connected to SFTP server.");
			return (ChannelSftp) channel;
		}
		return null;
	}

	private static int deleteFiles(ChannelSftp channelSftp) throws SftpException {
		int deletedFilesCount = 0;
		Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(SFTP_DIRECTORY);
		for (ChannelSftp.LsEntry entry : ls) {
			String filename = entry.getFilename();
			if (!isParentOrCurrentDirectory(filename)) {
				SftpATTRS attrs = channelSftp.lstat(SFTP_DIRECTORY + filename);
				String permissions = attrs.getPermissionsString();
				System.out.println(permissions);
				deleteFile(channelSftp, filename);
				deletedFilesCount++;

				if (permissions.equals(FILE_PERMISSIONS) && permissions.equals(FILE_PERMISSIONS1)) {
					deleteFile(channelSftp, filename);
					deletedFilesCount++;
				}

			}
		}
		return deletedFilesCount;
	}

	private static void deleteFile(ChannelSftp channelSftp, String filename) throws SftpException {
		String filePath = SFTP_DIRECTORY + filename;
		System.out.println("Deleting file: " + filePath);
		channelSftp.rm(filePath);
		// channelSftp.rmdir(filePath);
	}

	private static boolean isParentOrCurrentDirectory(String filename) {
		return filename.equals(".") || filename.equals("..");
	}

	private static void disconnectFromSftpServer(ChannelSftp channelSftp) {
		channelSftp.exit();
		System.out.println("Disconnected from SFTP server.");
	}

}
