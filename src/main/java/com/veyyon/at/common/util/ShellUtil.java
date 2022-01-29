package com.veyyon.at.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class ShellUtil {

    private static String password;

    public static String getPassword(String shellScriptPath) throws IOException {

        Runtime runtime= Runtime.getRuntime();
        Process process = runtime.exec("powershell "+shellScriptPath);
        InputStream inputStream = process.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String line;
        while(!Objects.equals(line=bufferedReader.readLine(),null)){
            password = line;
            System.out.println(password);
        }
        bufferedReader.close();
        process.getOutputStream().close();
        return password;
    }
}
