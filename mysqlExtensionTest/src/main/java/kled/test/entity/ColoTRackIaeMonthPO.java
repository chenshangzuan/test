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
 * colo 机柜盈收
 * </p>
 *
 * @author kled
 * @since 2021-05-20
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("colo_t_rack_iae_month")
public class ColoTRackIaeMonthPO extends Model<ColoTRackIaeMonthPO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 机柜ID
     */
    private Integer rackId;

    /**
     * 机柜编码
     */
    private String rackCode;

    /**
     * 机柜类型
     */
    private String rackType;

    /**
     * 数据中心名称
     */
    private Integer dcName;

    /**
     * 数据中心标识
     */
    private String dcCode;

    /**
     * 区域名
     */
    private String regionName;

    /**
     * 能源信息
     */
    private String powerInfo;

    /**
     * 使用情况
     */
    private String usage;

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
     * 空闲时间min
     */
    private Integer idleTime;

    /**
     * 空闲成本
     */
    private Double idleExpenditure;

    /**
     * 空闲成本占比
     */
    private Double idleExpenditureRadio;

    /**
     * 数据时间
     */
    private String dt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
