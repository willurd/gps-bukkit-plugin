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

/**
 * Defines the API for a simple logger (using the types defined in
 * {@link java.util.logging.Level}).
 */
public interface Logger
{
	/**
	 * Logs a CONFIG message.
	 */
	void config (String message);
	
	/**
	 * Logs a FINE message.
	 */
	void fine (String message);
	
	/**
	 * Logs a FINER message.
	 */
	void finer (String message);
	
	/**
	 * Logs a FINEST message.
	 */
	void finest (String message);
	
	/**
	 * Logs an INFO message.
	 */
	void info (String message);
	
	/**
	 * Logs a SEVERE message.
	 */
	void severe (String message);
	
	/**
	 * Logs a WARNING message.
	 */
	void warning (String message);
}
