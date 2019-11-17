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

import cn.sgf.asset.domain.ApplyItemDO;
import cn.sgf.asset.domain.AssetDO;
import lombok.Data;

/**
 * 资产使用申请单
 * 
 * @author user
 *
 */
@Data
public class ApplyDTO {
	
	private Long id;
	
	private Integer type;

	private Long organId;
	
	private String organName;

	private String applyUser;
	
	private Date retreatTime;// 归还时间

	private Date createTime;// 创建时间
	
	private Date applyTime;//领用时间
	
	private Date expectRetreatTime;//预计归还时间
	
	private Integer status;//借用单状态
	
	private String remarks;//备注
	
	private Long[] assetIds; //关联的资产ID
	
}
