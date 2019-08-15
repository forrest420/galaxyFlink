/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.galaxy.flink.streaming.scala.examples

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import com.galaxy.flink.java.WordCountData
import org.apache.flink.streaming.api.windowing.time.Time

/**
 * Implements a windowed version of the streaming "WordCount" program.
 *
 * The input is a plain text file with lines separated by newline characters.
 *
 * Usage:
 * {{{
 * WordCount
 * --input <path>
 * --output <path>
 * --window <n>
 * --slide <n>
 * }}}
 *
 * If no parameters are provided, the program is run with default data from
 * [[com.galaxy.flink.java.WordCountData]].
 *
 * This example shows how to:
 *
 *  - write a simple Flink Streaming program,
 *  - use tuple data types,
 *  - use basic windowing abstractions.
 *
 */
object WindowWordCount {

  def main(args: Array[String]): Unit = {
    println(WordCountData.WORDS.length)
    val params = ParameterTool.fromArgs(args)

    // set up the execution environment
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    // get input data
    val text =
    if (params.has("input")) {
      // read the text file from given input path
      env.readTextFile(params.get("input"))
    } else {
      println("Executing WindowWordCount example with default input data set.")
      println("Use --input to specify file input.")
      // get default test text data
      env.fromElements(WordCountData.WORDS: _*)
    }

    // make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)

    val windowSize = params.getInt("window", 10)
    val slideSize = params.getInt("slide", 5)

    val counts: DataStream[(String, Int)] = text
      // split up the lines in pairs (2-tuple) containing: (word,1)
      // \W与任何非单词字符匹配。与"[^A-Za-z0-9_]"等效。
      .flatMap(_.toLowerCase.split("\\W+"))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0)
      // create windows of windowSize records slided every slideSize records
      .countWindow(windowSize,slideSize)
     // .timeWindow(Time.seconds(1))
      // group by the tuple field "0" and sum up tuple field "1"
      .sum(1)

    //--output /Users/baozhiwang/local_dir/data/output

    // emit result
    if (params.has("output")) {
      counts.writeAsText(params.get("output"))
      counts.print()
    } else {
      println("Printing result to stdout. Use --output to specify output path.")
      counts.print()
    }

    // execute program
    env.execute("WindowWordCount")
  }

}
