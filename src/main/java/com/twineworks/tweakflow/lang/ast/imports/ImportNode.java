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

package com.twineworks.tweakflow.lang.ast.imports;

import com.twineworks.tweakflow.lang.analysis.AnalysisUnit;
import com.twineworks.tweakflow.lang.analysis.visitors.Visitor;
import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.ast.expressions.ExpressionNode;
import com.twineworks.tweakflow.lang.parse.SourceInfo;
import com.twineworks.tweakflow.lang.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class ImportNode implements Node {

  private ExpressionNode modulePath;
  private SourceInfo sourceInfo;
  private List<ImportMemberNode> members = new ArrayList<>();
  private AnalysisUnit importedCompilationUnit;
  private Scope scope;

  @Override
  public ImportNode copy() {
    ImportNode copy = new ImportNode();
    copy.sourceInfo = sourceInfo;
    copy.modulePath = modulePath.copy();
    for (ImportMemberNode member : members) {
      copy.members.add(member.copy());
    }
    return copy;
  }

  public List<ImportMemberNode> getMembers() {
    return members;
  }

  public ImportNode setMembers(List<ImportMemberNode> members) {
    this.members = members;
    // keep sub-nodes informed about the imported compilation unit
    for (ImportMemberNode member : members) {
      member.setImportedCompilationUnit(importedCompilationUnit);
    }

    return this;
  }

  public AnalysisUnit getImportedUnit() {
    return importedCompilationUnit;
  }

  public ImportNode setImportedUnit(AnalysisUnit importedCompilationUnit) {
    this.importedCompilationUnit = importedCompilationUnit;

    // keep sub-nodes informed about the imported compilation unit
    for (ImportMemberNode member : members) {
      member.setImportedCompilationUnit(importedCompilationUnit);
    }

    return this;
  }

  public ExpressionNode getModulePath() {
    return modulePath;
  }

  public ImportNode setModulePath(ExpressionNode modulePath) {
    this.modulePath = modulePath;
    return this;
  }

  @Override
  public SourceInfo getSourceInfo() {
    return sourceInfo;
  }

  @Override
  public ImportNode setSourceInfo(SourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    List<Node> ret = new ArrayList<>();
    ret.add(modulePath);
    ret.addAll(members);
    return ret;
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public ImportNode setScope(Scope scope) {
    this.scope = scope;
    return this;
  }

  @Override
  public ImportNode accept(Visitor visitor) {
    return visitor.visit(this);
  }




}
