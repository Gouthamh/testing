package sftpconnection;

import com.jcraft.jsch.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class downloadfile {

	public static void main(String[] args) {
		String sftpHost = "172.20.21.57";
		String sftpPort = "31703";
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-ns"));

		try {
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
			Session session = jsch.getSession(sftpUserName, sftpHost, Integer.parseInt(sftpPort));
			session.setPassword(sftpPassword);
			session.connect();

			Channel channel = session.openChannel("sftp");
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.connect();

			channelSftp.cd("/bulk-store/");

			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/bulk-store/");
			List<Integer> lineCounts = new ArrayList<>();

			for (ChannelSftp.LsEntry entry : ls) {
				SftpATTRS attrs = channelSftp.lstat("/bulk-store/" + entry.getFilename());
				String permissions = attrs.getPermissionsString();

				if (permissions.equals("-rw-r--r--")) {
					try (BufferedReader reader = new BufferedReader(
							new InputStreamReader(channelSftp.get("/bulk-store/" + entry.getFilename())));
							BufferedWriter writer = new BufferedWriter(
									new FileWriter("file_" + timestamp + ".csv", true))) {

						String line;
						int lineCount = 0;

						while ((line = reader.readLine()) != null) {
							System.out.println(line);
							writer.write(line);
							writer.newLine();
							lineCount++;
						}

						lineCounts.add(lineCount);
					}

					String sourcePath = "file.csv";
					String destinationPath = "C:\\dap\\bulk\\write_" + timestamp + ".txt";
					try (FileInputStream fis = new FileInputStream(sourcePath);
							FileOutputStream out = new FileOutputStream(destinationPath)) {

						byte[] buffer = new byte[1024];
						int byteReader;
						while ((byteReader = fis.read(buffer)) != -1) {
							System.out.println("byteReader " + byteReader);
							out.write(buffer, 0, byteReader);
							System.out.println("buffer " + buffer);

						}
					}
				}
			}

			channelSftp.exit();
			session.disconnect();

			// System.out.println("Line counts: " + lineCounts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}