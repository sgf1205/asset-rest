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
public class AssetDTO {
	private Long id;
	@Excel(name="资产编号",orderNum = "1",width=40)
    private String code;
	@Excel(name="资产名称",orderNum = "2",width=30)
    private String name;
    private Long classesId;
    private ClassesDO classes;
    @Excel(name="资产类别",orderNum = "4")
    private String classesName;
    
    private String classesCode;
    @Excel(name="品牌型号",orderNum = "3")
    private String specification;
    private String sn;
    private String metering;
    @Excel(name="单价",orderNum = "8")
    private Double money;
    @Excel(name="购置时间",orderNum = "6",format = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date purchaseTime;//购买时间
 
    private Integer status;
    
    @Excel(name="资产状态",orderNum = "13")
    private String statusLabel;
    
    private Integer deleteFlag;

    private String remark;
    
    private String supplier;
    private String contacts;
    private String tell;
    
    @Excel(name="财务记账日期",orderNum = "14",format = "yyyy-MM-dd",width=20)
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date accountingDate;//财务记账日期
    
    @Excel(name="财务记账凭账号",orderNum = "15",width=20)
    private String accountingNo;//财务记账凭证号
    @Excel(name="预计使用年限",orderNum = "7",width=20)
    private Integer life;//预计使用年限
    @Excel(name="资产来源",orderNum = "5")
    private String source;//资产来源
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date expiryTime; //维保到期时间
    private String explain;
    

    private Date registerTime;//登记时间
    
    private Long organId;//登记部门
    @Excel(name="所属部门",orderNum = "9")
    private String organName;
    
    private String organCode;
    
    private Long usingOrganId;
    
    @Excel(name="存放地点",orderNum = "11")
    private String usingOrganName;
    
    @Excel(name="使用人",orderNum = "10")
    private String usingUser;
    
    @Excel(name="领用时间",orderNum = "12",format = "yyyy-MM-dd HH:mm:ss",width=40)
    private Date usingTime;
    
    private String registerUserName;
    
    private UserDO registerUser;//登记人
    
}
