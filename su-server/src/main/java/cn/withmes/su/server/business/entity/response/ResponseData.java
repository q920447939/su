/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.entity.response;

import lombok.Data;

/**
 * 响应数据
 * ClassName: ResponseData
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Data
public class ResponseData {
    private short type;
    private int code;
    private String message;



}
