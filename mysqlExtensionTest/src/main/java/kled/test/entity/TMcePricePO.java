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
 * t_mce_price 
 * </p>
 *
 * @author kled
 * @since 2021-06-04
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_mce_price")
public class TMcePricePO extends Model<TMcePricePO> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 数据中心编码
     */
    private String dcCode;

    /**
     * 定价档位等级
     */
    private Integer bwPriceLevel;

    /**
     * 定价档位参考下限
     */
    private Integer bwPriceThresholdDown;

    /**
     * 定价档位参考上限
     */
    private Integer bwPriceThresholdUp;

    /**
     * 定价
     */
    private BigDecimal bwPrice;

    /**
     * 价格标签
     */
    private String priceLabel;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
