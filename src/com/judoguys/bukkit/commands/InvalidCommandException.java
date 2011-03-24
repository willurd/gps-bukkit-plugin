package com.judoguys.bukkit.commands;

public class InvalidCommandException extends Exception
{
	private static final long serialVersionUID = 1L;

	public InvalidCommandException ()
	{
		super("Unknown error");
	}
	
	public InvalidCommandException (String message)
	{
		super(message);
	}
}
