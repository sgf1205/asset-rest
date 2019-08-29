package cn.sgf.asset.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import lombok.Data;

@Data
public class AssetDTO {
	private Long id;
    private String code;
    private String name;
    private Long classesId;
    private ClassesDO classes;
    private String classesName;
    private String specification;
    private String sn;
    private String metering;
    private Double money;
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date purchaseTime;//购买时间
 
    private Integer status;
    
    private Integer deleteFlag;

    private String remark;
    
    private String supplier;
    private String contacts;
    private String tell;
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date accountingDate;//财务记账日期
    
    private String accountingNo;//财务记账凭证号
    
    private Integer life;//预计使用年限
    
    private String source;//资产来源
    @DateTimeFormat(pattern="yyyy-MM-dd")  
    private Date expiryTime; //维保到期时间
    private String explain;
    
    private Date registerTime;//登记时间
    
    private Long organId;//登记部门
    private String organName;
    
    private Long usingOrganId;
    
    private String usingOrganName;
    
    private String registerUserName;
    
    private UserDO registerUser;//登记人
    
}
