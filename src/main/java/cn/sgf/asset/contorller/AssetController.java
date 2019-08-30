package cn.sgf.asset.contorller;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.sgf.asset.core.enu.DeleteEnum;
import cn.sgf.asset.core.enu.StatusEnum;
import cn.sgf.asset.core.model.PageParam;
import cn.sgf.asset.core.model.PageResult;
import cn.sgf.asset.core.model.RespInfo;
import cn.sgf.asset.core.utils.AuthUtil;
import cn.sgf.asset.dao.AssetDao;
import cn.sgf.asset.domain.AssetDO;
import cn.sgf.asset.domain.ClassesDO;
import cn.sgf.asset.domain.SysOrganDO;
import cn.sgf.asset.domain.UserDO;
import cn.sgf.asset.dto.AssetDTO;
import cn.sgf.asset.dto.AssetImportDTO;
import cn.sgf.asset.dto.AssetSearchDTO;
import cn.sgf.asset.dto.UserDTO;
import cn.sgf.asset.service.AssetService;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

	private Logger logger = LoggerFactory.getLogger(AssetController.class);
	@Autowired
	private AssetService assetService;

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
	
	@RequestMapping("/downloadTemplate")
	public void downloadTemplate(HttpServletResponse response) {
		TemplateExportParams e=new TemplateExportParams();
		ExportParams params = new ExportParams("资产清单", "资产清单导入模板", ExcelType.XSSF);
		params.setDictHandler(new GlobalExcelDictHandler());
		Workbook workbook = ExcelExportUtil.exportExcel(params, AssetImportDTO.class, Lists.newArrayList() );
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
