/*******************************************************************************
 * Copyright (c) 2010, 2014 Obeo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *      Stephane Thibaudeau (Obeo) - initial API and implementation
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.activity.comments;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;
import org.eclipse.umlgen.reverse.c.activity.beans.CommentInfo;

public class CommentsReconciler {
	private Map<IASTNode, NodeLocationInfo> nodesLocationInfo = Maps.newHashMap();

	public Map<IASTNode, CommentInfo> reconcile(IASTFunctionDefinition function) {
		// First, we collect all the interesting statements
		List<IASTNode> interestingNodes = collectInterestingNodes(function);

		// Then, collect only the comments for the current function
		List<IASTComment> commentsToAnalyze = collectCommentsForFunction(function);

		return buildNodesAndComments(commentsToAnalyze, interestingNodes);
	}

	private Map<IASTNode, CommentInfo> buildNodesAndComments(List<IASTComment> commentsToAnalyze,
			List<IASTNode> interestingNodes) {
		Map<IASTNode, CommentInfo> nodesAndComments = Maps.newHashMap();

		for (IASTComment comment : commentsToAnalyze) {
			IASTNode node = getNodeForComment(comment, interestingNodes);
			if (node != null) {
				CommentInfo info = buildCommentInfo(node, comment, nodesAndComments.get(node));
				nodesAndComments.put(node, info);
			}
		}
		return nodesAndComments;
	}

	private CommentInfo buildCommentInfo(IASTNode node, IASTComment comment, CommentInfo existingInfo) {
		CommentInfo info = existingInfo;
		if (info == null) {
			info = new CommentInfo();
		}

		int commentFirstLine = getStartingLine(comment);
		int stmtFirstLine = getStartingLine(node);
		int commentOffset = getStartingOffset(comment);
		int stmtOffset = getStartingOffset(node);

		if (commentFirstLine < stmtFirstLine) {
			info.addBefore(comment.getRawSignature());
		} else if (commentFirstLine == stmtFirstLine) {
			if (isInlined(comment, node)) {
				info.addInline(comment.getRawSignature());
			} else if (commentOffset < stmtOffset) {
				info.addBefore(comment.getRawSignature());
			} else {
				info.addSameLine(comment.getRawSignature());
			}

		} else {
			// Comment is after the start of the statement
			int stmtLastLine = getEndingLine(node);
			if (commentFirstLine < stmtLastLine) {
				info.addLastLine(comment.getRawSignature());
			}
		}

		return info;
	}

	private List<IASTComment> collectCommentsForFunction(IASTFunctionDefinition function) {
		int startingOffset = getStartingOffset(function);
		int endingOffset = getEndingOffset(function);
		List<IASTComment> commentsWithoutStatements = Lists.newArrayList();
		for (IASTComment comment : function.getTranslationUnit().getComments()) {
			int cmtStartingOffset = getStartingOffset(comment);
			int cmtEndingOffset = getEndingOffset(comment);
			if (cmtStartingOffset >= startingOffset && cmtEndingOffset <= endingOffset) {
				commentsWithoutStatements.add(comment);
			}
		}
		return commentsWithoutStatements;
	}

	private IASTNode getNodeForComment(IASTComment comment, List<IASTNode> interestingNodes) {
		int cmtStartingOffset = getStartingOffset(comment);
		int cmtEndingOffset = getEndingOffset(comment);
		int cmtStartingLine = getStartingLine(comment);

		/*
		 * We try to determinate : - the nearest enclosing node - the nearest following node - the nearest
		 * node on the same line
		 */

		IASTNode nearestEnclosingNode = null;
		int nearestEnclosingNodeOffset = 0;
		IASTNode nearestFollowingNode = null;
		int nearestFollowingNodeOffset = Integer.MAX_VALUE;
		IASTNode nearestNodeOnSameLine = null;
		int nearestNodeOnSameLineOffset = 0;

		for (IASTNode node : interestingNodes) {
			// If the comment is inlined in the node then return this node
			if (isInlined(comment, node)) {
				return node;
			}
			int stmtStartingOffset = getStartingOffset(node);
			int stmtEndingOffset = getEndingOffset(node);
			int stmtStartingLine = getStartingLine(node);

			// Nearest enclosing statement
			if (stmtStartingOffset < cmtStartingOffset && stmtEndingOffset > cmtEndingOffset
					&& stmtStartingOffset > nearestEnclosingNodeOffset) {
				nearestEnclosingNodeOffset = stmtStartingOffset;
				nearestEnclosingNode = node;
			}

			// Nearest following statement
			if (stmtStartingOffset > cmtEndingOffset && stmtStartingOffset < nearestFollowingNodeOffset) {
				nearestFollowingNodeOffset = stmtStartingOffset;
				nearestFollowingNode = node;
			}

			// Nearest statement on same line
			if (cmtStartingOffset > stmtEndingOffset && stmtStartingLine == cmtStartingLine
					&& stmtEndingOffset > nearestNodeOnSameLineOffset) {
				nearestNodeOnSameLineOffset = stmtEndingOffset;
				nearestNodeOnSameLine = node;
			}

		}

		IASTNode bestNode = pickBestNode(cmtStartingLine, nearestEnclosingNode, nearestFollowingNode,
				nearestNodeOnSameLine);
		return bestNode;
	}

	private boolean isInlined(IASTComment comment, IASTNode node) {
		return getStartingOffset(comment) >= getStartingOffsetForInline(node)
				&& getEndingOffset(comment) <= getEndingOffsetForInline(node);
	}

	private IASTNode pickBestNode(int cmtStartingLine, IASTNode nearestEnclosingNode,
			IASTNode nearestFollowingNode, IASTNode nearestNodeOnSameLine) {
		IASTNode bestNode = null;

		// We choose the best one among the closest nodes
		if (nearestNodeOnSameLine != null) {
			// check if the comment is not inlined into the enclosing node
			if (nearestEnclosingNode != null) {
				int endOffset1 = getEndingOffset(nearestNodeOnSameLine);
				int startOffset2 = getStartingOffset(nearestEnclosingNode);
				if (startOffset2 > endOffset1) {
					bestNode = nearestEnclosingNode;
				} else {
					bestNode = nearestNodeOnSameLine;
				}
			} else {
				bestNode = nearestNodeOnSameLine;
			}
		} else {
			// if the nearest following node is itself enclosed by the nearest
			// enclosing statement
			// we choose this one
			if (nearestFollowingNode != null) {
				if (nearestEnclosingNode == null) {
					bestNode = nearestFollowingNode;
				} else {
					int startOffset1 = getStartingOffset(nearestFollowingNode);
					int endOffset1 = getEndingOffset(nearestFollowingNode);
					int startLine1 = getStartingLine(nearestFollowingNode);
					int startOffset2 = getStartingOffset(nearestEnclosingNode);
					int endOffset2 = getEndingOffset(nearestEnclosingNode);
					int startLine2 = getStartingLine(nearestEnclosingNode);
					if (startLine2 == cmtStartingLine && startLine1 != cmtStartingLine) {
						bestNode = nearestEnclosingNode;
					} else if (startOffset1 > startOffset2 && endOffset1 < endOffset2) {
						bestNode = nearestFollowingNode;
					} else {
						bestNode = nearestEnclosingNode;
					}
				}
			} else if (nearestEnclosingNode != null) {
				bestNode = nearestEnclosingNode;
			}
		}
		return bestNode;
	}

	private List<IASTNode> collectInterestingNodes(IASTFunctionDefinition function) {
		List<IASTNode> result = Lists.newArrayList();
		result.add(function.getBody());
		collectNodes(function.getBody(), result);
		return result;
	}

	private void collectNodes(IASTNode node, List<IASTNode> nodes) {
		if (node == null) {
			return;
		}
		if (node instanceof IASTSwitchStatement) {
			nodes.add(node);
			IASTSwitchStatement a = (IASTSwitchStatement)node;
			collectNodes(a.getBody(), nodes);
		} else if (node instanceof IASTForStatement) {
			nodes.add(node);
			IASTForStatement a = (IASTForStatement)node;
			collectNodes(a.getBody(), nodes);
		} else if (node instanceof IASTWhileStatement) {
			nodes.add(node);
			IASTWhileStatement a = (IASTWhileStatement)node;
			collectNodes(a.getBody(), nodes);
		} else if (node instanceof IASTDoStatement) {
			nodes.add(node);
			IASTDoStatement a = (IASTDoStatement)node;
			collectNodes(a.getBody(), nodes);
		} else if (node instanceof IASTIfStatement) {
			nodes.add(node);
			IASTIfStatement a = (IASTIfStatement)node;
			nodes.add(a.getThenClause());
			collectNodes(a.getThenClause(), nodes);
			if (a.getElseClause() != null) {
				nodes.add(a.getElseClause());
				collectNodes(a.getElseClause(), nodes);
			}
		} else if (node instanceof IASTCompoundStatement) {
			IASTCompoundStatement a = (IASTCompoundStatement)node;
			for (IASTStatement st : a.getStatements()) {
				collectNodes(st, nodes);
			}
		} else {
			nodes.add(node);
		}
	}

	private int getStartingOffset(IASTNode node) {
		return getLocationInfo(node).getStartingOffset();
	}

	private int getEndingOffset(IASTNode node) {
		return getLocationInfo(node).getEndingOffset();
	}

	private int getStartingLine(IASTNode node) {
		return getLocationInfo(node).getStartingLine();
	}

	private int getEndingLine(IASTNode node) {
		return getLocationInfo(node).getEndingLine();
	}

	private int getStartingOffsetForInline(IASTNode node) {
		return getLocationInfo(node).getStartingOffsetForInline();
	}

	private int getEndingOffsetForInline(IASTNode node) {
		return getLocationInfo(node).getEndingOffsetForInline();
	}

	private NodeLocationInfo getLocationInfo(IASTNode node) {
		// Retrieve from cache
		NodeLocationInfo locationInfo = nodesLocationInfo.get(node);
		if (locationInfo == null) {
			// Not in cache, collect the information and put it in cache
			locationInfo = new NodeLocationInfo(node);
			nodesLocationInfo.put(node, locationInfo);
		}
		return locationInfo;
	}
}
