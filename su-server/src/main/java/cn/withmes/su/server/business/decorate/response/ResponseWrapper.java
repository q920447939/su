/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月27日
 */
package cn.withmes.su.server.business.decorate.response;

import cn.withmes.su.server.business.entity.response.ResponseData;
import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.enums.response.BaseResponseEnums;
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
    private static final  BaseResponseEnums BASE_RESPONSE_ENUMS = BaseResponseEnums.SUCCESS;
    public static ResponseData succNoDataResponse(PackageEnums enums) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) enums.getType());
        responseData.setCode(BASE_RESPONSE_ENUMS.getCode());
        responseData.setMessage(BASE_RESPONSE_ENUMS.desc());
        return responseData;
    }

    public static ResponseData succResponse(PackageEnums enums,Object message) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) enums.getType());
        responseData.setCode(BASE_RESPONSE_ENUMS.getCode());
        responseData.setMessage(message);
        return responseData;
    }


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

    public static ResponseData chatFailResponse(ResponseEnumsInterface responseEnumsInterface) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) PackageEnums.CHAT.getType());
        responseData.setCode(responseEnumsInterface.code());
        responseData.setMessage(responseEnumsInterface.desc());
        return responseData;
    }

    public static ResponseData userlistResponse(ResponseEnumsInterface responseEnumsInterface,String message) {
        ResponseData responseData = new ResponseData();
        responseData.setType((short) PackageEnums.USER_FRIEND.getType());
        responseData.setCode(responseEnumsInterface.code());
        responseData.setMessage(message);
        return responseData;
    }
}
