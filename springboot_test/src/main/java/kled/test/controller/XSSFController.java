package kled.test.controller;

import com.fabric4cloud.oxygen.common.model.ResultInfo;
import com.fabric4cloud.oxygen.common.util.StringUtils;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: Kled
 * @version: XSSFController.java, v0.1 2020-10-27 14:59 Kled
 */
@RestController
@RequestMapping(value = "/xssf")
public class XSSFController {

    @GetMapping(value = "/sslClientTemplate")
    public void downloadSiteSslClientTemplate(HttpServletResponse response) {
        //logger.info("Request method[downloadSiteSslClientTemplate] received , userId={}, tenantUuid={}");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet sheet = xssfWorkbook.createSheet("ssl client template");
        sheet.setColumnWidth(0, 25 * 256);
        XSSFRow headerRow = sheet.createRow(0);

        XSSFCell headerColumn1 = headerRow.createCell(0, XSSFCell.CELL_TYPE_STRING);
        headerColumn1.setCellValue("用户名");

        //设置第一列的默认格式，文本+左对齐
        XSSFCellStyle headerColumn1Style = xssfWorkbook.createCellStyle();
        headerColumn1Style.setAlignment(HorizontalAlignment.LEFT);
        DataFormat format = xssfWorkbook.createDataFormat();
        headerColumn1Style.setDataFormat(format.getFormat("@"));
        sheet.setDefaultColumnStyle(0, headerColumn1Style);

        XSSFComment comment = sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
        comment.setString(new XSSFRichTextString("用户名由数字、字母、下划线及横线组成，不超过20个字符"));
        headerColumn1.setCellComment(comment);

        DataValidationHelper helper = sheet.getDataValidationHelper();
        CellRangeAddressList addressList = new CellRangeAddressList(1, 2000, 0, 0);
        DataValidationConstraint textLengthConstraint = helper.createNumericConstraint(DataValidationConstraint.ValidationType.TEXT_LENGTH, DataValidationConstraint.OperatorType.BETWEEN, "1", "20");
        DataValidation textLengthValidation = helper.createValidation(textLengthConstraint, addressList);
        textLengthValidation.setShowErrorBox(true);
        textLengthValidation.createErrorBox("错误", "用户名长度超出20个字符");
        sheet.addValidationData(textLengthValidation);

//        DataValidationConstraint textDuplicationConstraint = helper.createCustomConstraint("COUNTIF(A:A,A2)=1");
//        DataValidation textDuplicationValidation = helper.createValidation(textDuplicationConstraint, addressList);
//        textDuplicationValidation.setShowErrorBox(true);
//        textDuplicationValidation.createErrorBox("错误", "当前录入的用户名重复");
//        sheet.addValidationData(textDuplicationValidation);

        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment;filename=ssl_client_template.xlsx");
        try {
            xssfWorkbook.write(response.getOutputStream());
            response.getOutputStream().flush();
            response.getOutputStream().close();
        } catch (IOException e) {
            //logger.info("Request method[downloadSiteSslClientTemplate] error, e", e);
        }
        //logger.info("Request method[downloadSiteSslClientTemplate] handle complete");
    }

    @PutMapping(value = "/sslClientTemplate")
    public String uploadSiteSslClients(@RequestBody MultipartFile file) throws IOException {
        List<String> uploadAccounts = Lists.newArrayList();
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        for (Row row : xssfSheet){
            if(row == null || row.getRowNum() == 0){
                continue;
            }
            XSSFCell xssfCell = (XSSFCell) row.getCell(0);
            if (xssfCell != null && !StringUtils.isEmpty(xssfCell.getStringCellValue())) {
                uploadAccounts.add(xssfCell.getStringCellValue());
            }
        }
        return Joiner.on(",").join(uploadAccounts);
    }
//
//    public static void main(String[] args) {
//        //匹配数字、字母、下划线及横线
//        Pattern SITE_SSL_CLIENT_ACCOUNT_PATTERN = Pattern.compile("[a-z,A-Z,\\-,_,\\d]{1,20}");
//        Matcher matcher = SITE_SSL_CLIENT_ACCOUNT_PATTERN.matcher("da88ddad_adddd_da");
//        System.out.println(matcher.matches());
//    }
}
