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

package com.twineworks.tweakflow.lang.analysis.scope;

import com.twineworks.tweakflow.lang.analysis.AnalysisStage;
import com.twineworks.tweakflow.lang.ast.UnitNode;
import com.twineworks.tweakflow.lang.analysis.AnalysisUnit;
import com.twineworks.tweakflow.lang.analysis.AnalysisSet;

public class ScopeBuilder {

  public static void buildScope(AnalysisSet analysisSet, boolean recovery) {

    for (AnalysisUnit unit : analysisSet.getUnits().values()) {

      // already scoped?
      if (unit.getStage().getProgress() >= AnalysisStage.BUILT_SCOPE.getProgress()){
        continue;
      }

      buildScope(analysisSet, unit.getUnit(), recovery);

      unit.setStage(AnalysisStage.BUILT_SCOPE);
    }

  }

  static void buildScope(AnalysisSet analysisSet, UnitNode unitNode, boolean recovery){
    new ScopeBuilderVisitor(analysisSet.getGlobalScope(), recovery, analysisSet.getRecoveryErrors()).visit(unitNode);
  }

}
