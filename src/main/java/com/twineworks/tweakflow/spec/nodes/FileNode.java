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

import com.twineworks.tweakflow.lang.analysis.AnalysisResult;
import com.twineworks.tweakflow.lang.values.Value;
import com.twineworks.tweakflow.spec.runner.SpecContext;

import java.util.List;

public class FileNode implements SpecNode {

  private String name = "file";
  private List<SpecNode> nodes;
  private long startedMillis;
  private long endedMillis;

  private boolean success = true;
  private boolean didRun = false;

  private Throwable cause;
  private String errorMessage = "OK";
  private Value source;
  private AnalysisResult analysisResult;

  public FileNode setNodes(List<SpecNode> nodes) {
    this.nodes = nodes;
    return this;
  }


  @Override
  public Value getSource() {
    return source;
  }

  public FileNode setSource(Value source) {
    this.source = source;
    return this;
  }

  @Override
  public SpecNodeType getType() {
    return SpecNodeType.FILE;
  }

  public String getName() {
    return name;
  }

  public FileNode setName(String name) {
    this.name = name;
    return this;
  }

  @Override
  public void run(SpecContext context) {
    didRun = true;
    startedMillis = System.currentTimeMillis();
    context.onEnterFile(this);
    for (SpecNode node : nodes) {
      context.run(node);
      if (!node.isSuccess()) {
        success = false;
      }
    }
    endedMillis = System.currentTimeMillis();
    context.onLeaveFile(this);
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

  @Override
  public boolean isSuccess() {
    return success;
  }

  @Override
  public String getErrorMessage() {
    return errorMessage;
  }

  @Override
  public Throwable getCause() {
    return cause;
  }

  @Override
  public long getDurationMillis() {
    return endedMillis - startedMillis;
  }

  public AnalysisResult getAnalysisResult() {
    return analysisResult;
  }

  public FileNode setAnalysisResult(AnalysisResult analysisResult) {
    this.analysisResult = analysisResult;
    return this;
  }
}
