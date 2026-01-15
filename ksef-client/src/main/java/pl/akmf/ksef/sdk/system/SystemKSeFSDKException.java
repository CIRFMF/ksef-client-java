package pl.akmf.ksef.sdk.system;

public class SystemKSeFSDKException extends RuntimeException {

    public SystemKSeFSDKException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemKSeFSDKException(String message) {
        super(message);
    }
}