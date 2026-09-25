package pl.akmf.ksef.sdk.client.interfaces;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public interface SignatureService {

    /**
     * Podpisuje dokument XML podpisem XAdES-BASELINE-B (enveloped), dobierając algorytm podpisu
     * (RSA-SHA256 albo ECDSA-SHA256/384/512, w zależności od rozmiaru klucza) na podstawie typu klucza.
     *
     * @param xml                   Dokument XML do podpisania (tablica bajtów).
     * @param signatureCertificate  Certyfikat użyty do podpisu.
     * @param privateKey            Klucz prywatny odpowiadający certyfikatowi podpisującemu.
     * @return Podpisany dokument XML jako ciąg znaków.
     * @throws IOException w razie błędu odczytu podpisanego dokumentu.
     */
    String sign(byte[] xml, X509Certificate signatureCertificate, PrivateKey privateKey) throws IOException;
}
