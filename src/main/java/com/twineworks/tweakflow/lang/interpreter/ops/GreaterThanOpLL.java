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

package com.twineworks.tweakflow.lang.interpreter.ops;

import com.twineworks.tweakflow.lang.ast.expressions.GreaterThanNode;
import com.twineworks.tweakflow.lang.interpreter.EvaluationContext;
import com.twineworks.tweakflow.lang.interpreter.Stack;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.values.Values;

final public class GreaterThanOpLL implements ExpressionOp {

  private final GreaterThanNode node;
  private final ExpressionOp leftOp;
  private final ExpressionOp rightOp;

  public GreaterThanOpLL(GreaterThanNode node) {
    this.node = node;
    leftOp = node.getLeftExpression().getOp();
    rightOp = node.getRightExpression().getOp();
  }

  @Override
  public Value eval(Stack stack, EvaluationContext context) {

    Long left = leftOp.eval(stack, context).longNum();
    if (left == null) return Values.FALSE;

    Long right = rightOp.eval(stack, context).longNum();
    if (right == null) return Values.FALSE;

    return (left > right) ? Values.TRUE : Values.FALSE;

  }

  @Override
  public boolean isConstant() {
    return false;
  }

  @Override
  public ExpressionOp specialize() {
    return new GreaterThanOpLL(node);
  }

  @Override
  public ExpressionOp refresh() {
    return new GreaterThanOpLL(node);
  }

}
