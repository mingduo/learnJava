package com.ais.brm.common.utils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Excle操作.
 * Created by xuechen on 2016-10-14.
 *
 * @author xuechen
 */
public class ImportSetInfo
{
	private Map<String, List<List<String>>> objsMap = new HashMap<String, List<List<String>>>();

	private String[] titles = new String[] {};

	private List<String[]> headNames = new ArrayList<String[]>();

	private OutputStream out;

	public Map<String, List<List<String>>> getObjsMap()
	{
		return objsMap;
	}

	public void setObjsMap(Map<String, List<List<String>>> objsMap)
	{
		this.objsMap = objsMap;
	}

	public String[] getTitles()
	{
		return titles;
	}

	public void setTitles(String[] titles)
	{
		this.titles = titles;
	}

	public List<String[]> getHeadNames()
	{
		return headNames;
	}

	public void setHeadNames(List<String[]> headNames)
	{
		this.headNames = headNames;
	}

	public OutputStream getOut()
	{
		return out;
	}

	public void setOut(OutputStream out)
	{
		this.out = out;
	}

}
