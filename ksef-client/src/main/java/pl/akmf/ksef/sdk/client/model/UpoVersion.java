package pl.akmf.ksef.sdk.client.model;

public enum UpoVersion {

    UPO_4_2("upo-v4-2"),
    UPO_4_3("upo-v4-3");

    private final String value;

    UpoVersion(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static UpoVersion fromValue(String v) {
        for (UpoVersion c : UpoVersion.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return UpoVersion.UPO_4_3;
    }

}
