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

package com.twineworks.tweakflow.lang.parse;

import com.twineworks.tweakflow.lang.parse.units.ParseUnit;

import java.util.ArrayList;

public class SourceInfo {

  private ParseUnit parseUnit;
  private int line;
  private int charWithinLine;

  private int sourceIdxStart;
  private int sourceIdxEnd;
  private ArrayList<Integer> lineStarts;

  public SourceInfo(ParseUnit parseUnit, int line, int charWithinLine, int sourceIdxStart, int sourceIdxEnd) {
    this.parseUnit = parseUnit;
    this.line = line;
    this.charWithinLine = charWithinLine;
    this.sourceIdxStart = sourceIdxStart;
    this.sourceIdxEnd = sourceIdxEnd;
  }

  public ParseUnit getParseUnit() {
    return parseUnit;
  }

  public void setParseUnit(ParseUnit parseUnit) {
    this.parseUnit = parseUnit;
  }

  public int getLine() {
    return line;
  }

  public SourceInfo setLine(int line) {
    this.line = line;
    return this;
  }

  private ArrayList<Integer> getLineStartIndexes(){
    if (lineStarts == null){

      String str = parseUnit.getProgramText();

      lineStarts = new ArrayList<>();
      lineStarts.add(0);

      int idx = 0;
      int len = str.length();
      while(idx < len){
        int nextLine = str.indexOf('\n', idx)+1;
        // not found: nextLine == 0
        if (nextLine == 0) break;
        if (nextLine != len){
          lineStarts.add(nextLine);
        }
        idx = nextLine;
      }
    }

    return lineStarts;
  }

  public int getCharWithinLine() {
    return charWithinLine;
  }

  public SourceInfo setCharWithinLine(int charWithinLine) {
    this.charWithinLine = charWithinLine;
    return this;
  }

  public SourceInfo copy() {
    return new SourceInfo(parseUnit, line, charWithinLine, sourceIdxStart, sourceIdxEnd);
  }

  public int getSourceIdxStart() {
    return sourceIdxStart;
  }

  public SourceInfo setSourceIdxStart(int sourceIdxStart) {
    this.sourceIdxStart = sourceIdxStart;
    return this;
  }

  public int getSourceIdxEnd() {
    return sourceIdxEnd;
  }

  public SourceInfo setSourceIdxEnd(int sourceIdxEnd) {
    this.sourceIdxEnd = sourceIdxEnd;
    return this;
  }

  public String toString() {
    return getFullLocation();
  }

  public String getFullLocation() {
    return parseUnit.getPath() + ":" + line + ":" + charWithinLine;
  }

  public String getShortLocation() {
    return line + ":" + charWithinLine;
  }

  public String getLineLocationMarker() {
    String srcLine = getSourceCodeLine();
    if (srcLine == null) return null;
    int charIdx = charWithinLine - 1;

    if (charIdx >= 0 && charIdx <= srcLine.length()) {
      // construct a ptr by moving up to the index and replacing every char with a space, except for tabs
      StringBuilder marker = new StringBuilder();
      for (int i = 0; i < charIdx; i++) {
        if (srcLine.charAt(i) == '\t') {
          marker.append('\t');
        } else {
          marker.append(" ");
        }
      }
      marker.append("^");
      return marker.toString();
    } else {
      return null;
    }

  }

  public String getSourceCode() {
    String programText = parseUnit.getProgramText();
    if (programText == null) return null;
    if (sourceIdxStart < 0 || sourceIdxEnd < 0) return null;
    if (sourceIdxStart < programText.length() && sourceIdxEnd < programText.length()) {
      return programText.substring(sourceIdxStart, sourceIdxEnd + 1);
    } else {
      return null;
    }

  }

  public String getSourceCodeLine() {
    String programText = parseUnit.getProgramText();
    if (programText == null) return null;
    if (line <= 0) return null;
    ArrayList<Integer> lineStarts = getLineStartIndexes();
    if (line-1 >= lineStarts.size()) return null;
    int startIdx = lineStarts.get(line-1);
    int endIdx = line >= lineStarts.size() ? programText.length() : lineStarts.get(line);
    String line = programText.substring(startIdx, endIdx);
    // trim possible leading and trailing \r\n
    if (line.indexOf('\n') >= 0 || line.indexOf('\r') >= 0){
      line = line.replaceAll("^[\n\r]", "").replaceAll("[\n\r]$", "");
    }
    return line;
//    String[] lines = programText.split("\\r?\\n", -1);
//    if (lines.length > line - 1) {
//      return lines[line - 1];
//    } else {
//      return null;
//    }
  }

  public boolean precedes(SourceInfo other) {
    return parseUnit == other.parseUnit &&
        (getLine() < other.getLine() || getLine() == other.getLine() && getCharWithinLine() < other.getCharWithinLine());

  }

  public boolean encloses(int atLine, int atChar){
    if (line > atLine) return false;
    ArrayList<Integer> lineStarts = getLineStartIndexes();
    int lineIdx = atLine-1;
    if (lineIdx >= lineStarts.size()) return false;
    Integer startIdx = lineStarts.get(lineIdx);
    int pos = startIdx+(atChar-1);
    return (sourceIdxStart <= pos && sourceIdxEnd >= pos);
  }

}
