package pe.gob.reniec.aga.sdk;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pe.gob.reniec.aga.sdk.dto.ConfigAga;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Alexander Llacho
 */
public class SignTest {

    private String tempDir = System.getProperty("java.io.tmpdir");
    private ReniecAgaClient reniecAgaClient;

    @Before
    public void before() throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(getClass().getClassLoader().getResource("application.properties").getFile()));

        ConfigAga oConfigAga = new ConfigAga();
        oConfigAga.setAgaUri(properties.getProperty("aga.uri"));
        oConfigAga.setTimestamp(properties.getProperty("aga.timestamp"));
        oConfigAga.setCertificateId(properties.getProperty("aga.certificate.id"));
        oConfigAga.setSecretPassword(properties.getProperty("aga.password"));

        reniecAgaClient = new ReniecAgaClient(oConfigAga);
    }

    @Test
    public void signFile() throws IOException {
        String massiveCsv = getClass().getClassLoader().getResource("signTest.json").getFile();

        byte[] result = reniecAgaClient.signAga(new File(massiveCsv));

        if (result != null) {
            try (FileOutputStream fos = new FileOutputStream(tempDir.concat("fileSign.p7s"))) {
                fos.write(result);
            }
        }

        Assert.assertNotNull(result);
    }
}
