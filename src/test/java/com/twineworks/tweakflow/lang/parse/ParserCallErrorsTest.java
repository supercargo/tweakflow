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

import com.twineworks.tweakflow.lang.errors.LangError;
import com.twineworks.tweakflow.lang.errors.LangException;
import com.twineworks.tweakflow.lang.load.loadpath.ResourceLocation;
import com.twineworks.tweakflow.lang.parse.units.ResourceParseUnit;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParserCallErrorsTest {

  private ParseResult parseFailing(String fixturePath){
    Parser p = new Parser(
        new ResourceParseUnit(new ResourceLocation.Builder().build(), fixturePath)
    );
    ParseResult result = p.parseUnit();
    assertThat(result.isSuccess()).isFalse();
    return result;
  }

  @Test
  public void fails_on_missing_separator() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_missing_separator.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:13");
    assertThat(e.getMessage()).contains("expecting ','");
  }

  @Test
  public void fails_on_extra_initial_separator() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_extra_initial_separator.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:8");
    assertThat(e.getMessage()).contains("unexpected ','");
  }

  @Test
  public void fails_on_extra_mid_separator() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_extra_mid_separator.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:17");
    assertThat(e.getMessage()).contains("unexpected ','");
  }

  @Test
  public void fails_on_extra_final_separator() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_extra_final_separator.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:15");
    assertThat(e.getMessage()).contains("unexpected ','");
  }

  @Test
  public void fails_on_mix_binding_with_named() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_binding_with_named.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:13");
    assertThat(e.getMessage()).contains("unexpected named argument");
  }

  @Test
  public void fails_on_mix_binding_with_positional() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_binding_with_positional.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:13");
    assertThat(e.getMessage()).contains("unexpected positional argument");
  }

  @Test
  public void fails_on_mix_binding_with_splat() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_binding_with_splat.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:13");
    assertThat(e.getMessage()).contains("unexpected splat argument");
  }

  @Test
  public void fails_on_mix_named_with_binding() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_named_with_binding.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:14");
    assertThat(e.getMessage()).contains("unexpected partial application binding");
  }

  @Test
  public void fails_on_mix_positional_with_binding() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_positional_with_binding.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:11");
    assertThat(e.getMessage()).contains("unexpected partial application binding");
  }

  @Test
  public void fails_on_mix_splat_with_binding() throws Exception {
    ParseResult r = parseFailing("fixtures/tweakflow/analysis/parsing/errors/call_mix_splat_with_binding.tf");
    LangException e = r.getException();
    assertThat(e.getCode()).isEqualTo(LangError.PARSE_ERROR);
    assertThat(e.getSourceInfo().getShortLocation()).isEqualTo("3:15");
    assertThat(e.getMessage()).contains("unexpected partial application binding");
  }

}