package cn.sgf.asset.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import cn.sgf.asset.domain.AssetDO;
import lombok.Data;

/**
 * 资产领用单
 * 
 * @author user
 *
 */
@Data
public class CollarDTO {
	
	private Long id;

	private Long collarOrganId;
	
	private String collarOrganName;

	private Date collarTime; // 领用时间

	private Date retreatTime;// 归还时间

	private Date createTime;// 创建时间
	
	private String remarks;//备注
	
	private List<AssetDO> assets;
}
