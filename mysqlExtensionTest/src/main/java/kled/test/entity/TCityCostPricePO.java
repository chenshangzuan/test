package kled.test.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * t_city_cost_price 城市成本单价表
 * </p>
 *
 * @author kled
 * @since 2021-05-08
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_city_cost_price")
public class TCityCostPricePO extends Model<TCityCostPricePO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 起始城市名
     */
    private String fromCityName;

    /**
     * 起始城市编码
     */
    private String fromCityCode;

    /**
     * 起始洲名
     */
    private String fromContinentName;

    /**
     * 起始洲中文名
     */
    private String fromContinentChineseName;

    /**
     * 终止城市名
     */
    private String toCityName;

    /**
     * 终止城市编码
     */
    private String toCityCode;

    /**
     * 终止洲名
     */
    private String toContinentName;

    /**
     * 终止洲中文名
     */
    private String toContinentChineseName;

    /**
     * 带宽定价
     */
    private BigDecimal bandwidthPrice;

    /**
     * 数据时间
     */
    private String dt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
