package cn.sgf.asset.contorller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.joran.action.ActionUtil;
import cn.sgf.asset.core.RespInfo;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.RoleEnum;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.ClassesDao;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.UserDTO;


@RestController
@RequestMapping("/api/classes")
public class ClassesController {

	private Logger logger = LoggerFactory.getLogger(ClassesController.class);
	
	@Autowired
	private ClassesDao classesDao;
	
	@Value("${user.defaultPwd}")
	private String defaultPwd;
	
	
	
	@RequestMapping("/save")
	public RespInfo save(ClassesDO classesDo) {
		logger.info("classesDo:{}",classesDo);
		classesDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		classesDao.save(classesDo);
		return RespInfo.success();
	}
	
	
	@RequestMapping("/delete")
	public RespInfo delete(Long id) {
		logger.info("delete classes id:{}",id);
		ClassesDO classesDo=classesDao.getOne(id);
		classesDo.setDeleteFlag(DeleteEnum.DELETED.getCode());
		classesDao.save(classesDo);
		return RespInfo.success();
	}
	
	@RequestMapping("/list")
	public RespInfo list() {
		List<ClassesDO> list=classesDao.findByDeleteFlag(DeleteEnum.NO_DELETED.getCode());
		return RespInfo.success(list);
	}
}
