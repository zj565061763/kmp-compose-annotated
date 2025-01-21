package com.sd.lib.kmp.compose_annotated

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CharSequence.annotatedWithRegex(
  regex: Regex,
  targetStyle: SpanStyle = SpanStyle(Color.Red),
): AnnotatedString {
  return annotatedWithRegex(
    regex = regex,
    onTarget = { result ->
      addStyle(targetStyle, result.range.first, result.range.last + 1)
    },
  )
}

@Composable
fun CharSequence.annotatedWithRegex(
  regex: Regex,
  onTarget: AnnotatedString.Builder.(MatchResult) -> Unit,
): AnnotatedString {
  val input = this
  val initialValue = remember(input) { AnnotatedString(input.toString()) }
  if (input.isEmpty()) return initialValue

  if (LocalInspectionMode.current) {
    return charSequenceToAnnotated(
      input = input,
      regex = regex,
      onTarget = onTarget,
    )
  }

  val onTargetUpdated by rememberUpdatedState(onTarget)
  return produceState(initialValue = initialValue, input, regex) {
    value = withContext(Dispatchers.Default) {
      charSequenceToAnnotated(
        input = input,
        regex = regex,
        onTarget = { onTargetUpdated(it) },
      )
    }
  }.value
}

private fun charSequenceToAnnotated(
  input: CharSequence,
  regex: Regex,
  onTarget: AnnotatedString.Builder.(MatchResult) -> Unit,
): AnnotatedString {
  return buildAnnotatedString {
    append(input)
    regex.findAll(input).forEach { onTarget(it) }
  }
}