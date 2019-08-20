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
    private ClassesDO classes;
    private String specification;
    private String sn;
    private String metering;//计量单位
    private Double money;
    @Column(name = "purchase_time")
    private Date purchaseTime;//购买时间
 
    private Integer status;
    
    @Column(name="delete_flag")
    private Integer deleteFlag;

    private String remark;
    
    private String supplier;
    private String contacts;
    private String tell;
    @Column(name = "expiry_time")
    private Date expiryTime; //维保到期时间
    private String explain;
    
    @Column(name = "register_time")
    private Date registerTime;//登记时间
    
    @OneToOne
    @JoinColumn(name="register_user_id",referencedColumnName = "id")
    private UserDO registerUser;//登记人
    
    @OneToOne
    @JoinColumn(name="register_organ_id",referencedColumnName = "id")
    private SysOrganDO registerOrgan;//登记部门
}
