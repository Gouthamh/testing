package sftpconnection;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;

public class refactor_of_distant_code {

	public static void main(String[] args) {
		String sftpHost = "172.20.21.57";
		int sftpPort = 31702;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshkey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
			jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
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
				String sftpPermission = attrs.getPermissionsString();
				if (sftpPermission.equals("-rw-r--r--")) {
					InputStream inputStream = channelSftp.get("/bulk-store/" + entry.getFilename());
					BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
					int lines = 0;
					while (reader.readLine() != null) {
						lines++;
					}
					lineCounts.add(lines);
				}
			}

			Collections.sort(lineCounts);
			System.out.println("Sorted line counts: " + lineCounts);

			Map<Integer, Integer> lineCountFrequency = new HashMap<>();
			for (int i = 0; i < lineCounts.size(); i++) {
				int key = lineCounts.get(i);
				System.out.println("key-->"+key);
				//System.out.println("lineCountFrequency.get(key)-->"+lineCountFrequency.get(key));
				if (lineCountFrequency.containsKey(key)) {
					lineCountFrequency.put(key, lineCountFrequency.get(key) + 1);
				} else {
					lineCountFrequency.put(key, 1);
				}
			}

			for (Integer key : lineCountFrequency.keySet()) {
				System.out.println("Line count " + key + " occurs " + lineCountFrequency.get(key) + " times.");
			}

			channelSftp.exit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
