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

package com.twineworks.tweakflow.lang.interpreter;

import com.twineworks.tweakflow.lang.analysis.AnalysisResult;
import com.twineworks.tweakflow.lang.analysis.AnalysisSet;
import com.twineworks.tweakflow.lang.interpreter.memory.GlobalMemorySpace;
import com.twineworks.tweakflow.lang.interpreter.memory.MemorySpaceBuilder;

public class RuntimeSet {

  private final AnalysisSet analysisSet;
  private final AnalysisResult analysisResult;

  private final GlobalMemorySpace globalMemorySpace;

  public RuntimeSet(AnalysisResult analysisResult) {
    if (!analysisResult.isSuccess()) throw new AssertionError("RuntimeSet can only be instantiated with a successful AnalysisResult");
    this.analysisResult = analysisResult;
    this.analysisSet = analysisResult.getAnalysisSet();
    this.globalMemorySpace = new GlobalMemorySpace(analysisSet.getGlobalScope());
    MemorySpaceBuilder.buildRuntimeSpace(this);
  }

  public AnalysisSet getAnalysisSet() {
    return analysisSet;
  }

  public AnalysisResult getAnalysisResult() {
    return analysisResult;
  }

  public GlobalMemorySpace getGlobalMemorySpace() {
    return globalMemorySpace;
  }

  public RuntimeSet copy() {
    return new RuntimeSet(analysisResult);
  }
}
