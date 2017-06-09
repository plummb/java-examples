package org.swat.pdf;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * Created by swat
 * on 10/6/17.
 */
public class PDFUtil {
    public void decrypt(String inFile, String outFile, String password) throws Exception {
        if (inFile == null) {
            System.out.println("inFile cannot be null");
            return;
        }
        if (outFile == null) {
            outFile = "Decrypt-" + inFile;
        }
        if (password == null) {
            password = "";
        }

        PDDocument document = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(inFile);
            if (inFile.endsWith(".zip")) {
                ZipInputStream zipInputStream = new ZipInputStream(inputStream);
                zipInputStream.getNextEntry();
                inputStream = zipInputStream;
            }
            document = PDDocument.load(inputStream, password);

            int keyLength = 128;

            AccessPermission ap =  AccessPermission.getOwnerAccessPermission();

            StandardProtectionPolicy spp = new StandardProtectionPolicy("", "", ap);
            spp.setEncryptionKeyLength(keyLength);
            spp.setPermissions(ap);
            document.protect(spp);
            File saveAs = new File(outFile);
            document.save(saveAs);
        } catch (InvalidPasswordException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(document);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
