package cn.sgf.asset.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "T_ASSET")
public class AssetDO {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name = "bar_code")
    private String barCode;
    private String name;
    private String type;
    private String specification;
    private String sn;
    private String metering;
    private String money;
    private String company;
    private String department;
    @Column(name = "purchase_time")
    private Date purchaseTime;//购买时间
    @Column(name = "user_id")
    private String user;
    @Column(name = "manager_id")
    private String managerId;
    private String status;
    private String address;
    private String durationUse;
    private String source;
    private String remarks;
    private String image;
    private String supplier;
    private String contacts;
    private String tell;
    @Column(name = "expiry_time")
    private String expiryTime;
    private String explainprivate;
}
