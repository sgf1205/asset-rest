package cn.sgf.asset.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import lombok.Data;

@Data
public class AssetImportDTO {
	@Excel(name="资产名称",orderNum = "1",width=30)
	@NotNull(message = "资产名称不能为空")
    private String name;
    @Excel(name="资产类别",orderNum = "3")
    private String classesName;
    
    private Long classesId;
    
    @Excel(name="品牌型号",orderNum = "2")
    @NotNull(message = "品牌型号不能为空")
    private String specification;
    @Excel(name="单价",orderNum = "7")
    @Min(value = 0,message = "单价需要填入数值")
    private String money;
    @Excel(name="购置时间",orderNum = "5",format = "yyyy-MM-dd")
    @NotNull(message = "购买时间不能为空")
    private Date purchaseTime;//购买时间
    
    @Excel(name="财务记账日期",orderNum = "9",format = "yyyy-MM-dd",width=20)
    @NotNull(message = "财务记账日期不能为空")
    private Date accountingDate;//财务记账日期
    
    @Excel(name="财务记账凭账号",orderNum = "10",width=20)
    @NotNull(message = "财务记账凭账号不能为空")
    private String accountingNo;//财务记账凭证号
    @Excel(name="预计使用年限",orderNum = "6",width=20)
    @Min(value = 0,message = "预计使用年限需要填入数值")
    private String life;//预计使用年限
    @Excel(name="资产来源",orderNum = "4")
    @NotNull(message = "资产来源不能为空")
    private String source;//资产来源
    
    @Excel(name="所属部门",orderNum = "8")
    private String organName;
    
    private Long organId;//登记部门 
    
    
}
