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
public class ScrapDTO {
	
	private Long id;

	private String scrapUser;

	private Date createTime;// 创建时间
	
	private Date recoveryTime;//恢复时间
	
	private Integer status;//报废单状态
	
	private String remarks;//备注
	
	private Long[] assetIds; //关联的资产ID
	
}
