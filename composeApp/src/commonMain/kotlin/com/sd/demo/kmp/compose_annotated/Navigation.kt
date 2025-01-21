package com.sd.demo.kmp.compose_annotated

import kotlinx.serialization.Serializable

sealed interface AppRoute {
  @Serializable
  data object Home : AppRoute

  @Serializable
  data object Sample : AppRoute
}