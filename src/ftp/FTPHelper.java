/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPSClient;

/**
 *
 * @author KRN
 */
public class FTPHelper {

    private String username = "karan";
    private String password = "karan";
    private String mode;
    private String url = "localhost";
    private boolean ftps;
    private FTPClient ftp;

    public boolean isFtps() {
        return ftps;
    }

    public void setFtps(boolean ftps) {
        this.ftps = ftps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() throws IOException {
        if (isFtps()) {
            ftp = new FTPSClient();

        } else {

            ftp = new FTPClient();
            ftp.connect(url);
            ftp.login(username, password);

        }

    }

    public void setDirectory(String dirs) throws IOException {

        if (!ftp.changeWorkingDirectory(dirs)) {
            ftp.makeDirectory(dirs);
            ftp.changeWorkingDirectory(dirs);
        }

    }

    public void uploadFile(String fileName, InputStream in) throws IOException {
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.setKeepAlive(true);
        ftp.setBufferSize(1024 * 1024 * 10);
        if (ftp.storeFile(fileName, in)) {
            in.close();
        }

    }

    public void downloadFile(OutputStream out, String filename) throws IOException {
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.setKeepAlive(true);
        ftp.setBufferSize(1024 * 1024 * 10);
        ftp.retrieveFile(filename, out);

    }

    public InputStream getFileInputStream(String filename) throws IOException {
       ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
       ftp.enterLocalPassiveMode(); 
        ftp.setKeepAlive(true);
        return ftp.retrieveFileStream(filename);

    }
    
    public void completePendingCommand() throws IOException{
    
        ftp.completePendingCommand();
    }

    public void disconnect() throws IOException {
        ftp.disconnect();

    }

}
