package sftpconnection;

import com.jcraft.jsch.*;
import java.io.*;
import java.util.*;

public class frequencyOfOccurrenceForEachLineCount {
    public static int frequencyOfOccurrenceForEachLineCount(String sftpUserName, String sftpPassword, String sftpHost, int sftpPort, String sshKey, String sftpPath) {
        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
            session.setPassword(sftpPassword);
            session.connect();

            Channel channel = session.openChannel("sftp");
            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.connect();

            channelSftp.cd(sftpPath);
            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sftpPath);

            List<Integer> lineCounts = new ArrayList<>();
            
            for (ChannelSftp.LsEntry entry : ls) {
            	
                SftpATTRS attrs = channelSftp.lstat(sftpPath + entry.getFilename());
                System.out.println("attrs---> "+attrs);
                System.out.println();
                String sftpPermission = attrs.getPermissionsString();
                System.out.println("sftpPermission---> "+sftpPermission);
                System.out.println(entry.getFilename());
                System.out.println();

                if (sftpPermission.equals("-rw-r--r--")) {
                    InputStream inputStream = channelSftp.get(sftpPath + entry.getFilename());
                   // System.out.println("inputStream--> "+inputStream);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                   // System.out.println("reader--> "+reader);
                    int lines = 0;
                    while (reader.readLine() != null) {
                        lines++;
                       // System.out.println("lines---> "+lines);
                    }
                    lineCounts.add(lines);
                }
            }

            Collections.sort(lineCounts);
            System.out.println("Sorted line counts: " + lineCounts);

            Map<Integer, Integer> lineCountFrequency = new HashMap<>();
            for (int key : lineCounts) {
            	//System.out.println(key);
                lineCountFrequency.put(key, lineCountFrequency.getOrDefault(key, 0) + 1);
            }

            int maxFrequency = 0;
            for (int frequency : lineCountFrequency.values()) {
                if (frequency > maxFrequency) {
                    maxFrequency = frequency;
                }
            }

            channelSftp.exit();
            session.disconnect();

            return maxFrequency;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        String sftpHost = "172.20.21.57";
        int sftpPort = 31702;
        String sftpUserName = "admin";
        String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";

        int maxFrequency = frequencyOfOccurrenceForEachLineCount(sftpUserName, sftpPassword, sftpHost, sftpPort, sshKey, "/bulk-store/");
        System.out.println("Maximum frequency of occurrence: " + maxFrequency);
    }
}
