package kled.test.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * colo 盈收总览
 * </p>
 *
 * @author kled
 * @since 2021-05-19
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("colo_t_iae_overview_month")
public class ColoTIaeOverviewMonthPO extends Model<ColoTIaeOverviewMonthPO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 总客户数
     */
    private Integer totalCustomerCount;

    /**
     * 总客户环比增量
     */
    private Integer totalCustomerRingValue;

    /**
     * 总客户环比增率
     */
    private Double totalCustomerRingRadio;

    /**
     * 总利润率大于0客户数
     */
    private Integer profitRadioGtTage0CustomerCount;

    /**
     * 总利润率大于0客户率
     */
    private String profitRadioGtTage0CustomerRadio;

    /**
     * 总利润率小于等于0客户数
     */
    private Integer profitRadioLeTage0CustomerCount;

    /**
     * 总利润率小于等于0客户率
     */
    private String profitRadioLeTage0CustomerRadio;

    /**
     * 总数据中心数
     */
    private Integer totalDcCount;

    /**
     * 总数据中心环比增量
     */
    private Integer totalDcRingValue;

    /**
     * 总数据中心环比增率
     */
    private Double totalDcRingRadio;

    /**
     * 总利润率大于0数据中心数
     */
    private Integer profitRadioGtTage0DcCount;

    /**
     * 总利润率大于0数据中心率
     */
    private String profitRadioGtTage0DcRadio;

    /**
     * 总利润率小于等于0数据中心数
     */
    private String profitRadioLeTage0DcCount;

    /**
     * 总利润率小于等于0数据中心率
     */
    private String profitRadioLeTage0DcRadio;

    /**
     * 总机柜数
     */
    private Integer totalRackCount;

    /**
     * 总机柜数环比增量
     */
    private Integer totalRackRingValue;

    /**
     * 总机柜数环比增率
     */
    private Double totalRackRingRadio;

    /**
     * 总利润率大于0机柜数
     */
    private Integer profitRadioGtTage0RackCount;

    /**
     * 总利润率大于0机柜率
     */
    private String profitRadioGtTage0RackRadio;

    /**
     * 总利润率小于等于0机柜数
     */
    private String profitRadioLeTage0RackCount;

    /**
     * 总利润率小于等于0机柜率
     */
    private String profitRadioLeTage0RackRadio;

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 总收入环比增量
     */
    private Double totalIncomeRingValue;

    /**
     * 总收入环比增率
     */
    private Double totalIncomeRingRadio;

    /**
     * 总成本
     */
    private Double totalExpenditure;

    /**
     * 总支出环比增量
     */
    private Double totalExpenditureRingValue;

    /**
     * 总支出环比增率
     */
    private Double totalExpenditureRingRadio;

    /**
     * 总利润
     */
    private Double totalProfit;

    /**
     * 总利润环比增量
     */
    private Double totalProfitRingValue;

    /**
     * 总利润环比增率
     */
    private Double totalProfitRingRadio;

    /**
     * 利润率
     */
    private Double profitRadio;

    /**
     * 利润率环比增量
     */
    private Double profitRadioRingValue;

    /**
     * 利润率环比增率
     */
    private Double profitRadioRingRadio;

    /**
     * 数据时间
     */
    private String dt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
