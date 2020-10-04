package localTest;/*
 * Fabric4cloud.com Inc.
 * Copyright (c) 2015-2018 All Rights Reserved.
 */

import com.google.common.base.Splitter;
import com.zenlayer.wukong.common.utils.network.Subnet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author chenpc
 * @version $Id: localTest.TestSubnet.java, v 0.1 2018-04-16 09:56:58 chenpc Exp $
 */
public class TestSubnet {

    private static final Logger logger = LoggerFactory.getLogger(TestSubnet.class);

    public static void main(String[] args) {
        final Integer[] count = {1};
        List<String> cidrs = Stream.generate(new Supplier<String>() {
                                                 @Override
                                                 public String get() {
                                                     String cidr = "10.100." + count[0] + ".0/24";
                                                     count[0] = count[0] +1;
                                                     return cidr;
                                                 }
                                             }
        ).limit(255).collect(Collectors.toList());

        Subnet subnet11 = new Subnet("10.100.0.0/16");
        System.out.println(subnet11.asInteger("10.100.1.0"));

        String cidr = "172.16.0.1";
        String port = "70,90:90";
        //cidrCheck(cidr);
        //portCheck(port);
        //testSubnet();

        String portIp = "172.16.0.0/16";
        String peerIp = "172.16.1.1";
        System.out.println(new

                Subnet(portIp).

                isInRange(peerIp));

        String ip = "172.16.0.0/30";
        System.out.println(ip.split("/")[0].

                equals(new Subnet(ip).

                        getNetworkAddress()));

        List<String> ips = Arrays.asList("172.1.1.1/23", "172.1.1.2/30", "172.1.1.0/30");
        Set<String> networkAddresses = ips.stream().map(x -> {
            Subnet s = new Subnet(x);
            return s.getNetworkAddress();
        }).collect(Collectors.toSet());
        System.out.println(networkAddresses);
    }

    private static void testSubnet() {
        Subnet s = new Subnet("170.170.0.0/16");
        System.out.println(s.high());
        System.out.println(s.low());
        System.out.println(s.address());
        System.out.println(Integer.MIN_VALUE);
        System.out.println(s.getNetworkAddress());
        System.out.println(s.fromInteger(s.low()));
        System.out.println(s.fromInteger(s.high()));

        System.out.println(s);

        System.out.println(pop(-1431699455));
        System.out.println(s.toCidrNotation("111.11.11.0", "255.255.255.0"));
    }

    private static int pop(int x) {
        x -= x >>> 1 & 1431655765;  //1010101010101010101010101010101
        x = (x & 858993459) + (x >>> 2 & 858993459);  //110011001100110011001100110011
        x = x + (x >>> 4) & 252645135; //1111000011110000111100001111
        x += x >>> 8;
        x += x >>> 16;
        return x & 63; //111111
    }

    private static void cidrCheck(String cidr) {
        List<String> cidrs = Splitter.onPattern(",").omitEmptyStrings().trimResults().splitToList(cidr);
        List<String> formatCidrs = cidrs.stream().map(s -> {
            if (!s.contains("/")) {
                return s + "/32";
            }
            return s;
        }).collect(Collectors.toList());
        formatCidrs.forEach(x -> {
            try {
                new Subnet(x);
                System.out.println(x);
            } catch (IllegalArgumentException e) {
                logger.info("IllegalArgumentException occurred in TcServicePolicyOpsRestController->cidrCheck, e=", e);
            } catch (Exception ex) {
                logger.info("Exception occurred in TcServicePolicyOpsRestController->cidrCheck, ex=", ex);
            }
        });
    }

    private static String portCheck(String port) {
        if (port == null) {
            return port;
        }
        List<String> ports = Splitter.onPattern(",").omitEmptyStrings().trimResults().splitToList(port);
        if (ports.size() > 1) {
            throw new IllegalArgumentException("Could not parse [" + port + "]");
        }
        Pattern pattern = Pattern.compile("(\\d{1,5})(:(\\d{1,5}))?");
        Matcher matcher = pattern.matcher(ports.get(0));
        if (matcher.matches()) {
            if (port.contains(":")) {
                int begin = Integer.valueOf(matcher.group(1));
                int end = Integer.valueOf(matcher.group(3));
                portRangeCheck(begin, 1, 65535);
                portRangeCheck(end, 1, 65535);
                if (begin > end) {
                    throw new IllegalArgumentException("port begin [" + begin + "] is greater than end [" + end + "]");
                }
            } else {
                portRangeCheck(Integer.valueOf(matcher.group(1)), 1, 65535);
            }
        } else {
            throw new IllegalArgumentException("Could not parse [" + port + "]");
        }
        return port;
    }

    private static int portRangeCheck(int value, int begin, int end) {
        if (value >= begin && value <= end) {
            return value;
        } else {
            throw new IllegalArgumentException("Value [" + value + "] not in range [" + begin + "," + end + "]");
        }
    }

}
