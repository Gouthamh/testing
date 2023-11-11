package sftpconnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jzlib.GZIPOutputStream;

public class fileexectiontarfiles {

    public static void main(String[] args) {

        String sftpHost = "172.20.21.57";
        List<String> filesToCompress = new ArrayList<>();
        int sftpPort = 31703;
        String sftpUserName = "admin";
        String sftpPassword = "admin";
		String sshKey = "172.20.21.57 ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAACAQCfeK+wXukXepUaNZmSTGEjDeUTmJdJlMepkUZA6m5p6tzJa77dIAEx1DOdS+uYCOQWJeguACk204p8Qg83w2Is/YtfzoprK9suIBuZLgDoFT+rSXWptVR3weHQqmwh9aN3AwcRN2ql+czgT3SjIMOahz9peLeRKe+20TiOLv8+c0h8+IQxkqX7qrY0sYrrZyJsQ9HQVrLGEb9RLmOgyKPnqCDIRFH72+nYSpcN+k7Yxc8GiL+qUtKs/GZwBYW2oQMVJR/7XeFVfMYgKBbh/R4L9hYztJX5jCn6iovAwhMB9bd0KqDHhJsiU39R8JoUeGXkW83j3/dZdO1QvTY+AQHmZRW7mZ5MInWeT6BwAJqYICfibSfD/0FYXG7fonPAlzAl6lDiMyBpmZnZOMn0vF8JgHPu1AUxgPrcbNbLhwl19qfz2GnIHoDfg2h8IPXhASh56oIGFSbREIeTYjx0JkJgkZsbGKMYKnEjbSApMUNfhUu16/khm+/tptDqmMDnYmU2E4SQ+Jici9FD1bGO2pDUS2Z6NQz5HyzQyhHPnSH/eQnowRc+YnXa15npey/EEsKE/NGEtS/xk1DXsqEk30wMZYEfwEga5YDCCwIEgzYpHIgF2MmCGYnb/AgtjPRQEYvAWz42S2yD1FNnmsxGxTvOfg60+PuzJW5l2l6zrzvPxw==";
        String fileExtension = ".csv";

        try {
            JSch jsch = new JSch();
            jsch.setKnownHosts(new ByteArrayInputStream(sshKey.getBytes()));
            Session session = jsch.getSession(sftpUserName, sftpHost, sftpPort);
            session.setPassword(sftpPassword);
            session.connect();

            ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd("/bulk-store/");

            Vector<ChannelSftp.LsEntry> ls = channelSftp.ls("/bulk-store/");

            for (ChannelSftp.LsEntry entry : ls) {
                if (entry.getAttrs().getPermissionsString().equals("-rw-r--r--")) {
                    String filename = entry.getFilename();
                    System.out.println("filename--->" + filename);
                    if (filename.endsWith(fileExtension)) {
                        filesToCompress.add(filename);
                        System.out.println("filesToCompress.add(filename) " + filesToCompress.add(filename));
                    }
                }
            }

            if (!filesToCompress.isEmpty()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                TarArchiveOutputStream tarOutputStream = new TarArchiveOutputStream(new GZIPOutputStream(baos));

                for (String filename : filesToCompress) {
                    InputStream fileInputStream = channelSftp.get("/bulk-store/" + filename);
                    SftpATTRS attrs = channelSftp.lstat("/bulk-store/" + filename);
                    
                    TarArchiveEntry tarEntry = new TarArchiveEntry(filename);
                    tarEntry.setSize(attrs.getSize());
                    tarOutputStream.putArchiveEntry(tarEntry);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        tarOutputStream.write(buffer, 0, bytesRead);
                    }
                    tarOutputStream.closeArchiveEntry();
                    fileInputStream.close();
                }

                byte[] tarData = baos.toByteArray();
                InputStream tarInputStream = new ByteArrayInputStream(tarData);
                String tarFilename = "compressed_files_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-ns")) + ".tar.gz";
                channelSftp.put(tarInputStream, "/bulk-store/" + tarFilename);
                tarInputStream.close();
                baos.close();

                System.out.println("Files compressed and uploaded successfully!");
            } else {
                System.out.println("No files found to compress.");
            }

            channelSftp.disconnect();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
