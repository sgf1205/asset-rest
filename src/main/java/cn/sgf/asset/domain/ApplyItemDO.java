package cn.sgf.asset.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 资产申请单关联的具体资源
 * 
 * @author user
 *
 */
@Data
@Entity
@Table(name = "T_ASSET_APPLY_ITEM")
public class ApplyItemDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "apply_id", referencedColumnName = "id")
	private ApplyDO apply;// 领用单
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "scrap_id", referencedColumnName = "id")
	private ScrapDO scrap;// 报废单
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "repair_id", referencedColumnName = "id")
	private RepairDO repair;// 报修单

	@ManyToOne
	@JoinColumn(name = "asset_id", referencedColumnName = "id")
	private AssetDO asset; // 领用资产
	
	@Column(name = "retreat_time")
	private Date retreatTime;// 归还时间

	@Column(name = "create_time")
	private Date createTime;// 创建时间
	
	private Integer status; //资产状态
	
}
