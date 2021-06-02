package utilities;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnzipFile{
    private static final int BUFFER_SIZE = 4096;


    private TextArea textArea;

    public UnzipFile(TextArea textArea) { this.textArea = textArea; }

    public void unzip(InputStream zipFile, String destDirectory){
        try {
            File destDir = new File(destDirectory);
            if (!destDir.exists()) {
                destDir.mkdir();
            }
            ZipInputStream zipIn = new ZipInputStream(zipFile);

            ZipEntry entry = zipIn.getNextEntry();

            while (entry != null) {
                String filePath = destDirectory + File.separator + entry.getName();
                if (!entry.isDirectory()) {

                    new File(filePath).getParentFile().mkdirs();
                    extractFile(zipIn, filePath);
                    textArea.appendText(filePath + "\n" + "\n");


                } else {

                    File dir = new File(filePath);
                    textArea.appendText(filePath + "\n" + "\n");


                    dir.mkdirs();
                }

                zipIn.closeEntry();
                entry = zipIn.getNextEntry();
            }

            zipIn.close();
        } catch (IOException exception) {

            //FALLISCE SE NON RIESCE AD UNZIPPARE I FILE
            //OPPURE SE NON RIESCE A COPIARE TUTTO NELLA CARTELLA SELEZIONATA

            exception.printStackTrace();
        }
    }

        private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1) {
                bos.write(bytesIn, 0, read);
            }
            bos.close();
        }

}
