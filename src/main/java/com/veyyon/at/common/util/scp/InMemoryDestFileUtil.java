package com.veyyon.at.common.util.scp;

import net.schmizz.sshj.xfer.InMemoryDestFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class InMemoryDestFileUtil extends InMemoryDestFile {

    public ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Override
    public ByteArrayOutputStream getOutputStream() throws IOException {
        return byteArrayOutputStream;
    }
}
