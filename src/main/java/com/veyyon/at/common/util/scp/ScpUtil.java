package com.veyyon.at.common.util.scp;

import com.veyyon.at.common.util.SSHUtil;
import com.veyyon.at.common.util.SerializationUtil;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.xfer.FileSystemFile;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.Objects;

public  abstract class ScpUtil {

    protected String fileName=null;
    protected String filePath=null;
    protected String server=null;
    protected String userName;
    protected String password;
    protected byte[] byteArray;
    protected String destinationFilePath=null;


    public ScpUtil(String server, String userName, String password) {
        this.server = server;
        this.userName = userName;
        this.password = password;
    }
    public ScpUtil(String filePath, String fileName, String server, String userName, String password) {
        this(server,userName,password);
        this.fileName = fileName;
        this.filePath=filePath;
    }

    public ScpUtil(String filePath, String fileName, String server, String userName, String password,
                   String destinationFilePath) {
        this(filePath,fileName,server,userName,password);
        this.destinationFilePath = destinationFilePath;
    }

    public <T> T getScpObject(T clazz) throws JAXBException, IOException {
        download();
        String fileContent = new String(byteArray);
        return SerializationUtil.deserializeString(fileContent,clazz);
    }

    public void getSCPFile() throws IllegalAccessException, IOException {
        if(!Objects.equals(destinationFilePath,null)){
            download();
        }else{
            throw new IllegalAccessException("Destination path must be specified");
        }
    }
    protected void download() throws IOException {

        SSHUtil ssh = new SSHUtil(new SSHClient(),server,userName,password);
        InMemoryDestFileUtil inMemoryDestFile= new InMemoryDestFileUtil();
        ssh.connectSSH();
        try{
            if(Objects.equals(destinationFilePath,null)){
                ssh.getSshClient().newSCPFileTransfer().newSCPDownloadClient()
                        .copy(this.filePath+this.fileName,inMemoryDestFile);
               this.byteArray = inMemoryDestFile.getOutputStream().toByteArray();
            }else{
                ssh.getSshClient().newSCPFileTransfer().download(this.filePath+this.fileName,
                        new FileSystemFile(destinationFilePath));
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            ssh.disConnectSSH();
        }

    }


}
