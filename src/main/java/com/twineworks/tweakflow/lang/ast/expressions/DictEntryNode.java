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

package com.twineworks.tweakflow.lang.ast.expressions;

import com.twineworks.tweakflow.lang.analysis.visitors.Visitor;
import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.parse.SourceInfo;
import com.twineworks.tweakflow.lang.scope.Scope;

import java.util.Arrays;
import java.util.List;

public class DictEntryNode implements Node {

  private ExpressionNode key;
  private ExpressionNode value;

  private SourceInfo sourceInfo;
  private Scope scope;

  @Override
  public DictEntryNode copy() {
    DictEntryNode copy = new DictEntryNode();
    copy.sourceInfo = sourceInfo;
    copy.key = key.copy();
    copy.value = value.copy();
    return copy;
  }

  public ExpressionNode getKey() {
    return key;
  }

  public void setKey(ExpressionNode key) {
    this.key = key;
  }

  public ExpressionNode getValue() {
    return value;
  }

  public void setValue(ExpressionNode value) {
    this.value = value;
  }

  @Override
  public SourceInfo getSourceInfo() {
    return sourceInfo;
  }

  @Override
  public DictEntryNode setSourceInfo(SourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    return Arrays.asList(key, value);
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public DictEntryNode setScope(Scope scope) {
    this.scope = scope;
    return this;
  }

  @Override
  public DictEntryNode accept(Visitor visitor) {
    return visitor.visit(this);
  }

}
