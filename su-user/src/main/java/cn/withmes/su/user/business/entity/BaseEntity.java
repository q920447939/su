/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年07月18日
 */
package cn.withmes.su.user.business.entity;


import com.alibaba.fastjson.JSONObject;

public class BaseEntity {

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
