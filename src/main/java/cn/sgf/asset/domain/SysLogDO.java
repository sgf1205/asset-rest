package cn.sgf.asset.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "SYS_LOG")
@Data
public class SysLogDO {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
    @OneToOne
    @JoinColumn(name="ops_user_id",referencedColumnName = "id")
	private UserDO opsUser;
	
    @Column(name="ops_time")
	private Date opsTime;
	
    @Column(name="`describe`")
	private String describe;
	
	private Integer type;
}
