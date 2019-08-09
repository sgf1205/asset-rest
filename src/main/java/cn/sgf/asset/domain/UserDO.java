package cn.sgf.asset.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "AUTH_USER")
@Data
@NamedEntityGraph(name="user.all",attributeNodes={@NamedAttributeNode("organ")})
public class UserDO {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(length = 32)
    private String name;
    @Column(length = 32)
    private String account;
    @Column(length = 64)
    private String pwd;
    
    
    @Column(name="role_id")
    private Integer roleId;
    
    @OneToOne
    @JoinColumn(name="organ_id",referencedColumnName = "id")
    private SysOrganDO organ;
    
    @Column(name="delete_flag")
    private int deleteFlag;
    
}