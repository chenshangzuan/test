package java_base.generic_data;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;

/**
 * @author: Kled
 * @version: TestBloomFilter.java, v0.1 2020-10-19 14:44 Kled
 */
public class TestBloomFilter {
    //BloomFilter 的设计思想和BitSet有较大的相似性
    //核心思想也是使用多个Hash算法在一个“位图”结构上着色，最终提高“存在性”判断的效率, 解决hash冲突问题
    public static void main(String[] args) {
        Charset charset = Charset.forName("utf-8");
        BloomFilter<String> bloomFilter1 = BloomFilter.create(Funnels.stringFunnel(charset),2<<21);

        bloomFilter1.put("aaa");
        System.out.println(bloomFilter1.mightContain("aaa"));


        BloomFilter<Integer> bloomFilter2 = BloomFilter.create(Funnels.integerFunnel(), 128);
        bloomFilter2.put(200);
        System.out.println(bloomFilter2.mightContain(200));
    }
}
