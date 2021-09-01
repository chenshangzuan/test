package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author Kled
 * @date 2021/8/20 4:38 下午
 */
public class HbaseClient {

    //Hbase与Spark guava版本冲突
    public static void main(String[] args) throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "172.16.5.56:2181");// zookeeper地址
//        configuration.set("hbase.master", "172.16.5.5:60000");
//        configuration.set("hbase.security.authentication", "");

        Connection connection = ConnectionFactory.createConnection(configuration);
        HBaseAdmin hBaseAdmin = (HBaseAdmin) connection.getAdmin();

        TableName tableName = TableName.valueOf("student");
        if(!hBaseAdmin.tableExists(tableName)){
            TableDescriptorBuilder tableDescriptorBuilder = TableDescriptorBuilder.newBuilder(tableName);
            ColumnFamilyDescriptor baseInfoColumnFamily = ColumnFamilyDescriptorBuilder.of("base_info");
            tableDescriptorBuilder.setColumnFamily(baseInfoColumnFamily);

            TableDescriptor tableDescriptor = tableDescriptorBuilder.build();
            hBaseAdmin.createTable(tableDescriptor);

            //删除列簇
            //hBaseAdmin.deleteColumnFamily(tableName, Bytes.toBytes("base_info"));

            NamespaceDescriptor[] namespaceDescriptors = hBaseAdmin.listNamespaceDescriptors();
            System.out.println("list namespace:" + Arrays.stream(namespaceDescriptors).map(NamespaceDescriptor::getName).collect(Collectors.joining(",")));

            List<TableDescriptor> tableDescriptors = hBaseAdmin.listTableDescriptors();
            System.out.println("list table:" + tableDescriptors.stream().map(x -> Arrays.toString(x.getTableName().getName())).collect(Collectors.joining(",")));
        }
        //激活
        hBaseAdmin.enableTable(tableName);

        Table table = hBaseAdmin.getConnection().getTable(tableName);
        Put put = new Put(Bytes.toBytes("rowKey_1"));
        put.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes("10"));
        table.put(put);

        Get get = new Get(Bytes.toBytes("key_1")).readAllVersions()/*查看所有版本*/;
//        FilterList filterList = new FilterList(ValueFilter);
//        get.addColumn();
//        get.addFamily();
        Result result = table.get(get);
        if(result.getExists()){
            result.getValue(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
            result.getValueAsByteBuffer(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
            //cell 存储单元：row_key + column_family + column + timestamp唯一确定
            List<Cell> cells = result.getColumnCells(Bytes.toBytes("base_info"), Bytes.toBytes("age"));
            for (Cell cell : cells) {
                CellUtil.cloneFamily(cell); //列簇
                CellUtil.cloneQualifier(cell);  //列限定符(列名)
                CellUtil.cloneRow(cell);
                CellUtil.cloneValue(cell); //列值
            }
            CellScanner cellScanner = result.cellScanner();

            result.getRow();
        }

        Scan scan = new Scan();
        //过滤器
        //DependentColumnFilter
        //KeyOnlyFilter
        //ColumnCountGetFilter
        //SingleColumnValueFilter
        //PrefixFilter
        //SingleColumnValueExcludeFilter
        //FirstKeyOnlyFilter
        //ColumnRangeFilter
        //ColumnValueFilter
        //TimestampsFilter
        //FamilyFilter
        //QualifierFilter
        //ColumnPrefixFilter
        //RowFilter
        //MultipleColumnPrefixFilter
        //InclusiveStopFilter
        //PageFilter
        //ValueFilter
        //ColumnPaginationFilter
        SingleColumnValueFilter filter = new SingleColumnValueFilter(Bytes.toBytes("base_info"), Bytes.toBytes("age"),
                CompareOperator.EQUAL, Bytes.toBytes("10"));
        scan.setFilter(filter);
        //scan.setReversed(true); //反向扫描
        ResultScanner resultScanner = table.getScanner(scan);
        Iterator<Result> resultIterator = resultScanner.iterator();
        while (resultIterator.hasNext()){
            Result result1 = resultIterator.next();
            Cell[] rawCells = result1.rawCells();
            //...
        }

        //复合计数器
        Increment increment = new Increment(Bytes.toBytes("rowKey_1"));
        increment.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("age"), 1);
        increment.addColumn(Bytes.toBytes("base_info"), Bytes.toBytes("name"), 2);
        table.increment(increment);

        //单计数器
        table.incrementColumnValue(Bytes.toBytes("rowKey_1"), Bytes.toBytes("base_info"), Bytes.toBytes("age"), 1);

        Delete delete = new Delete(Bytes.toBytes("rowKey_1"));
        table.delete(delete);

        table.close();

        //禁用及删除
        hBaseAdmin.disableTable(tableName);
        hBaseAdmin.deleteTable(tableName);
        hBaseAdmin.close();
    }
}
