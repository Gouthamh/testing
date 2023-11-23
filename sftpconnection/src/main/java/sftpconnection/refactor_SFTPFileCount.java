package sftpconnection;

import java.io.ByteArrayInputStream;
import java.util.Vector;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class refactor_SFTPFileCount {

    public static void main(String[] args) {
    	
    	   String sftpHost = "192.168.32.241";
           int sftpPort = 30927;
           String sftpUserName = "admin";
           String sftpPassword = "admin";
           String sshkey = "192.168.32.241 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
           
           String sftpPath = "/bulk-store/";
    	
        try {
        	 System.out.println("--------");
            JSch jsch = new JSch();
            ////
            System.out.println("--------1");
            
   
      
         
            
            ///
            jsch.setKnownHosts(new ByteArrayInputStream(sshkey.getBytes()));
            System.out.println("--------2");
            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
            System.out.println("--------3");
            session.setPassword(sftpPassword);
            System.out.println("--------4");
            
            
            
            session.connect();
            System.out.println("--------5");
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            System.out.println("--------6");
            channelSftp.connect();
            System.out.println("--------7");
            ////
     
         
            ////
            channelSftp.cd(sftpPath);
            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls(sftpPath);
            int fileCount = ls.size() - 2; // subtract 2 for '.' and '..' entries
            System.out.println("File count: " + fileCount);
            channelSftp.exit();
            session.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
