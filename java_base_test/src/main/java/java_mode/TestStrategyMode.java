package java_mode;

import java.lang.reflect.Field;

/**
 * @author chenpc
 * @version $Id: TestStrategyMode.java, v 0.1 2018-05-27 17:14:24 chenpc Exp $
 */
public class TestStrategyMode{

    private CpeReportHanlerBase cpeReportBase;

    public TestStrategyMode(CpeReportHanlerBase cpeReportBase) {
        this.cpeReportBase = cpeReportBase;
    }

    public void handlerCpeReportResult(){
        cpeReportBase.handlerRequest();
    }

    public static void main(String[] args) throws Exception{
        CpeReportHanlerBase cpeReportBase = null;
        CpeReport report = new CpeReport();
        report.setHaStatusReport(new HaStatusReportHandler());
        Field[] fields = report.getClass().getDeclaredFields();
        for (Field field:fields) {
            field.setAccessible(true);
            try {
                Object val = field.get(report);
                if(val != null){
                    cpeReportBase = (CpeReportHanlerBase)val;
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(cpeReportBase == null){
            throw new Exception("cpe report body null");
        }
        TestStrategyMode testStrategyMode = new TestStrategyMode(cpeReportBase);
        testStrategyMode.handlerCpeReportResult();
    }

}

interface CpeReportHanlerBase{
    void handlerRequest();
}

class HaStatusReportHandler implements CpeReportHanlerBase{
    @Override
    public void handlerRequest() {
        System.out.println("report HA status");
    }
}

class PolicyStatusReportHandler implements CpeReportHanlerBase{
    @Override
    public void handlerRequest() {
        System.out.println("report policy status");
    }
}

class CpeReport{

    private HaStatusReportHandler haStatusReport;

    private PolicyStatusReportHandler policyStatusReport;

    public HaStatusReportHandler getHaStatusReport() {
        return haStatusReport;
    }

    public void setHaStatusReport(HaStatusReportHandler haStatusReport) {
        this.haStatusReport = haStatusReport;
    }

    public PolicyStatusReportHandler getPolicyStatusReport() {
        return policyStatusReport;
    }

    public void setPolicyStatusReport(PolicyStatusReportHandler policyStatusReport) {
        this.policyStatusReport = policyStatusReport;
    }
}

