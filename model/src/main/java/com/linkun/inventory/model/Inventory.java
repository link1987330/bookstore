package com.linkun.inventory.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 库存信息
 * </p>
 *
 * @author linkun
 * @since 2024-12-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_inventory")
@ApiModel(value="Inventory对象", description="库存信息")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "图书ID（实际中应该用条码）")
    private Long bookId;

    @ApiModelProperty(value = "当前库存数")
    private Integer quantity;

    @ApiModelProperty(value = "最小库存数（提醒补货）")
    private Integer minQuantity;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUserId;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    private Boolean defunct;


}
