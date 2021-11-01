package spark.udf;

import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.MutableAggregationBuffer;
import org.apache.spark.sql.expressions.UserDefinedAggregateFunction;
import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StructType;

/**
 * @author Kled
 * @date 2021/10/18 11:51 上午
 * 多->1
 */
public class MyUDAF1 extends UserDefinedAggregateFunction {
    //输入类型
    @Override
    public StructType inputSchema() {
        return null;
    }

    //中间结果类型(辅助字段)
    @Override
    public StructType bufferSchema() {
        return null;
    }

    //输出类型
    @Override
    public DataType dataType() {
        return null;
    }

    //输入和输出类型是否一致
    @Override
    public boolean deterministic() {
        return false;
    }

    //初始化辅助字段
    @Override
    public void initialize(MutableAggregationBuffer buffer) {

    }

    //更新辅助字段(分区内)
    @Override
    public void update(MutableAggregationBuffer buffer, Row input) {

    }

    //全局计算辅助字段
    @Override
    public void merge(MutableAggregationBuffer buffer1, Row buffer2) {

    }

    //计算过程
    @Override
    public Object evaluate(Row buffer) {
        return null;
    }
}
