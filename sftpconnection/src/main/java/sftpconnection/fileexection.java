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

public class fileexection {

	public static void main(String[] args) {

		String sftpHost = "172.20.21.57";
		int sftpPort = 31702;
		String sftpUserName = "admin";
		String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
		String sftppath = "/bulk-store/";
		String file = ".csv";
		

		try {
			List<String> filesexc = new ArrayList<>();
			String fileExtension = null;
			JSch jsch = new JSch();
			jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
			Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);

		
			session.setPassword(sftpPassword);
			session.connect();

			ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
			channelSftp.connect();
			channelSftp.cd(sftppath);

			Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sftppath);

			for (ChannelSftp.LsEntry entry : ls) {
				SftpATTRS attrs = entry.getAttrs();
				if (attrs.getPermissionsString().equals("-rw-r--r--")) {
					String filename = entry.getFilename();
					fileExtension = filename.substring(filename.lastIndexOf("."));
					filesexc.add(fileExtension);
				}
			}
			System.out.println("filesexc-->" + filesexc);

//
//			if (filesexc.stream().distinct().count() == 1 && !filesexc.isEmpty() && file.equals(filesexc.toString())) {
//				System.out.println("true--->");
//
//			} else {
//				System.out.println("false--->");
//			}

			if(filesexc==null || filesexc.size()==0) {
				System.out.println(true);
			}
			
			for (int i = 0; i < filesexc.size(); i++) {
				
		        if (!filesexc.get(i).equals(file)) {
		            System.out.println(false); // If any element is different, return false
		        }
		    }
			System.out.println(true);
			
				
			channelSftp.disconnect();
			session.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
