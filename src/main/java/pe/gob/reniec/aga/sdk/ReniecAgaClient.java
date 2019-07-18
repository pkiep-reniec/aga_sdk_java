package pe.gob.reniec.aga.sdk;

import pe.gob.reniec.aga.sdk.dto.ConfigAga;
import pe.gob.reniec.aga.sdk.service.SignService;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author Alexander Llacho
 */
public class ReniecAgaClient {

    private ConfigAga configAga = null;
    private SignService signService;

    public ReniecAgaClient(ConfigAga oConfigAga) {
        this.setConfig(oConfigAga);
    }

    public byte[] signAga(File file) {
        if (this.configAga == null) {
            return null;
        }

        return this.signService.procSignAga(file);
    }

    private void setConfig(ConfigAga oConfigAga) {
        try {
            this.configAga = oConfigAga;
            this.signService = SignService.getInstance(this.configAga);
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));

            System.out.println(sw.toString());
        }
    }
}
