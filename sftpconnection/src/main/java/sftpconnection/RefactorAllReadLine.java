package sftpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

public class RefactorAllReadLine {

	public static void main(String[] args) {
		String sftpHost = "172.20.21.57";
		String sftpPort = "31702";
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";

		String headers = "notificationId,emailTo,shortCode,msisdn,systemId,Name,DataPlan,amount";
		List<String> lines = new ArrayList<>();

		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));

			Session session = jsch.getSession(sftpUserName, sftpHost, Integer.valueOf(sftpPort));
			session.setPassword(sftpPassword);
			session.connect();

			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd("/bulk-store/");

			Vector<ChannelSftp.LsEntry> files = channelSftp.ls("*");
			for (ChannelSftp.LsEntry entry : files) {
				if (entry.getAttrs().isDir()) {
					continue; // Skip directories
				}

				if (!entry.getAttrs().getPermissionsString().equals("-rw-r--r--")) {
					continue; // Skip files with different permissions
				}

				InputStream inputStream = null;
				BufferedReader reader = null;
				try {
					inputStream = channelSftp.get(entry.getFilename());
					reader = new BufferedReader(new InputStreamReader(inputStream));

					String line;

					while ((line = reader.readLine()) != null) {
						lines.add(line);
					}
					System.out.println();
					System.out.println("line123---> "+lines);
				} finally {
					if (reader != null) {
						reader.close();
					}
					if (inputStream != null) {
						inputStream.close();
					}
				}
			}

			channelSftp.disconnect();
			session.disconnect();

			for (String line : lines) {
				System.out.println();
				System.out.println("lines---> "+line);
			}
			
			// Convert the lines list to a single string
						StringBuilder sb = new StringBuilder();
						for (String line : lines) {
							sb.append(line).append("\n");
						}
						String result = sb.toString();
						System.out.println();
						System.out.println("result--> "+result);
						
						if(result.contains(headers)) {
							System.out.println();
							System.out.println(true);
							
						}
						else {
							System.out.println();
							System.out.println(false);
						}
					
						

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
