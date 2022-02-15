package com.example.prmanager.utils

class Constants {

  companion object {
    const val API_ENDPOINT = "https://api.github.com"

    const val GITHUB_USER = "Siddharth2092"
    const val GITHUB_REPO = "pr-app"

    const val PR_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"

    const val PR_UI_DATE_FORMAT = "MMM dd, yyyy HH:mm:ss a"

    enum class PR_STATE(val value: String) {
      OPEN("open"),
      CLOSED("closed"),
      ALL("all")
    }
  }
}