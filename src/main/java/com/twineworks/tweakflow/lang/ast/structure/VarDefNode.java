/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Twineworks GmbH
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.twineworks.tweakflow.lang.ast.structure;

import com.twineworks.tweakflow.lang.ast.ForHeadElementNode;
import com.twineworks.tweakflow.lang.ast.MetaDataNode;
import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.ast.SymbolNode;
import com.twineworks.tweakflow.lang.ast.expressions.ExpressionNode;
import com.twineworks.tweakflow.lang.ast.meta.DocNode;
import com.twineworks.tweakflow.lang.ast.meta.MetaNode;
import com.twineworks.tweakflow.lang.analysis.visitors.Visitor;
import com.twineworks.tweakflow.lang.parse.SourceInfo;
import com.twineworks.tweakflow.lang.scope.Scope;
import com.twineworks.tweakflow.lang.scope.Symbol;
import com.twineworks.tweakflow.lang.types.Type;

import java.util.ArrayList;
import java.util.List;

public class VarDefNode implements MetaDataNode, SymbolNode, ForHeadElementNode, NamedValueNode {

  private DocNode docNode;
  private MetaNode metaNode;
  private ExpressionNode valueExpression;

  private Type declaredType;
  private boolean isDeclaredProvided;

  private SourceInfo sourceInfo;
  private String name;
  private Scope scope;

  public boolean isDeclaredProvided() {
    return isDeclaredProvided;
  }

  public VarDefNode setDeclaredProvided(boolean declaredMutable) {
    isDeclaredProvided = declaredMutable;
    return this;
  }

  public Type getDeclaredType() {
    return declaredType;
  }

  public VarDefNode setDeclaredType(Type declaredType) {
    this.declaredType = declaredType;
    return this;
  }

  public String getSymbolName() {
    return name;
  }

  public VarDefNode setSymbolName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public DocNode getDoc() {
    return docNode;
  }

  @Override
  public MetaNode getMeta() {
    return metaNode;
  }

  @Override
  public VarDefNode setDoc(DocNode docNode) {
    this.docNode = docNode;
    return this;
  }

  @Override
  public VarDefNode setMeta(MetaNode metaNode) {
    this.metaNode = metaNode;
    return this;
  }

  @Override
  public boolean hasDoc() {
    return docNode != null;
  }

  @Override
  public boolean hasMeta() {
    return metaNode != null;
  }

  @Override
  public SourceInfo getSourceInfo() {
    return sourceInfo;
  }

  @Override
  public VarDefNode setSourceInfo(SourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    List<Node> ret = new ArrayList<>();
    if (metaNode != null) ret.add(metaNode);
    if (docNode != null) ret.add(docNode);
    ret.add(valueExpression);
    return ret;
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public VarDefNode setScope(Scope scope) {
    this.scope = scope;
    return this;
  }

  @Override
  public VarDefNode accept(Visitor visitor) {
    return visitor.visit(this);
  }

  @Override
  public ExpressionNode getValueExpression() {
    return valueExpression;
  }

  public VarDefNode setValueExpression(ExpressionNode valueExpression) {
    this.valueExpression = valueExpression;
    return this;
  }

  public Symbol getSymbol(){
    return getScope().getSymbols().get(getSymbolName());
  }

  @Override
  public VarDefNode copy() {
    VarDefNode copy = new VarDefNode();
    copy.sourceInfo = sourceInfo;
    copy.declaredType = declaredType;
    copy.isDeclaredProvided = isDeclaredProvided;
    copy.docNode = docNode == null ? null : docNode.copy();
    copy.metaNode = metaNode == null ? null : metaNode.copy();
    copy.name = name;
    copy.valueExpression = valueExpression.copy();

    return copy;
  }

}
