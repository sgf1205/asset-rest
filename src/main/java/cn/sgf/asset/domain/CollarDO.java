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
 * 资产领用单
 * 
 * @author user
 *
 */
@Data
@Entity
@Table(name = "T_ASSET_COLLAR")
public class CollarDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "collar_organ_id", referencedColumnName = "id")
	private SysOrganDO CollarOrgan;// 领用部门

	@Column(name = "collar_time")
	private Date collarTime; // 领用时间

	@Column(name = "retreat_time")
	private Date retreatTime;// 归还时间

	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	private String remarks;
	
	@OneToOne
	@JoinColumn(name = "create_user_id", referencedColumnName = "id")
	private UserDO createUesr;

	@ManyToMany(cascade = CascadeType.MERGE)
	@JoinTable(name = "T_ASSET_COLLAR_MAP",joinColumns = @JoinColumn(name = "collar_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "asset_id", referencedColumnName = "id"))
	private List<AssetDO> assets;
}
