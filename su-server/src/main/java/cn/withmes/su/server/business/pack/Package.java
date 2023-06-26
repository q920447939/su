/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.pack;

import lombok.Data;

/**
 * ClassName: Package
 * @Description: 序列化完的实体
 * @author leegoo
 * @date 2023年06月25日
 */
@Data
public class Package {
    private short command;
    private Object body;
}
