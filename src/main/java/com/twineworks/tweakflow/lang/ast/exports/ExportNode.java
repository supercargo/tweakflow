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

package com.twineworks.tweakflow.lang.ast.exports;

import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.ast.SymbolNode;
import com.twineworks.tweakflow.lang.ast.expressions.ReferenceNode;
import com.twineworks.tweakflow.lang.analysis.visitors.Visitor;
import com.twineworks.tweakflow.lang.parse.SourceInfo;
import com.twineworks.tweakflow.lang.scope.Scope;
import com.twineworks.tweakflow.lang.scope.Symbol;

import java.util.Collections;
import java.util.List;

public class ExportNode implements SymbolNode {

  private ReferenceNode source;
  private SourceInfo sourceInfo;
  private String symbolName;
  private Scope scope;


  @Override
  public ExportNode copy() {
    ExportNode copy = new ExportNode();
    copy.symbolName = symbolName;
    copy.sourceInfo = sourceInfo;
    copy.source = source.copy();
    return copy;
  }

  @Override
  public SourceInfo getSourceInfo() {
    return sourceInfo;
  }

  @Override
  public ExportNode setSourceInfo(SourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    return Collections.singletonList(source);
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public Node setScope(Scope scope) {
    this.scope = scope;
    return this;
  }

  public ReferenceNode getSource() {
    return source;
  }

  public ExportNode setSource(ReferenceNode source) {
    this.source = source;
    return this;
  }

  @Override
  public String getSymbolName() {
    return symbolName;
  }

  @Override
  public ExportNode setSymbolName(String symbolName) {
    this.symbolName = symbolName;
    return this;
  }

  public Symbol getExportedSymbol(){
    return scope.getPublicScope().getSymbols().get(getSymbolName());
  }

  public Symbol getSymbol(){
    return scope.getSymbols().get(getSymbolName());
  }

  @Override
  public ExportNode accept(Visitor visitor) {
    return visitor.visit(this);
  }


}
