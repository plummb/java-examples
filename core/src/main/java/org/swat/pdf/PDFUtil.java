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
    if (password == null) {
      password = "";
    }

    PDDocument document = null;
    InputStream inputStream = null;
    try {
      final File inputFile = new File(inFile);
      inputStream = new FileInputStream(inputFile);
      if (inFile.endsWith(".zip")) {
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        zipInputStream.getNextEntry();
        inputStream = zipInputStream;
      }
      document = PDDocument.load(inputStream, password);

      int keyLength = 128;

      AccessPermission ap = AccessPermission.getOwnerAccessPermission();

      StandardProtectionPolicy spp = new StandardProtectionPolicy("", "", ap);
      spp.setEncryptionKeyLength(keyLength);
      spp.setPermissions(ap);
      document.protect(spp);
      File saveAs;
      if (outFile == null) {
        File file = new File(inFile);
        saveAs = new File(file.getParentFile(), "Decrypt-" + file.getName());
      } else {
        saveAs = new File(outFile);
      }
      document.setAllSecurityToBeRemoved(true);
      document.save(saveAs);
      saveAs.setLastModified(inputFile.lastModified());
    } catch (InvalidPasswordException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    } finally {
      IOUtils.closeQuietly(inputStream);
    }
  }
}
