package sftpconnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;

public class sampledummi {

	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) {

		System.out.println(false);
		// String files = null;
		String localPath = "";

		String sftpHost = "172.20.21.57";
		String sftpPort = "31703";
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshkey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";

		try {
			JSch jsch = new JSch();
			Session session = jsch.getSession(sftpUserName, sftpHost, Integer.valueOf(sftpPort));
			System.out.println("------------");
			// jsch.setKnownHosts("knownhost");
			jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
			// jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
			// session.getConfig(sshkey.getBytes);
			session.setPassword(sftpPassword);
			session.connect();
			Channel channel = session.openChannel("sftp");
			ChannelSftp channelSftp = (ChannelSftp) channel;
			channelSftp.connect();
			// channelSftp.exit();
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
				//System.out.println("sftppermission-----" + sftppermission);
				ArrayList<String> array = new ArrayList<String>();
				array.add(attrs.getPermissionsString());
				long lines = 0;
				for (String e : array) {
					if (e.equals("-rw-r--r--")) {
						//System.out.println("name" + entry.getFilename());
						//File file = new File("/bulk-store/" + entry.getFilename());
						//System.out.println("file----->" + file);

						InputStream inputStream = channelSftp.get("/bulk-store/" + entry.getFilename());
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						
						System.out.println("reader--->"+reader);
						while(reader.readLine()!=null) lines++;
					}
					System.out.println("lines--->"+lines);
				}

			}

			System.out.println();

			channelSftp.exit();

		} catch (Exception e) {
			e.printStackTrace();

		}

		// TODO Auto-generated method stub

	}

}
