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
 * 
 * </p>
 *
 * @author kled
 * @since 2021-04-15
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ipt_app_t_charge_region_month")
public class IptAppTChargeRegionMonthPO extends Model<IptAppTChargeRegionMonthPO> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 计费区域
     */
    private String chargeRegion;

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 总成本
     */
    private Double totalCost;

    /**
     * 客户平均售卖单价
     */
    private Double customerAvgSellUnitPrice;

    /**
     * 客户平均成本单价
     */
    private Double customerAvgCostUnitPrice;

    /**
     * 客户95流量速率
     */
    private Double customerP95Rate;

    /**
     * 供应商平均售卖单价
     */
    private Double vendorAvgSellUnitPrice;

    /**
     * 供应商平均成本单价
     */
    private Double vendorAvgCostUnitPrice;

    /**
     * 供应商95流量速率
     */
    private Double vendorP95Rate;

    /**
     * 毛利率
     */
    private Double grossProfitRate;

    /**
     * 客户数
     */
    private Integer customerCount;

    /**
     * 数据时间
     */
    private String dt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
