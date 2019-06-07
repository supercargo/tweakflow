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

package com.twineworks.tweakflow.lang.parse.units;

import com.twineworks.tweakflow.lang.load.loadpath.LoadPathLocation;

public class MemoryParseUnit implements ParseUnit {

  private final String programText;
  private final String programLocation;
  private final LoadPathLocation location;

  public MemoryParseUnit(LoadPathLocation location, String programText, String path) {
    this.programText = programText;
    this.programLocation = path;
    this.location = location;
  }

  @Override
  public String getProgramText() {
    return programText;
  }

  public String getPath() {
    return programLocation;
  }


  @Override
  public ParseUnitType getParsingUnitType() {
    return ParseUnitType.MEMORY;
  }

  @Override
  public LoadPathLocation getLocation() {
    return location;
  }
}
