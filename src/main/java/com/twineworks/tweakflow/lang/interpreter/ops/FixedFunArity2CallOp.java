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

import com.twineworks.tweakflow.lang.interpreter.Interpreter;
import com.twineworks.tweakflow.lang.ast.args.ArgumentNode;
import com.twineworks.tweakflow.lang.ast.expressions.CallNode;
import com.twineworks.tweakflow.lang.values.Arity2CallSite;
import com.twineworks.tweakflow.lang.values.Arity3CallSite;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.lang.interpreter.EvaluationContext;
import com.twineworks.tweakflow.lang.interpreter.CallContext;
import com.twineworks.tweakflow.lang.interpreter.Stack;
import com.twineworks.tweakflow.lang.interpreter.calls.CallSites;

import java.util.List;

final public class FixedFunArity2CallOp implements ExpressionOp {

  private final CallNode node;
  private final Value f;
  private final ExpressionOp arg0Op;
  private final ExpressionOp arg1Op;
  private final ThreadLocal<Arity2CallSite> tlcs;

  public FixedFunArity2CallOp(CallNode node) {
    this.node = node;
    this.f = Interpreter.evaluateInEmptyScope(node.getExpression());
    this.tlcs = new ThreadLocal<>();
    List<ArgumentNode> argsList = node.getArguments().getList();
    this.arg0Op = argsList.get(0).getExpression().getOp();
    this.arg1Op = argsList.get(1).getExpression().getOp();
  }

  @Override
  public Value eval(Stack stack, EvaluationContext context) {

    Arity2CallSite cs = tlcs.get();
    if (cs == null){
      cs = CallSites.createArity2CallSite(f, node, stack, context, new CallContext(stack, context));
      tlcs.set(cs);
    }
    return cs.call(arg0Op.eval(stack, context), arg1Op.eval(stack, context));
  }

  @Override
  public boolean isConstant() {
    return false;
  }

  @Override
  public ExpressionOp specialize() {
    return new FixedFunArity2CallOp(node);
  }

  @Override
  public ExpressionOp refresh() {
    return new FixedFunArity2CallOp(node);
  }


}
