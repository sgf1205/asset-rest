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
 * 资产报废单
 * 
 * @author user
 *
 */
@Data
@Entity
@Table(name = "T_ASSET_SCRAP")
public class ScrapDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer status;//借用单状态，参考StatusEnum


	@Column(name="scrap_user")
	private String scrapUser;//申请人


	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	
	@Column(name = "recovery_time")
	private Date recoveryTime;// 恢复时间
	
	private String remarks;
	
	@OneToOne
	@JoinColumn(name = "create_user_id", referencedColumnName = "id",updatable=false)
	private UserDO createUesr;
	
	@OneToOne
	@JoinColumn(name = "recovery_user_id", referencedColumnName = "id",insertable=false)
	private UserDO recoveryUesr;
	

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "scrap")
	private List<ApplyItemDO> items;
}
