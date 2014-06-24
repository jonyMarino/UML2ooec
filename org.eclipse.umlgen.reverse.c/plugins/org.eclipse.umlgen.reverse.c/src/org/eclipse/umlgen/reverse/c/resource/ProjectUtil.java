/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Mikael Barbero (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.resource;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;

public class ProjectUtil {

	public static void addToBuildSpec(IProject project, String builderID)
			throws CoreException {

		IProjectDescription description = project.getDescription();
		ICommand builderCommand = getBuilderCommand(description, builderID);

		if (builderCommand == null) {
			// Add a new build spec
			ICommand command = description.newCommand();
			command.setBuilderName(builderID);
			setBuilderCommand(project, description, command);
		}
	}

	private static ICommand getBuilderCommand(IProjectDescription description,
			String builderId) {
		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderId)) {
				return commands[i];
			}
		}
		return null;
	}

	public static void removeFromBuildSpec(IProject project, String builderID)
			throws CoreException {
		IProjectDescription description = project.getDescription();

		ICommand[] commands = description.getBuildSpec();
		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderID)) {
				ICommand[] newCommands = new ICommand[commands.length - 1];
				System.arraycopy(commands, 0, newCommands, 0, i);
				System.arraycopy(commands, i + 1, newCommands, i,
						commands.length - i - 1);
				description.setBuildSpec(newCommands);
			}
		}
		project.setDescription(description, new NullProgressMonitor());
	}

	private static void setBuilderCommand(IProject project,
			IProjectDescription description, ICommand newCommand)
			throws CoreException {

		ICommand[] oldCommands = description.getBuildSpec();
		ICommand oldBuilderCommand = getBuilderCommand(description,
				newCommand.getBuilderName());

		ICommand[] newCommands;

		if (oldBuilderCommand == null) {
			// Add a build spec after other builders
			newCommands = new ICommand[oldCommands.length + 1];
			System.arraycopy(oldCommands, 0, newCommands, 0, oldCommands.length);
			newCommands[oldCommands.length] = newCommand;
		} else {
			for (int i = 0, max = oldCommands.length; i < max; i++) {
				if (oldCommands[i] == oldBuilderCommand) {
					oldCommands[i] = newCommand;
					break;
				}
			}
			newCommands = oldCommands;
		}

		// Commit the spec change into the project
		description.setBuildSpec(newCommands);
		project.setDescription(description, null);
	}

	/**
	 * Adds the nature to the <b>.project</b> file of the current project.
	 * 
	 * @param project
	 *            The project on which the nature must be added.
	 * @throws CoreException
	 *             If the addition operation fails.
	 */
	public static void addNature(IProject project, String natureId)
			throws CoreException {
		IProjectDescription projectDesc = project.getDescription();
		String[] natureIds = projectDesc.getNatureIds();
		String[] newNatureIds = new String[natureIds.length + 1];
		System.arraycopy(natureIds, 0, newNatureIds, 0, natureIds.length);
		newNatureIds[natureIds.length] = natureId;
		projectDesc.setNatureIds(newNatureIds);
		project.setDescription(projectDesc, new NullProgressMonitor());
	}

	/**
	 * Removes the nature from the <b>.project</b> file of the current project.
	 * 
	 * @param project
	 *            The project on which the nature must be added.
	 * @throws CoreException
	 *             If the removal operation failed
	 */
	public static void removeNature(IProject project, String natureId)
			throws CoreException {
		IProjectDescription projectDesc = project.getDescription();
		String[] natureIds = projectDesc.getNatureIds();
		String[] newNatureIds = new String[natureIds.length - 1];
		for (int i = 0, j = 0; i < natureIds.length && j < newNatureIds.length; i++) {
			if (!natureId.equals(natureIds[i])) {
				newNatureIds[j] = natureIds[i];
				j++;
			}
		}
		projectDesc.setNatureIds(newNatureIds);
		project.setDescription(projectDesc, new NullProgressMonitor());
	}

	public static boolean hasNature(IProject project, String natureId) {
		boolean ret = false;
		try {
			String[] naturesId = project.getDescription().getNatureIds();
			for (int i = 0; i < naturesId.length; i++) {
				if (naturesId[i].equals(natureId)) {
					ret = true;
					break; // stop fast
				}
			}
		} catch (CoreException e) {
			// will return false, and it's ok
		}

		return ret;
	}
}
