package pe.gob.reniec.aga.sdk.utils;


import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author Paulo Portugal
 */
public class MySSLConnectionSocketFactory {

    public static SSLConnectionSocketFactory getConnectionSocketFactory() {

        SSLConnectionSocketFactory sslcf = null;
        try {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            HostnameVerifier hnv = new NoopHostnameVerifier();
            sslcf = new SSLConnectionSocketFactory(sslContext, hnv);
        } catch (Exception e) {
            sslcf = null;
        }

        return sslcf;
    }

}
