/*******************************************************************************
 * Copyright (c) 2010, 2014 CS Systèmes d'Information (CS-SI).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Christophe LE CAMUS (CS-SI) - initial API and implementation
 *     Sebastien GABEL (CS-SI) - evolutions
 *******************************************************************************/
package org.eclipse.umlgen.reverse.c.reconciler;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorObjectStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.model.ITranslationUnit;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.umlgen.c.common.BundleConstants;
import org.eclipse.umlgen.reverse.c.event.AbstractCModelChangedEvent;
import org.eclipse.umlgen.reverse.c.event.CommentAdded;
import org.eclipse.umlgen.reverse.c.event.CommentRemoved;
import org.eclipse.umlgen.reverse.c.event.FunctionBodyChanged;
import org.eclipse.umlgen.reverse.c.internal.reconciler.SameFileLocation;

/**
 * AST Comment reconciler.
 *
 * @author clecamus
 */
public class ASTCommentReconciler extends AbstractReconciler {

    /** the include inactive nodes. */
    private boolean includeInactiveNodes;

    /** decalage. */
    private final int decalage = 1;

    /** original map. */
    private TreeMap<Integer, IASTNode> originalMap;

    /** new map. */
    private TreeMap<Integer, IASTNode> newMap;

    /** original AST. */
    private IASTTranslationUnit originalAst;

    /** new AST. */
    private IASTTranslationUnit newAst;

    /**
     * Reconcile.
     *
     * @param originalUnit
     *            the original unit.
     * @param newUnit
     *            The new unit.
     * @throws CoreException
     *             exception.
     */
    public void reconcile(ITranslationUnit originalUnit, ITranslationUnit newUnit) throws CoreException {
        originalAst = originalUnit.getAST();
        IASTDeclaration[] originalDecls = originalAst.getDeclarations(includeInactiveNodes);

        Collection<IASTDeclaration> originalDeclsFiltered = Collections2.filter(Lists
                .newArrayList(originalDecls), new SameFileLocation(originalAst));

        originalMap = fillMap(originalUnit, originalDeclsFiltered);
        this.reconcile(newUnit, originalUnit, originalDecls);
    }

    /**
     * Reconcile.
     *
     * @param newUnit
     *            The new unit.
     * @param originalUnit
     *            The original unit.
     * @param originalDecls
     *            The original declarations
     * @throws CoreException
     *             exception.
     */
    public void reconcile(ITranslationUnit newUnit, ITranslationUnit originalUnit,
            IASTDeclaration[] originalDecls) throws CoreException {
        // fill the maps with considered typed elements
        newAst = newUnit.getAST();
        IASTDeclaration[] newDecls = newAst.getDeclarations(includeInactiveNodes);

        Collection<IASTDeclaration> newDeclsFiltered = Collections2.filter(Lists.newArrayList(newDecls),
                new SameFileLocation(newUnit.getAST()));

        newMap = fillMap(newUnit, newDeclsFiltered);
        if (originalMap == null) {
            originalMap = new TreeMap<Integer, IASTNode>();
        }

        AbstractCModelChangedEvent event = null;
        String body = "";
        IASTNode parent = null;
        IASTNode oldparent = null;
        boolean addOrRemove = true;
        IASTComment comment = findCommentAdded(originalMap, newMap);
        IASTComment oldComment = findCommentRemoved(originalMap, newMap);

        while (comment != null || oldComment != null) {
            addOrRemove = true;
            if (originalMap.size() == newMap.size()) {
                addOrRemove = false;
            }
            if (addOrRemove && comment != null) {
                // try {
                parent = findParentOfComment(newMap, newAst, comment);
                if (!(parent instanceof IASTFunctionDefinition) && !(parent instanceof IASTInitializerList)) {
                    body = computeStartOfComment(newMap, comment).concat(
                            computeEndOfComment(newMap, comment, parent));
                    if (body.trim().length() > 0) {
                        event = CommentAdded.builder().setBody(body).setParent(parent).setSource(comment)
                                .translationUnit(newUnit).build();
                    }
                } else {
                    if (parent instanceof IASTFunctionDefinition
                            && comment.getFileLocation().getNodeOffset() < parent.getFileLocation()
                                    .getNodeOffset()) {
                        body = computeStartOfComment(newMap, comment).concat(
                                computeEndOfComment(newMap, comment, parent));
                        if (body.trim().length() > 0) {
                            event = CommentAdded.builder().setBody(body).setParent(parent).setSource(comment)
                                    .translationUnit(newUnit).build();
                        }
                    }
                }
                // } catch (Exception e) {
                // Activator.log(e);
                // }
            } else {
                // try {
                if (addOrRemove && oldComment != null) {
                    oldparent = findParentOfComment(originalMap, originalAst, oldComment);
                    if (!(oldparent instanceof IASTFunctionDefinition)
                            && !(parent instanceof IASTInitializerList)) {
                        body = computeStartOfComment(originalMap, oldComment).concat(
                                computeEndOfComment(originalMap, oldComment, parent));
                        if (body.trim().length() > 0) {
                            event = CommentRemoved.builder().setBody(body).setParent(oldparent).setSource(
                                    oldComment).translationUnit(newUnit).build();
                        }
                    }
                } else {
                    if (!addOrRemove && comment != null) {
                        oldparent = findParentOfComment(originalMap, originalAst, oldComment);
                        parent = findParentOfComment(newMap, newAst, comment);
                        if (!(oldparent instanceof IASTFunctionDefinition)
                                && !(parent instanceof IASTInitializerList)) {
                            // deletion of old comment
                            body = computeStartOfComment(originalMap, oldComment).concat(
                                    computeEndOfComment(originalMap, oldComment, parent));
                            if (body.trim().length() > 0) {
                                event = CommentRemoved.builder().setBody(body).setParent(oldparent)
                                        .setSource(oldComment).translationUnit(originalUnit).build();
                                notifyListeners(event, false);
                            }
                        }
                        if (!(parent instanceof IASTFunctionDefinition)
                                && !(parent instanceof IASTInitializerList)) {
                            // addition of the new Comment
                            body = computeStartOfComment(newMap, comment).concat(
                                    computeEndOfComment(newMap, comment, parent));
                            if (body.trim().length() > 0) {
                                event = CommentAdded.builder().setBody(body).setParent(parent).setSource(
                                        comment).translationUnit(newUnit).build();
                            }
                        } else {
                            if (parent instanceof IASTFunctionDefinition) {
                                String newbody = ((IASTFunctionDefinition)parent).getBody().getRawSignature();
                                String oldBody = ((IASTFunctionDefinition)oldparent).getBody()
                                        .getRawSignature();
                                String currentName = ((IASTFunctionDefinition)parent).getDeclarator()
                                        .getName().toString();
                                event = FunctionBodyChanged.builder().setBody(newbody).setOldBody(oldBody)
                                        .currentName(currentName).translationUnit(newUnit).build();
                            }
                        }
                    }
                }
                // } catch (Exception e) {
                // Activator.log(e);
                // }
            }
            if (event != null) {
                notifyListeners(event, false);
                event = null;
            }
            // try {
            if (comment != null) {
                newMap = removeCommentsFrom(newMap, comment, parent);
                comment = findCommentAdded(originalMap, newMap);
            }
            if (comment == null && oldComment != null) {
                originalMap = removeCommentsFrom(originalMap, oldComment, parent);
                oldComment = findCommentRemoved(originalMap, newMap);
            }
            // } catch (Exception e) {
            // Activator.log(e);
            // }
        }
    }

    /**
     * This checks if the given comment is before or inside the parent node.
     *
     * @param comment
     *            The comment
     * @param parent
     *            The parent node
     * @return True if yes.
     */
    private boolean isBeforeOrInsideParent(IASTComment comment, IASTNode parent) {
        // is after
        boolean result = false;
        // is before
        if (comment.getFileLocation().getNodeOffset() < parent.getFileLocation().getNodeOffset()) {
            result = true;
        } else if (comment.getFileLocation().getStartingLineNumber() == parent.getFileLocation()
                .getStartingLineNumber()) {
            // is on the same line before the parent
            result = true;
        } else if (comment.getFileLocation().getEndingLineNumber() == parent.getFileLocation()
                .getEndingLineNumber()) {
            // is on the same line after the parent
            result = true;
        } else if (comment.getFileLocation().getNodeOffset() < parent.getFileLocation().getNodeOffset()
                + parent.getFileLocation().getNodeLength()) {
            // is inside
            result = true;
        }

        return result;
    }

    /**
     * Remove the current comment and the immediate following ones.
     *
     * @param treeMap
     *            tree map
     * @param comment
     *            comment
     * @param parent
     *            parent
     * @return : cleaned TreeMap
     */
    private TreeMap<Integer, IASTNode> removeCommentsFrom(TreeMap<Integer, IASTNode> treeMap,
            IASTComment comment, IASTNode parent) {
        boolean notEnd = true;
        Iterator<Integer> iterator = treeMap.keySet().iterator();
        Map<Integer, Integer> removalMap = new HashMap<Integer, Integer>();
        while (iterator.hasNext() && notEnd) {
            Integer integer = iterator.next();
            if (integer.intValue() > 0
                    && integer.intValue() <= comment.getFileLocation().getNodeOffset() + decalage) {
                if (treeMap.get(integer) instanceof IASTComment) {
                    if (isBeforeOrInsideParent(comment, parent)) {
                        removalMap.put(integer, integer);
                    }
                }
            }
            if (integer.intValue() > comment.getFileLocation().getNodeOffset() + decalage) {
                if (treeMap.get(integer) instanceof IASTComment) {
                    if (isBeforeOrInsideParent((IASTComment)treeMap.get(integer), parent)) {
                        removalMap.put(integer, integer);
                    } else {
                        notEnd = false;
                    }
                } else {
                    notEnd = false;
                }
            }
        }
        for (Integer integer : removalMap.keySet()) {
            treeMap.remove(integer);
        }
        return treeMap;
    }

    /**
     * Compute the start of comment.
     *
     * @param map
     *            map
     * @param comment
     *            comment
     * @return The result
     */
    public String computeStartOfComment(TreeMap<Integer, IASTNode> map, IASTComment comment) {
        StringBuilder returnString = new StringBuilder();
        for (Integer integer : map.keySet()) {
            Object element = map.get(integer);
            if (element instanceof IASTComment) {
                IASTComment currentComment = (IASTComment)element;
                if (currentComment != comment) {
                    if (currentComment.getFileLocation().getNodeOffset() < comment.getFileLocation()
                            .getNodeOffset()) {
                        returnString = returnString.append(new String(((IASTComment)element).getComment()))
                                .append(BundleConstants.LINE_SEPARATOR);
                    } else if (currentComment.getFileLocation().getNodeOffset() == comment.getFileLocation()
                            .getNodeOffset()) {
                        return returnString.toString();
                    }
                }
            }
        }
        return returnString.toString();
    }

    /**
     * Compute the end of comment.
     *
     * @param map
     *            map
     * @param comment
     *            comment
     * @param parent
     *            parent
     * @return The result
     */
    public String computeEndOfComment(TreeMap<Integer, IASTNode> map, IASTComment comment, IASTNode parent) {
        StringBuilder returnString = new StringBuilder();
        boolean notReached = true;
        boolean notEnd = true;

        for (Iterator<Integer> iterator = map.keySet().iterator(); iterator.hasNext()
                && (notReached || notEnd);) {
            Integer key = iterator.next();
            IASTNode element = map.get(key);

            if (element instanceof IASTComment) {
                if (parent instanceof IASTTranslationUnit
                        || element instanceof IASTTranslationUnit
                        || element.getFileLocation().getEndingLineNumber() <= parent.getFileLocation()
                                .getStartingLineNumber()) {
                    // we must have reached the comment to concat the following
                    // comments ...
                    if (!notReached) {
                        returnString = returnString.append(new String(((IASTComment)element).getComment()))
                                .append(BundleConstants.LINE_SEPARATOR);
                    }
                    // check if have reach the starting comment
                    if ((IASTComment)element == comment) {
                        returnString = returnString.append(new String(((IASTComment)element).getComment()));
                        notReached = false;
                    }
                } else if (!notReached) {
                    notEnd = false;
                }
            } else if (!notReached) {
                // if we have already reached the comment and we found an Element so
                // end of search.
                notEnd = false;
            }
        }
        return returnString.toString();
    }

    /**
     * Find the parent of the given parent.
     *
     * @param map
     *            map
     * @param astTranslationUnitNode
     *            translation unit node
     * @param comment
     *            comment
     * @return The parent node
     */
    // CHECKSTYLE:OFF
    public IASTNode findParentOfComment(TreeMap<Integer, IASTNode> map,
            IASTTranslationUnit astTranslationUnitNode, IASTComment comment) {
        Iterator<Integer> iterator = map.keySet().iterator();

        // The Unit
        IASTNode previousNode = astTranslationUnitNode;
        IASTNode followingNode = astTranslationUnitNode;
        boolean endOfCompare = false;

        while (iterator.hasNext()
                && followingNode.getFileLocation().getNodeOffset() < comment.getFileLocation()
                        .getNodeOffset()) {
            Integer key = iterator.next();
            Object element = map.get(key);
            if (!endOfCompare) {
                if (!(element instanceof IASTComment)) {
                    if (key.intValue() > 0) {
                        if (((IASTNode)element).getFileLocation().getNodeOffset() > comment.getFileLocation()
                                .getNodeOffset()) {
                            followingNode = (IASTNode)element;
                            endOfCompare = true;
                        } else {
                            previousNode = (IASTNode)element;
                            followingNode = (IASTNode)element;
                        }
                    }
                }
            }
        }

        // a node has been probably reached but the previousNode is the Unit so
        // the parent is the Unit
        if (previousNode instanceof IASTTranslationUnit) {
            return astTranslationUnitNode;
        } else {
            if (previousNode instanceof IASTParameterDeclaration) {
                if (previousNode.getFileLocation().getStartingLineNumber() == comment.getFileLocation()
                        .getStartingLineNumber()
                        || previousNode.getFileLocation().getEndingLineNumber() == comment.getFileLocation()
                                .getEndingLineNumber()) {
                    return previousNode;
                } else {
                    if (comment.getFileLocation().getNodeOffset() > previousNode.getParent()
                            .getFileLocation().getNodeOffset()
                            && comment.getFileLocation().getNodeOffset() < previousNode.getParent()
                                    .getFileLocation().getNodeOffset()
                                    + previousNode.getParent().getFileLocation().getNodeLength()) {
                        if (previousNode.getParent() instanceof IASTFunctionDeclarator) {
                            return ((IASTFunctionDeclarator)previousNode.getParent()).getParent();
                        }
                    } else {
                        // The comment is the parent of the parameter
                        if (previousNode.getParent() instanceof IASTFunctionDeclarator) {
                            IASTNode parentParameter = ((IASTFunctionDeclarator)previousNode.getParent())
                                    .getParent();
                            if (comment.getFileLocation().getNodeOffset() > parentParameter.getFileLocation()
                                    .getNodeOffset()
                                    && comment.getFileLocation().getNodeOffset() < parentParameter
                                            .getFileLocation().getNodeOffset()
                                            + parentParameter.getFileLocation().getNodeLength()) {
                                return ((IASTFunctionDeclarator)previousNode.getParent()).getParent();
                            }
                        }
                        return followingNode;
                    }
                }
            } else {
                // we encountered the first include statement and the comment is
                // the comment of the class
                if (previousNode instanceof IASTPreprocessorObjectStyleMacroDefinition
                        && followingNode instanceof IASTPreprocessorIncludeStatement) {
                    return followingNode.getParent();
                }
                // we encountered the first include and the comment is the
                // comment of the class
                if (previousNode instanceof IASTTranslationUnit
                        && followingNode instanceof IASTPreprocessorIncludeStatement) {
                    return astTranslationUnitNode;
                }
                // in line comments
                if (previousNode.getFileLocation().getStartingLineNumber() == comment.getFileLocation()
                        .getStartingLineNumber()
                        && previousNode.getFileLocation().getEndingLineNumber() == comment.getFileLocation()
                                .getEndingLineNumber()) {
                    // is after the node but on the same line
                    if (previousNode.getFileLocation().getNodeOffset() <= comment.getFileLocation()
                            .getNodeOffset()) {
                        return previousNode;
                    } else {
                        // is before the node on the same line
                        return followingNode;
                    }
                }
                if (previousNode instanceof IASTSimpleDeclaration) {
                    if (((IASTSimpleDeclaration)previousNode).getDeclSpecifier() instanceof IASTEnumerationSpecifier) {
                        if (comment.getFileLocation().getStartingLineNumber() == previousNode
                                .getFileLocation().getEndingLineNumber()) {
                            return previousNode;
                        }
                        if (comment.getFileLocation().getNodeOffset() < previousNode.getFileLocation()
                                .getNodeOffset()
                                + previousNode.getFileLocation().getNodeLength()) {
                            return getParentEnumerator(
                                    (IASTEnumerationSpecifier)((IASTSimpleDeclaration)previousNode)
                                            .getDeclSpecifier(), comment);
                        }
                    }
                }
                // the following node has not evolved
                if (followingNode instanceof IASTTranslationUnit) {
                    return astTranslationUnitNode;
                }
                // general case
                return followingNode;
            }
        }
        return null;
    }

    // CHECKSTYLE:ON

    /**
     * Get the parent enumerator.
     *
     * @param enumeration
     *            The enumeration
     * @param comment
     *            The comment
     * @return The enumerator
     */
    private IASTEnumerator getParentEnumerator(IASTEnumerationSpecifier enumeration, IASTComment comment) {
        IASTEnumerator enumerator = null;
        for (IASTEnumerator enumLiteral : enumeration.getEnumerators()) {
            if (enumLiteral != null) {
                if (enumLiteral.getFileLocation().getStartingLineNumber() == comment.getFileLocation()
                        .getStartingLineNumber()
                        && enumLiteral.getFileLocation().getEndingLineNumber() == comment.getFileLocation()
                                .getEndingLineNumber()) {
                    enumerator = enumLiteral;
                    break;
                } else if (enumLiteral.getFileLocation().getStartingLineNumber() > comment.getFileLocation()
                        .getEndingLineNumber()) {
                    enumerator = enumLiteral;
                    break;
                }
            }
        }
        return enumerator;
    }

    /**
     * Find the removed comment.
     *
     * @param oldMap
     *            The old map
     * @param pNewMap
     *            The new map
     * @return The comment
     */
    private IASTComment findCommentRemoved(TreeMap<Integer, IASTNode> oldMap,
            TreeMap<Integer, IASTNode> pNewMap) {
        return findCommentAdded(pNewMap, oldMap);
    }

    /**
     * Find the added comment.
     *
     * @param oldMap
     *            The old map
     * @param pNewMap
     *            The new map
     * @return The comment
     */
    // CHECKSTYLE:OFF
    private IASTComment findCommentAdded(TreeMap<Integer, IASTNode> oldMap, TreeMap<Integer, IASTNode> pNewMap) {
        Object oldKey = null;
        IASTNode element = null;
        IASTNode oldElement = null;
        Integer index = new Integer(0);
        boolean found = false;
        IASTNode fromNode = null;
        StringBuilder newComment = null;
        StringBuilder oldComment = null;

        for (Iterator<Integer> newIterator = pNewMap.keySet().iterator(); newIterator.hasNext() && !found;) {
            found = false;
            index = newIterator.next();
            element = pNewMap.get(index);

            if (!(element instanceof IASTComment)) {
                fromNode = element;
                newComment = new StringBuilder();
            } else {
                if (element instanceof IASTComment) {
                    // concat the comment to compare eventually repetitions of
                    // comment
                    newComment = newComment.append(new String(((IASTComment)element).getComment()));

                    Iterator<Integer> originalIterator = oldMap.keySet().iterator();
                    IASTNode fromOldElement = null;
                    while (originalIterator.hasNext() && index != null) {
                        oldKey = originalIterator.next();
                        oldElement = oldMap.get(oldKey);
                        if (!(oldElement instanceof IASTComment)) {
                            fromOldElement = oldElement;
                            oldComment = new StringBuilder();
                        } else {
                            // we add a comment at the beginning of the file
                            if (fromNode instanceof IASTTranslationUnit
                                    && fromOldElement instanceof IASTTranslationUnit) {
                                if (element.getRawSignature().equals(oldElement.getRawSignature())) {
                                    index = null;
                                } else {
                                    if (oldElement.getFileLocation().getNodeOffset() >= element
                                            .getFileLocation().getNodeOffset()) {
                                        return (IASTComment)pNewMap.get(index);
                                    }
                                }
                            }
                            if (fromOldElement != null && fromNode != null
                                    && fromOldElement.getRawSignature().equals(fromNode.getRawSignature())) {
                                // concat the comment to compare eventually
                                // repetitions of comment
                                oldComment = oldComment.append(new String(((IASTComment)oldElement)
                                        .getComment()));

                                if (oldComment.toString().equals(newComment.toString())) {
                                    index = null;
                                }
                            } else {
                                // In case of IASTTranslationUnit the raw
                                // signature is the complete file, so test the
                                // comment

                                // concat the comment to compare eventually
                                // repetitions of comment
                                oldComment = oldComment.append(new String(((IASTComment)oldElement)
                                        .getComment()));

                                if (fromOldElement instanceof IASTTranslationUnit
                                        && fromNode instanceof IASTTranslationUnit
                                        && oldElement instanceof IASTComment) {
                                    if (oldComment.toString().equals(newComment.toString())) {
                                        index = null;
                                    }
                                }
                            }
                        }
                    }
                    if (index != null) {
                        found = true;
                    }
                }
            }
        }
        if (found) {
            return (IASTComment)pNewMap.get(index);
        }
        return null;
    }

    // CHECKSTYLE:ON

    /**
     * Fill map.
     *
     * @param tu
     *            : the translation unit
     * @param declarations
     *            : the declarations of aTranslationUnit
     * @return : the map with all considered objects
     * @throws CoreException
     *             exception
     */
    public TreeMap<Integer, IASTNode> fillMap(ITranslationUnit tu, Collection<IASTDeclaration> declarations)
            throws CoreException {
        IASTTranslationUnit astTU = tu.getAST();
        IASTTranslationUnit emptyAst = astTU.getASTNodeFactory().newTranslationUnit(null);
        TreeMap<Integer, IASTNode> map = new TreeMap<Integer, IASTNode>();
        map.put(0, emptyAst);

        /* fill map with all comments encountered into the file */
        IASTComment[] originalComments = astTU.getComments();

        Collection<IASTComment> originalCommentsFiltered = Collections2.filter(Lists
                .newArrayList(originalComments), new SameFileLocation(astTU));

        for (IASTComment originalComment : originalCommentsFiltered) {
            map.put(originalComment.getFileLocation().getNodeOffset() + decalage, originalComment);
        }

        /* fill map with all pre-processor statements encountered into the file */
        IASTPreprocessorStatement[] originalPreProcessorStatements = astTU.getAllPreprocessorStatements();

        Collection<IASTPreprocessorStatement> originalPreProcessorStatementsFiltered = Collections2.filter(
                Lists.newArrayList(originalPreProcessorStatements), new SameFileLocation(astTU));

        for (IASTPreprocessorStatement originalPreProcessorStatement : originalPreProcessorStatementsFiltered) {
            map.put(originalPreProcessorStatement.getFileLocation().getNodeOffset() + decalage,
                    originalPreProcessorStatement);
        }

        IASTNode[] children = null;
        /* fill map with declarations found inside the file */
        for (IASTDeclaration declaration : declarations) {
            map.put(new Integer(declaration.getFileLocation().getNodeOffset() + decalage), declaration);

            if (declaration instanceof IASTFunctionDefinition || declaration instanceof IASTSimpleDeclaration) {

                // CHECKSTYLE:OFF
                if (declaration instanceof IASTFunctionDefinition) {
                    children = ((IASTFunctionDefinition)declaration).getDeclarator().getChildren();
                } else if (declaration instanceof IASTSimpleDeclaration) {
                    IASTDeclSpecifier specifier = ((IASTSimpleDeclaration)declaration).getDeclSpecifier();
                    if (specifier instanceof IASTEnumerationSpecifier) {
                        children = ((IASTEnumerationSpecifier)specifier).getEnumerators();
                    } else if (specifier instanceof IASTCompositeTypeSpecifier) {
                        children = ((IASTCompositeTypeSpecifier)specifier).getMembers();
                    } else if (specifier instanceof IASTElaboratedTypeSpecifier) {
                        children = ((IASTElaboratedTypeSpecifier)specifier).getChildren();
                    } else if (((IASTSimpleDeclaration)declaration).getDeclarators().length > 0) {
                        IASTDeclarator declarator = ((IASTSimpleDeclaration)declaration).getDeclarators()[0];
                        if (declarator instanceof IASTFunctionDeclarator) {
                            children = declarator.getChildren();
                        } else if (declarator instanceof IASTArrayDeclarator) {
                            IASTInitializer initializer = declarator.getInitializer();
                            if (initializer != null) {
                                IASTNode[] initializerLists = initializer.getChildren();
                                for (IASTNode initializerList : initializerLists) {
                                    if (initializerList instanceof IASTInitializerList) {
                                        for (IASTNode init : ((IASTInitializerList)initializerList)
                                                .getChildren()) {
                                            map.put(init.getFileLocation().getNodeOffset() + decalage, init);
                                        }
                                    }
                                }
                            }
                        }
                    }

                }

                if (children != null) {
                    for (IASTNode child : children) {
                        if (!(child instanceof IASTName)) {
                            if (child instanceof IASTParameterDeclaration) {
                                map.put(child.getFileLocation().getNodeOffset() + decalage, child);
                            }
                        }
                    }
                }
                // CHECKSTYLE:ON
            }
        }
        // remove the comment ending the file if present.
        // Choice has been made to consider beginning comment for class comment
        while (map.get(map.lastKey()) instanceof IASTComment) {
            map.remove(map.lastKey());
        }

        return map;
    }
}
