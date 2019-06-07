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

package com.twineworks.tweakflow.lang.ast.structure.match;

import com.twineworks.tweakflow.lang.analysis.visitors.Visitor;
import com.twineworks.tweakflow.lang.ast.Node;
import com.twineworks.tweakflow.lang.parse.SourceInfo;
import com.twineworks.tweakflow.lang.scope.Scope;
import com.twineworks.tweakflow.lang.interpreter.ops.PatternOp;

import java.util.ArrayList;
import java.util.List;

public class MidListPatternNode implements Node, MatchPatternNode {

  private ArrayList<MatchPatternNode> headElements = new ArrayList<>();
  private ArrayList<MatchPatternNode> lastElements = new ArrayList<>();
  private CapturePatternNode midCapture;
  private CapturePatternNode capture;
  private PatternOp patternOp;
  private SourceInfo sourceInfo;
  private Scope scope;


  @Override
  public MidListPatternNode copy() {

    MidListPatternNode copy = new MidListPatternNode();

    copy.sourceInfo = sourceInfo;
    copy.capture = capture == null ? null : capture.copy();
    copy.midCapture = midCapture.copy();

    for (MatchPatternNode headElement : headElements) {
      copy.headElements.add((MatchPatternNode) headElement.copy());
    }

    for (MatchPatternNode lastElement : lastElements) {
      copy.lastElements.add((MatchPatternNode) lastElement.copy());
    }

    return copy;
  }

  @Override
  public SourceInfo getSourceInfo() {
    return sourceInfo;
  }

  @Override
  public MidListPatternNode setSourceInfo(SourceInfo sourceInfo) {
    this.sourceInfo = sourceInfo;
    return this;
  }

  @Override
  public List<? extends Node> getChildren() {
    ArrayList<Node> ret = new ArrayList<>();
    ret.addAll(headElements);
    ret.add(midCapture);
    ret.addAll(lastElements);
    if (capture != null){
      ret.add(capture);
    }
    return ret;
  }

  @Override
  public Scope getScope() {
    return scope;
  }

  @Override
  public MidListPatternNode setScope(Scope scope) {
    this.scope = scope;
    return this;
  }

  @Override
  public MidListPatternNode accept(Visitor visitor) {
    return visitor.visit(this);
  }

  @Override
  public PatternOp getPatternOp() {
    return patternOp;
  }

  public MidListPatternNode setPatternOp(PatternOp patternOp) {
    this.patternOp = patternOp;
    return this;
  }

  public ArrayList<MatchPatternNode> getHeadElements() {
    return headElements;
  }

  public ArrayList<MatchPatternNode> getLastElements() {
    return lastElements;
  }

  public CapturePatternNode getMidCapture() {
    return midCapture;
  }

  public MidListPatternNode setMidCapture(CapturePatternNode midCapture) {
    this.midCapture = midCapture;
    return this;
  }

  public CapturePatternNode getCapture() {
    return capture;
  }

  public MidListPatternNode setCapture(CapturePatternNode capture) {
    this.capture = capture;
    return this;
  }
}
