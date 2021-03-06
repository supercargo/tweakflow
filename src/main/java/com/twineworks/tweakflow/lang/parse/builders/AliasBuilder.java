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

package com.twineworks.tweakflow.lang.parse.builders;

import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.ast.aliases.AliasNode;
import com.twineworks.tweakflow.lang.ast.expressions.ReferenceNode;
import com.twineworks.tweakflow.grammar.TweakFlowParser;
import com.twineworks.tweakflow.grammar.TweakFlowParserBaseVisitor;
import com.twineworks.tweakflow.lang.errors.LangException;
import com.twineworks.tweakflow.lang.parse.units.ParseUnit;

import java.util.List;

import static com.twineworks.tweakflow.lang.parse.util.CodeParseHelper.identifier;
import static com.twineworks.tweakflow.lang.parse.util.CodeParseHelper.srcOf;

public class AliasBuilder extends TweakFlowParserBaseVisitor<Node> {

  private final ParseUnit parseUnit;
  private final boolean recovery;
  private final List<LangException> recoveryErrors;

  public AliasBuilder(ParseUnit parseUnit, boolean recovery, List<LangException> recoveryErrors) {
    this.parseUnit = parseUnit;
    this.recovery = recovery;
    this.recoveryErrors = recoveryErrors;
  }

  @Override
  public AliasNode visitAliasDef(TweakFlowParser.AliasDefContext ctx) {

    ExpressionBuilder b = new ExpressionBuilder(parseUnit, recovery, recoveryErrors);
    ReferenceNode source = (ReferenceNode) b.visit(ctx.reference());

    String name = identifier(ctx.aliasName().getText());

    return new AliasNode()
        .setSourceInfo(srcOf(parseUnit, ctx))
        .setSymbolName(name)
        .setSource(source);

  }

}
