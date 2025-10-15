package pl.akmf.ksef.sdk.client;

public final class Parameter {

    private Parameter() {

    }

    ///QUERY
    public static final String PAGE_SIZE = "pageSize";
    public static final String PAGE_OFFSET = "pageOffset";
    public static final String STATUS = "status";
    public static final String STATUSES = "statuses";
    public static final String DESCRIPTION = "description";
    public static final String AUTHOR_IDENTIFIER = "authorIdentifier";
    public static final String AUTHOR_IDENTIFIER_TYPE = "authorIdentifierType";
    public static final String SESSION_TYPE = "sessionType";
    public static final String REFERENCE_NUMBER = "referenceNumber";
    public static final String DATE_CREATED_FROM = "dateCreatedFrom";
    public static final String DATE_CREATED_TO = "dateCreatedTo";
    public static final String DATE_CLOSED_FROM = "dateClosedFrom";
    public static final String DATE_CLOSED_TO = "dateClosedTo";
    public static final String DATE_MODIFIED_FROM = "dateModifiedFrom";
    public static final String DATE_MODIFIED_TO = "dateModifiedTo";
    public static final String VERIFY_CERTIFICATE_CHAIN = "verifyCertificateChain";

    //PATH
    public static final String PATH_REFERENCE_NUMBER = "{referenceNumber}";
    public static final String PATH_UPO_REFERENCE_NUMBER = "{upoReferenceNumber}";
    public static final String PATH_KSEF_NUMBER = "{ksefNumber}";
    public static final String PATH_KSEF_REFERENCE_NUMBER = "{ksefReferenceNumber}";
    public static final String PATH_INVOICE_NUMBER = "{invoiceReferenceNumber}";
    public static final String PATH_PERMISSION_ID = "{permissionId}";
    public static final String PATH_CERTIFICATE_SERIAL_NUMBER = "{certificateSerialNumber}";
}
