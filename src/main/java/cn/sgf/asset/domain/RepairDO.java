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
 * 资产报修单
 * 
 * @author user
 *
 */
@Data
@Entity
@Table(name = "T_ASSET_REPAIR")
public class RepairDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Integer status;//状态，参考StatusEnum


	@Column(name="report_user")
	private String reportUser;//申请人


	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	
	@Column(name = "finish_time")
	private Date finishTime;// 恢复时间
	
	private String remarks;
	
	@OneToOne
	@JoinColumn(name = "create_user_id", referencedColumnName = "id",updatable=false)
	private UserDO createUesr;
	
	@OneToOne
	@JoinColumn(name = "finish_user_id", referencedColumnName = "id",insertable=false)
	private UserDO finishUesr;
	

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "repair")
	private List<ApplyItemDO> items;
}
