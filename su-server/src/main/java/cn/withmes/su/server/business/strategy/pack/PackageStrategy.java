/**
 * @Project:
 * @Author: leegoo
 * @Date: 2023年06月25日
 */
package cn.withmes.su.server.business.strategy.pack;

import cn.withmes.su.server.business.enums.PackageEnums;
import cn.withmes.su.server.business.pack.Package;

/**
 * ClassName: PackageStrategy
 * @Description: 数据包处理策略
 * @author leegoo
 * @date 2023年06月25日
 */
public interface PackageStrategy {
    PackageEnums getPackageEnums();
    Package handle(Package aPackage);
}

