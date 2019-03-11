package data_ingestion.app.helper;

import java.io.*;

import static org.apache.commons.lang3.CharEncoding.UTF_8;

public class Helper {
    public static String readFile(String filename) throws IOException {
        String content;
        File file = new File(filename);
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return content;
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        try(ByteArrayOutputStream result = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }

            return result.toString(UTF_8);
        }
    }
}