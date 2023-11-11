package sftpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

public class sample {

	public static void main(String[] args) {

		String sftpHost = "172.20.21.57";
		Integer linecount;
		int sftpPort = 31703;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";

		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
			Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);

			// Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			session.setPassword(sftpPassword);
			session.connect();

			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd("/bulk-store/");

			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/bulk-store/");

			for (ChannelSftp.LsEntry entry : ls) {
				SftpATTRS attrs = entry.getAttrs();
				if (attrs.getPermissionsString().equals("-rw-r--r--")) {
					String filename = entry.getFilename();
					String fileExtension = filename.substring(filename.lastIndexOf("."));
					//System.out.println("Filename: " + filename);
					//System.out.println("File extension: " + fileExtension);
					System.out.println("Extension is .txt: " + fileExtension.equals(".csv"));
				}
			}

			channelSftp.disconnect();
			session.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
