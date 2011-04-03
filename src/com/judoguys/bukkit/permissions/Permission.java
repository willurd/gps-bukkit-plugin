package com.judoguys.bukkit.permissions;

public class Permission
{
	private String name;
	
	public Permission (String name)
	{
		setName(name);
	}
	
	public String getName ()
	{
		return name;
	}
	
	public void setName (String value)
	{
		name = value;
	}
}
