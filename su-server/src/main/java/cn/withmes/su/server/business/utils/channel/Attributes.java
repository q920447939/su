/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.utils.channel;

import io.netty.util.AttributeKey;

/**
 * 属性
 * ClassName: Attributes
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
public class Attributes {
   public static  AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
}
