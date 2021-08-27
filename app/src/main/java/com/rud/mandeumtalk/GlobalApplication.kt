package com.rud.mandeumtalk

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class GlobalApplication : Application() {
	override fun onCreate() {
		super.onCreate()

		KakaoSdk.init(this, "b93470768a9eeb39d55aeda99225c0cf")
	}
}