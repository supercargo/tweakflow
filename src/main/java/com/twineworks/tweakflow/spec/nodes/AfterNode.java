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

package com.twineworks.tweakflow.spec.nodes;

import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.spec.runner.SpecContext;

public class AfterNode implements SpecNode {

  private Value source;

  private String name = "after";
  private EffectNode effectNode;

  private long startedMillis;
  private long endedMillis;

  private boolean success = true;
  private String errorMessage;
  private Throwable cause;

  private NodeLocation at;
  private boolean didRun = false;

  public AfterNode setAt(NodeLocation at) {
    this.at = at;
    return this;
  }

  public AfterNode setEffect(EffectNode effectNode) {
    this.effectNode = effectNode;
    return this;
  }

  @Override
  public SpecNodeType getType() {
    return SpecNodeType.AFTER;
  }

  @Override
  public Value getSource() {
    return source;
  }

  public AfterNode setSource(Value source) {
    this.source = source;
    return this;
  }

  public String getName() {
    return name;
  }

  public AfterNode setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public void run(SpecContext context) {
    startedMillis = System.currentTimeMillis();
    context.onEnterAfter(this);
    boolean didCrash = false;
    if (success) {
      didRun = true;
      try {
        effectNode.execute(context);
      } catch (Throwable e) {
        fail(e.getMessage(), e);
        didCrash = true;
      }
    }
    endedMillis = System.currentTimeMillis();
    context.onLeaveAfter(this);
    if (didCrash){
      throw new RuntimeException("Failed to run after hook. Failed running " + source + " with error: " + errorMessage, cause);
    }

  }

  @Override
  public void fail(String errorMessage, Throwable cause) {
    success = false;
    this.errorMessage = errorMessage;
    this.cause = cause;
  }

  @Override
  public boolean didRun() {
    return didRun;
  }

  public boolean isSuccess() {
    return success;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public Throwable getCause() {
    return cause;
  }

  public NodeLocation at() {
    return at;
  }

  @Override
  public long getDurationMillis() {
    return endedMillis - startedMillis;
  }


}
