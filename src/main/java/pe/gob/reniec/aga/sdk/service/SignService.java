package pe.gob.reniec.aga.sdk.service;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import pe.gob.reniec.aga.sdk.common.Constants;
import pe.gob.reniec.aga.sdk.common.Utils;
import pe.gob.reniec.aga.sdk.dto.*;
import pe.gob.reniec.aga.sdk.utils.ConvertResponse;
import pe.gob.reniec.aga.sdk.utils.MySSLConnectionSocketFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alexander Llacho
 */
public class SignService {

    private static SignService __instance = null;

    private ConfigAga configAga;
    private Utils utils;

    private SignService(ConfigAga configAga) {
        this.configAga = configAga;
        this.utils = Utils.getInstance();
    }

    public static SignService getInstance(ConfigAga configAga) {
        if (__instance == null) {
            __instance = new SignService(configAga);
        }

        return __instance;
    }

    public byte[] procSignAga(File file) {
        try {
            String pathDir = utils.createTempDir();

            File fileSignMetadata = signFile(file, pathDir);

            if (fileSignMetadata != null) {
//                utils.deleteDirectory(new File(pathDir));
                return Files.readAllBytes(Paths.get(fileSignMetadata.getPath()));
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }

    private File signFile(File file, String pathDir) {
        File targetFile = new File(pathDir, Constants.FILE_SIGN);

        try {
            utils.generateTempFiles(pathDir, this.configAga, file);

            File fileZip = new File(pathDir, Constants.FILE_ZIP);
            CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(MySSLConnectionSocketFactory.getConnectionSocketFactory()).build();

            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("uploadFile", fileZip, ContentType.create("application/octet-stream"), fileZip.getName())
                    .build();

            HttpPost request = new HttpPost(this.configAga.getAgaUri());
            request.setEntity(entity);

            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == 200) {
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(IOUtils.toByteArray(response.getEntity().getContent()));
                }

                return targetFile;
            } else {
                System.out.println(ConvertResponse.getInstance().convertToString(response));
            }
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }

        return null;
    }
}
