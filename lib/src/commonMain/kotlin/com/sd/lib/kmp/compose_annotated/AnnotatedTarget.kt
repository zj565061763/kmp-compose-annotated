package com.sd.lib.kmp.compose_annotated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle

@Composable
fun CharSequence.annotatedWithTarget(
  target: String,
  ignoreCase: Boolean = false,
  targetStyle: SpanStyle = SpanStyle(Color.Red),
): AnnotatedString {
  return annotatedWithTarget(
    target = target,
    ignoreCase = ignoreCase,
    onTarget = { result ->
      addStyle(targetStyle, result.range.first, result.range.last + 1)
    },
  )
}

@Composable
fun CharSequence.annotatedWithTarget(
  target: String,
  ignoreCase: Boolean = false,
  onTarget: AnnotatedString.Builder.(MatchResult) -> Unit,
): AnnotatedString {
  val input = this
  val initialValue = remember(input) { AnnotatedString(input.toString()) }
  if (input.isEmpty()) return initialValue
  if (target.isEmpty()) return initialValue

  val regex = remember(target, ignoreCase) {
    if (ignoreCase) {
      target.toRegex(RegexOption.IGNORE_CASE)
    } else {
      target.toRegex()
    }
  }

  return annotatedWithRegex(
    regex = regex,
    onTarget = onTarget,
  )
}