package com.judoguys.logging;

/**
 * Copyright (C) 2011  William Bowers <http://williambowers.net/>
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

/**
 * A simple wrapper for a java.util.logging.Logger that adds a
 * customizable string prefix to each message.
 */
public class PrefixLogger implements Logger
{
	// ======================================================================
	// PRIVATE PROPERTIES
	// ======================================================================
	
	/**
	 * The actual logger that will be doing the logging.
	 */
	private java.util.logging.Logger logger;
	
	/**
	 * The prefix to place before each message.
	 */
	private String prefix;
	
	// ======================================================================
	// CONSTRUCTORS
	// ======================================================================
	
	public PrefixLogger (java.util.logging.Logger logger, String prefix)
	{
		setLogger(logger);
		setPrefix(prefix);
	}
	
	// ======================================================================
	// ACCESSORS
	// ======================================================================
	
	// ~ logger
	
	/**
	 * Returns the actual logging object.
	 */
	public java.util.logging.Logger getLogger ()
	{
		return logger;
	}
	
	/**
	 * Sets the actual logging object.
	 */
	public void setLogger (java.util.logging.Logger value)
	{
		if (value == null) {
			throw new IllegalArgumentException("PrefixLogger given a null logger");
		}
		logger = value;
	}
	
	// ~ prefix
	
	/**
	 * Returns the prefix to place before each message.
	 */
	public String getPrefix ()
	{
		return prefix;
	}
	
	/**
	 * Sets the prefix to place before each message.
	 */
	public void setPrefix (String value)
	{
		if (value == null) {
			// Make sure prefix is never null.
			value = "";
		}
		prefix = value;
	}
	
	// ~ level
	
	/**
	 * Sets the minimum level this logger will log. All messages
	 * with levels below this level will be ignored.
	 */
	public void setLevel (Level value)
	{
		getLogger().setLevel(value);
	}
	
	// ======================================================================
	// PUBLIC METHODS
	// ======================================================================
	
	/**
	 * Logs a CONFIG message.
	 */
	public void config (String message)
	{
		getLogger().config(makeMessage(message));
	}
	
	/**
	 * Logs a FINE message.
	 */
	public void fine (String message)
	{
		getLogger().fine(makeMessage(message));
	}
	
	/**
	 * Logs a FINER message.
	 */
	public void finer (String message)
	{
		getLogger().finer(makeMessage(message));
	}
	
	/**
	 * Logs a FINEST message.
	 */
	public void finest (String message)
	{
		getLogger().finest(makeMessage(message));
	}
	
	/**
	 * Logs an INFO message.
	 */
	public void info (String message)
	{
		getLogger().info(makeMessage(message));
	}
	
	/**
	 * Logs a SEVERE message.
	 */
	public void severe (String message)
	{
		getLogger().severe(makeMessage(message));
	}
	
	/**
	 * Logs a WARNING message.
	 */
	public void warning (String message)
	{
		getLogger().warning(makeMessage(message));
	}
	
	// ======================================================================
	// PROTECTED METHODS
	// ======================================================================
	
	/**
	 * Adds the prefix to the given message.
	 */
	protected String makeMessage (String message)
	{
		return getPrefix() + message;
	}
}
