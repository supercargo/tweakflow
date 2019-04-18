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

import com.twineworks.tweakflow.lang.ast.expressions.CastNode;
import com.twineworks.tweakflow.lang.types.Type;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.interpreter.EvaluationContext;
import com.twineworks.tweakflow.lang.interpreter.Stack;

final public class CastOp implements ExpressionOp {

  private final Type targetType;
  private final ExpressionOp exp;
  private final CastNode node;

  public CastOp(CastNode node) {
    this.node = node;
    this.targetType = node.getTargetType();
    this.exp = node.getExpression().getOp();
  }

  @Override
  public Value eval(Stack stack, EvaluationContext context) {
    return exp.eval(stack, context).castTo(targetType);
  }

  @Override
  public boolean isConstant() {
    return exp.isConstant();
  }

  @Override
  public ExpressionOp specialize() {
    if (node.getExpression().getValueType() == targetType){
      return exp;
    }
    else{
      return new CastOp(node);
    }
  }

  @Override
  public ExpressionOp refresh() {
    return new CastOp(node);
  }


}
