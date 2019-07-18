package pe.gob.reniec.aga.sdk.dto;

/**
 * @author Alexander Llacho
 */
public class ConfigAga {

    private String agaUri;
    private String timestamp;
    private String certificateId;
    private String secretPassword;

    public ConfigAga() {
    }

    public String getAgaUri() {
        return agaUri;
    }

    public void setAgaUri(String agaUri) {
        this.agaUri = agaUri;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }

    public String getSecretPassword() {
        return secretPassword;
    }

    public void setSecretPassword(String secretPassword) {
        this.secretPassword = secretPassword;
    }

    @Override
    public String toString() {
        return "ConfigAga{" +
                "agaUri='" + agaUri + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", certificateId='" + certificateId + '\'' +
                ", secretPassword='" + secretPassword + '\'' +
                '}';
    }
}
