package cn.sgf.asset.handler;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.afterturn.easypoi.excel.entity.result.ExcelVerifyHandlerResult;
import cn.afterturn.easypoi.handler.inter.IExcelVerifyHandler;
import cn.sgf.asset.dao.ClassesDao;
import cn.sgf.asset.dao.OrganDao;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.dto.AssetImportDTO;

@Service
public class AssetImportVerifyHandler implements IExcelVerifyHandler<AssetImportDTO> {

	@Autowired
	private OrganDao organDao;
	
	@Autowired
	private ClassesDao classesDao;
	
	@Override
	public ExcelVerifyHandlerResult verifyHandler(AssetImportDTO assetImportDto) {
		// TODO Auto-generated method stub
		ExcelVerifyHandlerResult result=new ExcelVerifyHandlerResult();
		result.setSuccess(true);
		ClassesDO classesDo=classesDao.findByName(assetImportDto.getClassesName());
		String msg="";
		if(classesDo==null) {
			msg="找不到"+assetImportDto.getClassesName()+"的资产分类;";
		}else {
			assetImportDto.setClassesId(classesDo.getId());
		}
		SysOrganDO organDo=organDao.findByName(assetImportDto.getOrganName());
		if(organDo==null) {
			msg+="找不到"+assetImportDto.getClassesName()+"部门;";
		}else {
			assetImportDto.setOrganId(organDo.getId());
		}
		if(StringUtils.isNotEmpty(msg)) {
			result.setSuccess(false);
			result.setMsg(msg);
		}
		return result;
	}

}
