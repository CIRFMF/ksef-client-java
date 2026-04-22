package pl.akmf.ksef.sdk.sign;

import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


public class CertUtil {
    private CertUtil() {
    }


    public static boolean isMatchingEcdsaPair(X509Certificate cert, PrivateKey privateKey) {
        if (cert == null || privateKey == null) {
            return false;
        }

        if ((cert.getPublicKey() instanceof ECPublicKey) && (privateKey instanceof ECPrivateKey)) {
            return true;
        }

        return "EC".equalsIgnoreCase(cert.getPublicKey().getAlgorithm())
                && "EC".equalsIgnoreCase(privateKey.getAlgorithm());
    }

    public static boolean isMatchingRsaPair(X509Certificate cert, PrivateKey privateKey) {
        if (cert == null || privateKey == null) {
            return false;
        }

        if ((cert.getPublicKey() instanceof RSAPublicKey) && (privateKey instanceof RSAPrivateKey)) {
            return true;
        }

        return "RSA".equalsIgnoreCase(cert.getPublicKey().getAlgorithm())
                && "RSA".equalsIgnoreCase(privateKey.getAlgorithm());
    }
}
