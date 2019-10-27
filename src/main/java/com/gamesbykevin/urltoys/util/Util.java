package com.gamesbykevin.urltoys.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Util {

    //how many bytes to read when downloading files
    private static final int BUFFER_SIZE = 4096;

    //default save directory
    public static final String SAVE_DIR_DEFAULT = "C:\\Users\\Kevin\\Desktop";

    //make sure all text case is the same otherwise we may have problems
    public static String convertText(String text) {
        return text.toLowerCase();
    }

    public static String getRandomDirectoryName() {
        return "folder_" + System.nanoTime();
    }

    public static void createDirectory(String path) {

        //create the object representing our directory
        File file = new File(path);

        //create the directory(s) if they don't exist
        if (!file.exists())
            file.mkdirs();
    }

    public static void download(String fileUrl) {
        download(fileUrl, SAVE_DIR_DEFAULT);
    }

    public static void download(String fileURL, String saveDir) {

        try {

            URL url = new URL(fileURL);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();

            // always check HTTP response code first
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0)
                        fileName = disposition.substring(index + 9);
                } else {
                    // extracts file name from URL
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1);
                }

                //remove illegal characters
                fileName = fileName.replace("\"", "");
                fileName = fileName.replace(";", "");

                System.out.println("Content-Type = " + contentType);
                System.out.println("Content-Disposition = " + disposition);
                System.out.println("Content-Length = " + contentLength);
                System.out.println("Downloading = " + fileName);

                //create directory if it doesn't exist
                createDirectory(saveDir);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = saveDir + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int count = 0;
                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {

                    //keep track of how many bytes we read
                    count += bytesRead;

                    //what is our progress downloaded
                    int progress = (int)(((double)count / (double)contentLength) * 100);

                    System.out.print(progress + "%" + "\r");
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                System.out.println("File downloaded = " + fileName);

            } else {
                System.out.println("No file to download. Server replied HTTP code: " + responseCode);
            }

            httpConn.disconnect();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void removeDuplicates(List<String> list) {

        while (true) {

            boolean match = false;

            for (int i = 0; i < list.size(); i++) {

                for (int j = 0; j < list.size(); j++) {

                    if (i == j)
                        break;

                    //if they match they are a duplicate
                    if (list.get(i).equals(list.get(j))) {
                        match = true;
                        list.remove(j);
                        break;
                    }
                }
            }

            //if no more matches we are done
            if (!match)
                break;
        }
    }

    public static void print(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}