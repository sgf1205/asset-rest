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
@Table(name = "T_ASSET_CHECKINFO")
public class CheckInfoDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "organ_id", referencedColumnName = "id")
	private SysOrganDO organ;// 盘点人所在部门

	@Column(name="user")
	private String user;//盘点人


	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	@Column(name = "apply_time")
	private Date checkTime;// 盘点时间
	
	@Column(name="need_check_asset_ids")
	private String needCheckAssets; //待盘点资产ID，逗号分割
	
	@Column(name="already_check_asset_ids")
	private String alreadyCheckAssets; //已盘点资产ID,逗号分割
	
	@OneToOne
	@JoinColumn(name = "create_user_id", referencedColumnName = "id")
	private UserDO createUesr;

}
