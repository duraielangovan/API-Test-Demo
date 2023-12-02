package com.securonix.at.common.util.scp;

import java.io.IOException;

public class ScpProfiles extends ScpUtil{

    public ScpProfiles(String server, String userName, String password) {
        super(server, userName, password);
    }

    public ScpProfiles(String filePath, String fileName, String server, String userName, String password) {
        super(filePath, fileName, server, userName, password);
    }
    public ScpProfiles(String filePath, String fileName, String server, String userName, String password, String destinationFilePath) {
        super(filePath, fileName, server, userName, password, destinationFilePath);
    }

    public String getXMLFileContent() throws IllegalAccessException, IOException {
     super.download();
        return stripNonXMLCharacters(new String(byteArray));
    }

    private String stripNonXMLCharacters(String inData){
        if (inData == null || ("".equals(inData))) {
            return "";
        }
        StringBuilder cleanText = new StringBuilder(); // Used to hold the output.
        char current;
        for (int i = 0; i < inData.length(); i++)
        {
            current = inData.charAt(i);
            if ((current == 0x9) || (current == 0xA) || (current == 0xD)
                    || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF)))
            {
                cleanText.append(current);
            }
        }
        return cleanText.toString();
    }
}
