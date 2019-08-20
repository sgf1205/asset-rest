package cn.sgf.asset.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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
    private Date purchaseTime;//购买时间
 
    private Integer status;
    
    private Integer deleteFlag;

    private String remark;
    
    private String supplier;
    private String contacts;
    private String tell;
    private Date expiryTime; //维保到期时间
    private String explain;
    
    private Date registerTime;//登记时间
    
    private Long organId;//登记部门
    private String organName;
    
    private String registerUserName;
    
    private UserDO registerUser;//登记人
}
