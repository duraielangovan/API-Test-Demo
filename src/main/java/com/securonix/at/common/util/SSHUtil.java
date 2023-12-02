package com.securonix.at.common.util;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SSHUtil {

    private final String server;
    private final String userName;
    private final String password;
    private final SSHClient sshClient;
    protected Logger logger = LoggerFactory.getLogger(SSHUtil.class);

    public SSHUtil(SSHClient sshClient, String server, String userName, String password) {
        this.sshClient = sshClient;
        this.server = server;
        this.userName = userName;
        this.password = password;
        sshClient.addHostKeyVerifier(new PromiscuousVerifier());
     }

    public SSHClient getSshClient() {
        return sshClient;
    }

    public void connectSSH() throws IOException {

        if(!sshClient.isConnected()){
            sshClient.connect(server);
            sshClient.authPassword(userName,password);
        }else{
            logger.info("already connected");
        }
     }

     public void disConnectSSH() throws IOException {

         if(sshClient.isConnected()){
             sshClient.disconnect();
         }else{
             logger.info("already disconnected");
         }
     }

}
