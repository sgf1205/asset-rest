package cn.sgf.asset.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "T_ASSET")
public class AssetDO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "code")
    private String code;
    private String name;
    @OneToOne
    @JoinColumn(name="classes_id",referencedColumnName = "id")
    private ClassesDO classes;//资产类别
    private String specification;//品牌型号
    private String sn;
    private String metering;//计量单位
    private Double money;//单价
    
    
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @Column(name = "purchase_time")
    private Date purchaseTime;//购置时间
    
    private Date accountingDate;//财务记账日期
    
    private String accountingNo;//财务记账凭证号
    
    private Integer life;//预计使用年限
    
    private String source;//资产来源
 
    private Integer status;
    
    @Column(name="delete_flag")
    private Integer deleteFlag;

    private String remark;
    
    private String supplier;
    private String contacts;
    private String tell;
    
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @Column(name = "expiry_time")
    private Date expiryTime; //维保到期时间
    private String explain;
    
    
    
    @Column(name = "register_time",updatable=false)
    private Date registerTime;//登记时间
    
    @OneToOne
    @JoinColumn(name="register_user_id",referencedColumnName = "id",updatable=false)
    private UserDO registerUser;//登记人
    
    @OneToOne
    @JoinColumn(name="register_organ_id",referencedColumnName = "id")
    private SysOrganDO registerOrgan;//登记部门
    
    @OneToOne
    @JoinColumn(name="using_organ_id",referencedColumnName = "id")
    private SysOrganDO usingOrgan;//当前使用部门
    
    @OneToOne
    @JoinColumn(name="edit_user_id",referencedColumnName = "id",insertable=false)
    private UserDO editUser;//修改人
    
    @Column(name = "edit_time",insertable=false)
    private Date editTime;//修改时间
}
