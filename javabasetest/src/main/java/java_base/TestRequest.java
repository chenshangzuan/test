package java_base;

import java.util.Map;
import java.util.HashMap;

/**
 * @author chenpc
 * @version $Id: java_base.TestRequest.java, v 0.1 2018-05-24 17:47:24 chenpc Exp $
 */
public class TestRequest {

    private boolean isActive;

    private PolicyStatus policyStatus;

    private LanConfigStatus lanConfigStatus;

    public Map<String, CpeReportBase> reportBaseMap = new HashMap<>();

    {
        reportBaseMap.put("POLICY",policyStatus);
        reportBaseMap.put("LAN_CONFIG",lanConfigStatus);
    }

    public PolicyStatus getPolicyStatus() {
        return policyStatus;
    }

    public void setPolicyStatus(PolicyStatus policyStatus) {
        this.policyStatus = policyStatus;
    }

    public LanConfigStatus getLanConfigStatus() {
        return lanConfigStatus;
    }

    public void setLanConfigStatus(LanConfigStatus lanConfigStatus) {
        this.lanConfigStatus = lanConfigStatus;
    }

    public Map<String, CpeReportBase> getReportBaseMap() {
        return reportBaseMap;
    }

    public void setReportBaseMap(Map<String, CpeReportBase> reportBaseMap) {
        this.reportBaseMap = reportBaseMap;
    }

    public static void main(String[] args) {
        TestRequest testRequest = new TestRequest();
        System.out.println(testRequest.reportBaseMap.size());
        for(String key : testRequest.reportBaseMap.keySet()){
            if(testRequest.reportBaseMap.get(key) == null)
                continue;
            switch (key){
                case "POLICY":
                    System.out.println("POLICY");
                    PolicyStatus policyStatus = (PolicyStatus)testRequest.reportBaseMap.get(key);
                    break;
                case "LAN_CONFIG":
                    System.out.println("LAN_CONFIG");
                    break;
                default:
                    break;
            }

        }
    }

}

class PolicyStatus implements CpeReportBase{}

class LanConfigStatus implements CpeReportBase{}

interface CpeReportBase{}
