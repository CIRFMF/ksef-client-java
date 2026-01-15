package pl.akmf.ksef.sdk.client.model.limit;

public class SessionInvoiceListRateLimit {
    private int perSecond;
    private int perMinute;
    private int perHour;

    public SessionInvoiceListRateLimit() {

    }

    public SessionInvoiceListRateLimit(int perSecond, int perMinute, int perHour) {
        this.perSecond = perSecond;
        this.perMinute = perMinute;
        this.perHour = perHour;
    }

    public int getPerSecond() {
        return perSecond;
    }

    public void setPerSecond(int perSecond) {
        this.perSecond = perSecond;
    }

    public int getPerMinute() {
        return perMinute;
    }

    public void setPerMinute(int perMinute) {
        this.perMinute = perMinute;
    }

    public int getPerHour() {
        return perHour;
    }

    public void setPerHour(int perHour) {
        this.perHour = perHour;
    }
}
