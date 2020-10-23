package kled.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @since 2020-10-18
 * @CfgParam hello
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerAddr extends Model<CustomerAddr> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private String name;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
