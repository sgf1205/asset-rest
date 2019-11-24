package cn.sgf.asset.contorller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.google.common.collect.Lists;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import cn.afterturn.easypoi.word.parse.excel.ExcelEntityParse;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.dao.ClassesDao;
import cn.sgf.asset.dao.OrganDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.CheckInfoDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetImportDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.CheckInfoDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.handler.AssetImportVerifyHandler;
import cn.sgf.asset.service.AssetService;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

	private Logger logger = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetService assetService;
	
	@Autowired
	private ClassesDao classesDao;
	
	@Autowired
	private OrganDao organDao;
	
	@Autowired
	private AssetImportVerifyHandler assetImportVerifyHandler;

	@RequestMapping("/save")
	public RespInfo save(@RequestHeader("token") String token, AssetDTO assetDto) {
		logger.info("asset:{}", assetDto);
		UserDTO currentUserDto = AuthUtil.getUserByToken(token);
		assetService.save(assetDto, currentUserDto);
		return RespInfo.success();
	}
	

	@RequestMapping("/delete")
	public RespInfo del(@RequestHeader("token") String token, Long[] ids) {
		UserDTO currentUserDto = AuthUtil.getUserByToken(token);
		assetService.delete(ids, currentUserDto);
		return RespInfo.success();
	}

	@RequestMapping("/list")
	public RespInfo list(AssetSearchDTO searchDto) {
		return RespInfo.success(assetService.list(searchDto));
	}
	
	@GetMapping("/getByCodeAndUsingOrganId")
	public RespInfo getByCodeAndUsingOrganId(String code,Long usingOrganId) {
		return RespInfo.success(assetService.getByCodeAndUsingOrganId(code,usingOrganId));
	}

	@RequestMapping("/statistics")
	public RespInfo statistics(String type) {
		return RespInfo.success(assetService.statistics(type));
	}
	
	

	@RequestMapping("/export")
	public void export(AssetSearchDTO searchDto, HttpServletResponse response) {
		PageResult<AssetDTO> pageResult = assetService.list(searchDto);
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("资产清单", "资产清单"), AssetDTO.class,
				pageResult.getResult());
		String fileName = "asset";
		try {
			// 设置返回响应头
			response.setContentType("application/xls;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@RequestMapping("/import")
	public RespInfo assetImport(@RequestHeader("token") String token,@RequestParam MultipartFile file) {
		logger.info("fileName:{}",file.getOriginalFilename());
		UserDTO currentUserDto=AuthUtil.getUserByToken(token);
		try {
			ImportParams params = new ImportParams();
			params.setHeadRows(1);
			params.setTitleRows(1);
			params.setNeedVerify(true);
			params.setVerifyHandler(assetImportVerifyHandler);
            ExcelImportResult<AssetImportDTO> result = ExcelImportUtil.importExcelMore(
                    file.getInputStream(),
                    AssetImportDTO.class, params);
            assetService.save(result.getList(), currentUserDto);
            logger.info("导入成功结果：{},失败：{}",result.getList().size(),result.getFailList().size());
            if(result.isVerfiyFail()) {
	            FileOutputStream fos = new FileOutputStream(currentUserDto.getId()+"_import_fail.xls");
	            result.getFailWorkbook().write(fos);
	            fos.close();
            }
            Map<String,Object> importResult=new HashMap<String,Object>();
            importResult.put("success", result.getList().size());
            importResult.put("fail", result.getFailList().size());
            return RespInfo.success(importResult);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("导入异常:{}",e);
		}
		return RespInfo.success();
	}
	
	@RequestMapping("/downloadError")
	public void downloadError(@RequestHeader("token") String token,HttpServletResponse response) {
		BufferedInputStream bis=null;
		BufferedOutputStream bos =null;
		try {
			UserDTO user=AuthUtil.getUserByToken(token);
			File errorFile=new File(user.getId()+"_import_fail.xls");
			FileInputStream fis = new FileInputStream(errorFile);
			// 设置返回响应头
			response.setContentType("application/xls;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=asset");
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("导入结果下载异常：{}",e);
		}finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}
	
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletResponse response) {

		ExportParams params = new ExportParams("资产清单", "资产清单导入模板");
		Workbook workbook = ExcelExportUtil.exportExcel(params, AssetImportDTO.class, Lists.newArrayList() );
		List<ClassesDO> classesList=classesDao.findAll();
		String[] classesArray=classesList.stream().map(classes->classes.getName()).toArray(String[]::new);;
		List<SysOrganDO> organList=organDao.findAll();
		String[] organArray=organList.stream().map(organ->organ.getName()).toArray(String[]::new);
		//资产分类下拉框
		CellRangeAddressList classesRegions = new CellRangeAddressList(1, 10000, 2, 2);
		DVConstraint clasesConstraint = DVConstraint.createExplicitListConstraint(classesArray);
		HSSFDataValidation dataValidation = new HSSFDataValidation(classesRegions, clasesConstraint);
		workbook.getSheetAt(0).addValidationData(dataValidation);
		
		//部门选择下拉框
		CellRangeAddressList organRegions = new CellRangeAddressList(1, 10000,7, 7);
		DVConstraint organConstraint = DVConstraint.createExplicitListConstraint(organArray);
		dataValidation = new HSSFDataValidation(organRegions, organConstraint);
		workbook.getSheetAt(0).addValidationData(dataValidation);
		
		try {
			// 设置返回响应头
			response.setContentType("application/xls;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=asset");
			OutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
