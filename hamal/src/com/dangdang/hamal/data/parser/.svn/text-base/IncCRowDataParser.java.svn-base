/**
 * copyright @ dangdang.com 2015
 */
package com.dangdang.hamal.data.parser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dangdang.hamal.data.Cell;
import com.dangdang.hamal.data.IncCRowData;
import com.dangdang.hamal.data.IncCRowData.IncCAction;
import com.dangdang.hamal.mysql.core.event.DeleteRowsEventData;
import com.dangdang.hamal.mysql.core.event.Event;
import com.dangdang.hamal.mysql.core.event.EventType;
import com.dangdang.hamal.mysql.core.event.TableMapEventData;
import com.dangdang.hamal.mysql.core.event.TableMapEventDataCache;
import com.dangdang.hamal.mysql.core.event.UpdateRowsEventData;
import com.dangdang.hamal.mysql.core.event.WriteRowsEventData;
import com.dangdang.hamal.mysql.meta.FieldMeta;
import com.dangdang.hamal.mysql.meta.TableMeta;
import com.dangdang.hamal.mysql.meta.TableMetaCache;

/**
 * 增量变化数据 解析类
 * @author zhangkunjs
 * @
 */
public class IncCRowDataParser {

	/**
	 * 从Event事件中获取 具体修改、删除、插入的数据
	 * TODO 数据类型为Blog的暂未处理
	 * @param event
	 * @return
	 */
	public static IncCRowData parse(Event event)
	{
		EventType et= event.getHeader().getEventType();
		if(EventType.isDelete(et)||EventType.isUpdate(et)||EventType.isWrite(et))
		{
			IncCRowData incCRow =new IncCRowData();
			incCRow.setTs(event.getHeader().getTimestamp());

			if(EventType.isDelete(et))
			{
				incCRow.setAction(IncCAction.DELETE);
				DeleteRowsEventData eventData=(DeleteRowsEventData)event.getData();
				long tableid=eventData.getTableId();			

				incCRow.setTblid(tableid);
				TableMapEventData tableMeta=TableMapEventDataCache.get(tableid);
				incCRow.setDb(tableMeta.getDatabase());
				incCRow.setTbl(tableMeta.getTable());					
				setDeleteOrInsertRows(incCRow,eventData.getRows());
			}
			else if(EventType.isUpdate(et))
			{
				incCRow.setAction(IncCAction.UPDATE);
				UpdateRowsEventData eventData=(UpdateRowsEventData)event.getData();
				long tableid=eventData.getTableId();		
				incCRow.setTblid(tableid);

				TableMapEventData tableMeta=TableMapEventDataCache.get(tableid);
				incCRow.setDb(tableMeta.getDatabase());
				incCRow.setTbl(tableMeta.getTable());		
				setUpdateRows(incCRow,eventData.getUpdatedRows(),eventData.getUpdatedCoulumnIdxs());	
			}
			else if(EventType.isWrite(et))
			{
				incCRow.setAction(IncCAction.INSERT);
				WriteRowsEventData eventData=(WriteRowsEventData)event.getData();
				long tableid=eventData.getTableId();		
				incCRow.setTblid(tableid);						
				TableMapEventData tableMeta=TableMapEventDataCache.get(tableid);
				incCRow.setDb(tableMeta.getDatabase());
				incCRow.setTbl(tableMeta.getTable());	
				setDeleteOrInsertRows(incCRow,eventData.getRows());		
			}
			return incCRow;
		}
		return null;
	}	
	
	public static void setDeleteOrInsertRows(IncCRowData incCRow, List<Serializable[]> rows)
	{
		TableMeta tableMeta=TableMetaCache.get(incCRow.getFull());
		FieldMeta[] fieldMetas=tableMeta.getFields();
		List<ArrayList<Cell>> lsRow=new ArrayList<ArrayList<Cell>>();
		for(Serializable[] row:rows)
		{
			ArrayList<Cell> cells=new ArrayList<Cell>();
			for(int i=0;i<row.length;i++)
			{				
				Cell cell=new Cell();
				cell.setN(fieldMetas[i].getF_name());
				cell.setV(row[i]);
				cell.setUpd(false);
				cells.add(cell);
			}
			lsRow.add(cells);
		}
		incCRow.setRows(lsRow);
	}
	
	public  static void setUpdateRows(IncCRowData incCRow,List<Serializable[]> rows,List<Integer[]> updatedColumnIdxs)
	{
		TableMeta tableMeta=TableMetaCache.get(incCRow.getFull());
		FieldMeta[] fieldMetas=tableMeta.getFields();
		List<ArrayList<Cell>> lsRow=new ArrayList<ArrayList<Cell>>();
		int j=0;
		for(Serializable[] row:rows)
		{
			ArrayList<Cell> cells=new ArrayList<Cell>();
			Integer[] updateIdx=updatedColumnIdxs.get(j);
			j++;
			for(int i=0;i<row.length;i++)
			{				
				Cell cell=new Cell();
				cell.setN(fieldMetas[i].getF_name());
				cell.setV(row[i]);
				boolean updated=false;
				for(int idx:updateIdx)
				{
					if(idx==i)
					{
						updated=true;
						break;
					}
				}
				cell.setUpd(updated);
				cells.add(cell);
			}
			lsRow.add(cells);			
		}
		incCRow.setRows(lsRow);
	}
	
}
