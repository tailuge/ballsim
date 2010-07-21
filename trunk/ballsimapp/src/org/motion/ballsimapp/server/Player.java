package org.motion.ballsimapp.server;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Player {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String id;
	
	@Persistent
	private String tableId;

	public Player(String id, String tableId)
	{
		this.id = id;
		this.tableId = tableId;
	}
	
	
	
	public String getId()
	{
		return id;
	}
	
	public String getTableId()
	{
		return tableId;
	}
	

}
