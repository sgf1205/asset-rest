package cn.sgf.asset;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import cn.sgf.asset.core.config.UserConfig;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.RoleEnum;
import cn.sgf.asset.dao.RoleConfigDao;
import cn.sgf.asset.dao.UserDao;
import cn.sgf.asset.domain.RoleConfigDO;
import cn.sgf.asset.domain.UserDO;

public class AppStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        UserDao userDao = contextRefreshedEvent.getApplicationContext().getBean(UserDao.class);
        UserConfig userConfig=contextRefreshedEvent.getApplicationContext().getBean(UserConfig.class);
        userConfig.getDefaultUsers().forEach(userMap->{
        	 UserDO userDo=new UserDO();
             userDo.setName(userMap.get("name"));
             userDo.setAccount(userMap.get("account"));
             userDo.setPwd(userConfig.getDefaultPwd());
             userDo.setDeleteFlag(DeleteEnum.NO_DELETED.getCode());
             if(userDo.getAccount().equals("super")) {
            	 userDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
             }else {
            	 userDo.setRoleId(RoleEnum.NORMAL_ROLE.getCode());
             }
             userDao.save(userDo);
        });
       
        RoleConfigDao roleConfigDao = contextRefreshedEvent.getApplicationContext().getBean(RoleConfigDao.class);
        RoleConfigDO roleConfigDo=new RoleConfigDO();
        roleConfigDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
        roleConfigDo.setRoleName(RoleEnum.SYS_ROLE.getName());
        roleConfigDo.setMenu("Manage");
        roleConfigDao.save(roleConfigDo);
        roleConfigDo=new RoleConfigDO();
        roleConfigDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
        roleConfigDo.setRoleName(RoleEnum.SYS_ROLE.getName());
        roleConfigDo.setMenu("Classes");
        roleConfigDao.save(roleConfigDo);
        roleConfigDo=new RoleConfigDO();
        roleConfigDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
        roleConfigDo.setRoleName(RoleEnum.SYS_ROLE.getName());
        roleConfigDo.setMenu("organ");
        roleConfigDao.save(roleConfigDo);
        roleConfigDo=new RoleConfigDO();
        roleConfigDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
        roleConfigDo.setRoleName(RoleEnum.SYS_ROLE.getName());
        roleConfigDo.setMenu("user");
        roleConfigDao.save(roleConfigDo);
        roleConfigDo=new RoleConfigDO();
        roleConfigDo.setRoleId(RoleEnum.SYS_ROLE.getCode());
        roleConfigDo.setRoleName(RoleEnum.SYS_ROLE.getName());
        roleConfigDo.setMenu("roleConfig");
        roleConfigDao.save(roleConfigDo);
        
    }
}

