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
import com.twineworks.tweakflow.lang.types.Type;

import java.util.Collections;
import java.util.List;

public class CastNode extends AExpressionNode implements ExpressionNode {

  private ExpressionNode expression;
  private Type targetType;

  @Override
  public CastNode copy() {
    CastNode copy = new CastNode();
    copy.sourceInfo = sourceInfo;
    copy.targetType = targetType;
    copy.expression = expression.copy();
    return copy;
  }

  public Type getTargetType() {
    return targetType;
  }

  public CastNode setTargetType(Type targetType) {
    this.targetType = targetType;
    return this;
  }

  public ExpressionNode getExpression() {
    return expression;
  }

  public CastNode setExpression(ExpressionNode expression) {
    this.expression = expression;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    return Collections.singletonList(expression);
  }

  @Override
  public ExpressionNode accept(Visitor visitor) {
    return visitor.visit(this);
  }

  @Override
  public Type getValueType() {
    return targetType;
  }

  @Override
  public ExpressionType getExpressionType() {
    return ExpressionType.CAST;
  }

}
