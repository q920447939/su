/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.decorate.response;

import cn.withmes.su.server.business.entity.response.ResponseData;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.ResponseEnumsInterface;
import lombok.Data;

/**
 * 响应包装
 * ClassName: ResponseWrapper
 *
 * @author leegoo
 * @Description:
 * @date 2023年06月27日
 */
@Data
public class ResponseWrapper {

    /**
     * 登录succ响应
     *
     * @param responseEnumsInterface 响应枚举接口
     * @return {@link ResponseData}
     */
    public static ResponseData loginSuccResponse(ResponseEnumsInterface responseEnumsInterface) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) PackageEnums.LOGIN.getType());
        responseData.setCode(responseEnumsInterface.code());
        responseData.setMessage(responseEnumsInterface.desc());
        return responseData;
    }

    public static ResponseData loginFailResponse(ResponseEnumsInterface responseEnumsInterface) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) PackageEnums.LOGIN.getType());
        responseData.setCode(responseEnumsInterface.code());
        responseData.setMessage(responseEnumsInterface.desc());
        return responseData;
    }

    public static ResponseData chatResponse(ResponseEnumsInterface responseEnumsInterface,String message) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) PackageEnums.CHAT.getType());
        responseData.setCode(responseEnumsInterface.code());
        responseData.setMessage(message);
        return responseData;
    }
}
