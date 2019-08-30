package cn.sgf.asset.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import lombok.Data;

@Data
public class AssetImportDTO {
	@Excel(name="资产名称",orderNum = "1",width=30)
    private String name;
    @Excel(name="资产类别",orderNum = "3",dict="classDict")
    private String classesName;
    @Excel(name="品牌型号",orderNum = "2")
    private String specification;
    @Excel(name="单价",orderNum = "7")
    private Double money;
    @Excel(name="购置时间",orderNum = "5",format = "yyyy-MM-dd")
    private Date purchaseTime;//购买时间
    
    @Excel(name="财务记账日期",orderNum = "9",format = "yyyy-MM-dd",width=20)
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date accountingDate;//财务记账日期
    
    @Excel(name="财务记账凭账号",orderNum = "10",width=20)
    private String accountingNo;//财务记账凭证号
    @Excel(name="预计使用年限",orderNum = "6",width=20)
    private Integer life;//预计使用年限
    @Excel(name="资产来源",orderNum = "4")
    private String source;//资产来源
    
    private Long organId;//登记部门
    @Excel(name="所属部门",orderNum = "8",dict = "organDict")
    private String organName;
    
    
}
