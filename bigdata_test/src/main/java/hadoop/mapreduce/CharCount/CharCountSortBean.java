package hadoop.mapreduce.CharCount;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Kled
 * @date 2021/7/31 9:49 下午
 */
public class CharCountSortBean implements WritableComparable<CharCountSortBean> {

    private String charStr;

    private int num;

    public String getCharStr() {
        return charStr;
    }

    public void setCharStr(String charStr) {
        this.charStr = charStr;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public int compareTo(CharCountSortBean o) {
        //order by charStr, num
        int result = this.charStr.compareTo(o.getCharStr());
        if(result == 0){
            return this.num - o.getNum();
        }
        return result;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(charStr);
        out.writeInt(num);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.charStr = in.readUTF();
        this.num = in.readInt();
    }
}
