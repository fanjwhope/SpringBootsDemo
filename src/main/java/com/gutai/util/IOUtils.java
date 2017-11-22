
package com.gutai.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * @author DeserveL
 * @date 2017/7/5 16:22
 * @since 1.0.0
 */
public class IOUtils {

    static void writeStream(String content, String charset, OutputStream outputStream) throws IOException {
        if (content != null) {
            outputStream.write(content.getBytes(charset));
            outputStream.flush();
        }
    }

    static String readStream(HttpURLConnection connection, String charset) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return IOUtils.readStream(connection.getInputStream(), charset);
        } else {
            return IOUtils.readStream(connection.getErrorStream(), charset);
        }
    }

    static String readStream(InputStream inputStream, String charset) throws IOException {
        byte[] result = readStreamBytes(inputStream);
        if (result == null) {
            return null;
        }
        return new String(result, charset);
    }

    static byte[] readStreamBytes(HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return IOUtils.readStreamBytes(connection.getInputStream());
        } else {
            return IOUtils.readStreamBytes(connection.getErrorStream());
        }
    }

    static byte[] readStreamBytes(InputStream inputStream) throws IOException {
        byte[] cache = new byte[2048];
        int len;
        byte[] bytes = new byte[0];
        while ((len = inputStream.read(cache)) > 0) {
            byte[] temp = bytes;
            bytes = new byte[bytes.length + len];
            System.arraycopy(temp, 0, bytes, 0, temp.length);
            System.arraycopy(cache, 0, bytes, temp.length, len);
        }
        if (bytes.length == 0) {
            return new byte[0];
        }
        return bytes;
    }

    static byte[] readStreamBytesAndClose(InputStream inputStream) throws IOException {
        byte[] bytes = readStreamBytes(inputStream);
        inputStream.close();
        return bytes;
    }
}
