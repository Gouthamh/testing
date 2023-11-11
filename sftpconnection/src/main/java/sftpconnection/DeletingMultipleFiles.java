package sftpconnection;

import com.jcraft.jsch.*;

import java.io.ByteArrayInputStream;
import java.util.Vector;

public class DeletingMultipleFiles {

    public static void main(String[] args) {
        String sftpHost = "172.20.21.57";
        int sftpPort = 31702;
        String sftpUserName = "admin";
        String sftpPassword = "admin";
        String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
        String sftpPath = "/bulk-store/";

        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));

            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
            session.setPassword(sftpPassword);
            session.connect();

            System.out.println("Connected to SFTP server");

            Channel channel = session.openChannel("sftp");
            channel.connect();

            System.out.println("Connected to SFTP channel");

            ChannelSftp channelSftp = (ChannelSftp) channel;
            channelSftp.cd(sftpPath);

            System.out.println("Changed directory to " + sftpPath);

            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sftpPath);

            for (ChannelSftp.LsEntry entry : ls) {
                SftpATTRS attrs = entry.getAttrs();
                String permissions = attrs.getPermissionsString();
                System.out.println("permissions---"+permissions);

                if (!permissions.equals("drwxr-xr-x") && !permissions.equals("drwxrwxrwx")) {
                    String filename = entry.getFilename();
                    System.out.println("Deleting file: " + filename);
                    channelSftp.rm(sftpPath + filename);
                }
            }

            channelSftp.exit();
            session.disconnect();
            System.out.println("Disconnected from SFTP server");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
