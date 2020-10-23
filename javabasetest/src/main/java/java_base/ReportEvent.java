package java_base;

public class ReportEvent {
    
    private String eventType;
    
    private Integer elementId;
    
    private long tsms;
    
    public ReportEvent(String eventType, Integer elementId,long tsms) {
        super();
        this.eventType = eventType;
        this.elementId = elementId;
        this.tsms = tsms;
    }
    
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public Integer getElementId() {
        return elementId;
    }
    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }
    public long getTsms() {
        return tsms;
    }
    public void setTsms(long tsms) {
        this.tsms = tsms;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((elementId == null) ? 0 : elementId.hashCode());
        result = prime * result + ((eventType == null) ? 0 : eventType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ReportEvent other = (ReportEvent) obj;
        if (elementId == null) {
            if (other.elementId != null)
                return false;
        } else if (!elementId.equals(other.elementId))
            return false;
        if (eventType == null) {
            if (other.eventType != null)
                return false;
        } else if (!eventType.equals(other.eventType))
            return false;
        return true;
    }
    
    
    
    
}
