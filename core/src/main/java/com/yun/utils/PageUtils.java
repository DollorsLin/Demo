/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.yun.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 *
 * @author Mark sunlightcs@gmail.com
 */
@ApiModel(value = "Page")
public class PageUtils<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	@ApiModelProperty(value = "总记录数")
	private int totalCount;
	/**
	 * 每页记录数
	 */
	@ApiModelProperty(value = "每页记录数")
	private int pageSize;
	/**
	 * 总页数
	 */
	@ApiModelProperty(value = "总页数")
	private int totalPage;
	/**
	 * 当前页数
	 */
	@ApiModelProperty(value = "当前页数")
	private int currPage;
	/**
	 * 列表数据
	 */
	@ApiModelProperty(value = "列表数据")
	private List<T> list;

	//添加无参的构造器
	public PageUtils(){
	}


	/**
	 * 分页
	 * @param list        列表数据
	 * @param totalCount  总记录数
	 * @param pageSize    每页记录数
	 * @param currPage    当前页数
	 */
	public PageUtils(List<T> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 * 分页
	 */
	public PageUtils(IPage<T> page) {
		this.list = page.getRecords();
		this.totalCount = (int)page.getTotal();
		this.pageSize = (int)page.getSize();
		this.currPage = (int)page.getCurrent();
		this.totalPage = (int)page.getPages();
	}

	/**
	 * github mapper分页
	 * @param page
	 * @param
	 * @return
	 */
	public  PageUtils(Page page){
		List<T> result = page.getResult();
		int totalCount =  Integer.parseInt(page.getTotal() + "");
		int pageSize = page.getPageSize();
		int currPage = page.getPageNum();
		int totalPage = page.getPages();

		this.list = result;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
