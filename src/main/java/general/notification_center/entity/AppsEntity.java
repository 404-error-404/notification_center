package general.notification_center.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.IEntity;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import cn.org.atool.fluent.mybatis.functions.TableSupplier;
import java.io.Serializable;
import java.lang.Class;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * AppsEntity: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@SuppressWarnings("unchecked")
@Data
@Accessors(
    chain = true
)
@EqualsAndHashCode(
    callSuper = false
)
@FluentMybatis(
    table = "apps"
)
public class AppsEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * 主键，对应app的id
   */
  @TableId(
      value = "app_id",
      auto = false
  )
  private Long appId;

  /**
   * app名字
   */
  @TableField("app_name")
  private String appName;

  /**
   * app token验证使用
   */
  @TableField("app_token")
  private String appToken;

  @Override
  public Serializable findPk() {
    return this.appId;
  }

  @Override
  public final Class<? extends IEntity> entityClass() {
    return AppsEntity.class;
  }

  @Override
  public final AppsEntity changeTableBelongTo(TableSupplier supplier) {
    return super.changeTableBelongTo(supplier);
  }

  @Override
  public final AppsEntity changeTableBelongTo(String table) {
    return super.changeTableBelongTo(table);
  }
}
