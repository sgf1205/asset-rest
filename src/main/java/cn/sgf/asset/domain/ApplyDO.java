package cn.sgf.asset.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * 资产使用申请单
 * 
 * @author user
 *
 */
@Data
@Entity
@Table(name = "T_ASSET_APPLY")
public class ApplyDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer type;//申请单类型(1：领用, 2：借用，3:归还)

	@OneToOne
	@JoinColumn(name = "organ_id", referencedColumnName = "id")
	private SysOrganDO organ;// 提出部门

	@Column(name="apply_user")
	private String applyUser;//申请人

	@Column(name = "retreat_time")
	private Date retreatTime;// 归还时间

	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	private String remarks;
	
	@OneToOne
	@JoinColumn(name = "create_user_id", referencedColumnName = "id")
	private UserDO createUesr;

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "apply")
	private List<ApplyItemDO> items;
}
