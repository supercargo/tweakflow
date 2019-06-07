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

import com.twineworks.tweakflow.lang.errors.LangError;
import com.twineworks.tweakflow.lang.errors.LangException;
import com.twineworks.tweakflow.lang.load.loadpath.LoadPathLocation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FilesystemParseUnit implements ParseUnit {

  private String programText;
  private LoadPathLocation location;
  private String path;

  public FilesystemParseUnit(LoadPathLocation location, String path) {
    this.location = location;
    this.path = path;
  }

  @Override
  synchronized public String getProgramText() {

    if (programText == null){

      try{
        String ret = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
        if (location.allowsCaching()){
          programText = ret;
        }
        return ret;
      } catch (IOException e) {
        throw LangException.wrap(e, LangError.IO_ERROR);
      }

    }

    return programText;

  }

  public String getPath() {
    return path;
  }

  @Override
  public ParseUnitType getParsingUnitType() {
    return ParseUnitType.FILE;
  }

  @Override
  public LoadPathLocation getLocation() {
    return location;
  }
}
