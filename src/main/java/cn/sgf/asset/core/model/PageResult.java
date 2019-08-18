package cn.sgf.asset.core.model;

import java.util.List;

import lombok.Data;

@Data
public class PageResult<T> {
	private Integer totalPage;
	private Integer currentPage;
	private Long totalSize;
	private List<T> result;
}
