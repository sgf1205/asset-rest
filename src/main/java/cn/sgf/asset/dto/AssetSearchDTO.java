package cn.sgf.asset.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import lombok.Data;

@Data
public class AssetSearchDTO {
	private String name;
	
	private Long registerOrganId; //资产登记部门
	
	private Long usingOrganId;//使用部门
	
	private Integer status;
	
	private Double lowMoney;
	
	private Double maxMoney;
	
	private Integer pageSize;
	
	private Integer currentPage;
    
}
