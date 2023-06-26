/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月26日
 */
package cn.withmes.su.server.utils;

import cn.hutool.crypto.digest.MD5;

/**
 * ClassName: MD5Utils
 * @Description:
 * @author leegoo
 * @date 2023年06月26日
 */
public class MD5Utils {
    private static final  MD5 md5 = MD5.create();

    public static String md5 (String plainText) {
        return md5.digestHex(plainText);
    }

}
