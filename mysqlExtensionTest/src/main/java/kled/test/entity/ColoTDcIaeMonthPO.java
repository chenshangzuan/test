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
 * colo 数据中心盈收
 * </p>
 *
 * @author kled
 * @since 2021-05-19
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("colo_t_dc_iae_month")
public class ColoTDcIaeMonthPO extends Model<ColoTDcIaeMonthPO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户标识
     */
    private Integer dcName;

    /**
     * 客户名称
     */
    private String dcCode;

    /**
     * 机柜数
     */
    private Integer rackCount;

    /**
     * 区域名
     */
    private String regionName;

    /**
     * 总收入
     */
    private Double totalIncome;

    /**
     * 总成本
     */
    private Double totalExpenditure;

    /**
     * 总利润
     */
    private Double totalProfit;

    /**
     * 利润率
     */
    private Double profitRadio;

    /**
     * 是否达标
     */
    private Integer isStandard;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
