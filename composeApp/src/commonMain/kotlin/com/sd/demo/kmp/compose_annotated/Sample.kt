package com.sd.demo.kmp.compose_annotated

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.sd.lib.kmp.compose_annotated.annotatedWithRegex
import com.sd.lib.kmp.compose_annotated.annotatedWithTarget
import com.sd.lib.kmp.compose_annotated.annotatedWithTargets

@Composable
fun Sample(
  onClickBack: () -> Unit,
) {
  RouteScaffold(
    title = "Sample",
    onClickBack = onClickBack,
  ) {
    AnnotatedWithTarget()
    AnnotatedWithTargets()
    AnnotatedWithRegex()
  }
}

@Composable
private fun AnnotatedWithTarget(
  modifier: Modifier = Modifier,
  content: String = "1122334455-1122334455",
) {
  val annotated = content.annotatedWithTarget("2")
  Text(
    modifier = modifier,
    text = annotated,
  )
}

@Composable
private fun AnnotatedWithTargets(
  modifier: Modifier = Modifier,
  content: String = "1122334455-1122334455",
) {
  val targets = remember { listOf("2", "4") }
  val annotated = content.annotatedWithTargets(targets)
  Text(
    modifier = modifier,
    text = annotated,
  )
}

@Composable
private fun AnnotatedWithRegex(
  modifier: Modifier = Modifier,
  content: String = "1122334455-1122334455",
) {
  val regex = remember { "3".toRegex() }
  val annotated = content.annotatedWithRegex(regex)
  Text(
    modifier = modifier,
    text = annotated,
  )
}