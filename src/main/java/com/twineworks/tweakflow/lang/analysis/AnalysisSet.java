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

package com.twineworks.tweakflow.lang.analysis;

import com.twineworks.tweakflow.lang.errors.LangException;
import com.twineworks.tweakflow.lang.load.loadpath.LoadPath;
import com.twineworks.tweakflow.lang.scope.GlobalScope;
import com.twineworks.tweakflow.lang.scope.Symbol;

import java.util.*;

public class AnalysisSet {

  private final GlobalScope globalScope = new GlobalScope();
  private final Map<String, AnalysisUnit> units = new HashMap<>();

  private IdentityHashMap<Symbol, LinkedHashSet<Symbol>> dependencies;
  private IdentityHashMap<Symbol, LinkedHashSet<Symbol>> dependants;

  private final ArrayList<LangException> recoveryErrors = new ArrayList<>();

  private final LoadPath loadPath;

  public LoadPath getLoadPath() {
    return loadPath;
  }

  public AnalysisSet(LoadPath loadPath) {
    this.loadPath = loadPath;
  }

  public Map<String, AnalysisUnit> getUnits() {
    return units;
  }

  public GlobalScope getGlobalScope() {
    return globalScope;
  }

  public IdentityHashMap<Symbol, LinkedHashSet<Symbol>> getDependencies() {
    return dependencies;
  }

  public AnalysisSet setDependencies(IdentityHashMap<Symbol, LinkedHashSet<Symbol>> dependencies) {
    this.dependencies = dependencies;
    return this;
  }

  public IdentityHashMap<Symbol, LinkedHashSet<Symbol>> getDependants() {
    return dependants;
  }

  public AnalysisSet setDependants(IdentityHashMap<Symbol, LinkedHashSet<Symbol>> dependants) {
    this.dependants = dependants;
    return this;
  }

  public ArrayList<LangException> getRecoveryErrors() {
    return recoveryErrors;
  }


}
