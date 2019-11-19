package cn.sgf.asset.dto;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.UserDO;
import lombok.Data;

@Data
public class CheckSearchDTO {
	
	
	private Long organId;//使用部门
	
	
	private Integer pageSize;
	
	private Integer currentPage;
    
}
