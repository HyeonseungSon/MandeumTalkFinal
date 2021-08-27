package com.rud.mandeumtalk.board

data class BoardModel(val title: String = "",
                      val contents: String = "",
                      val writer: String = "",
                      val dateTime : String = "",
                      val writerUid : String = "",
                      val key : String = "")