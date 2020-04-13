package com.lv.wanandroid.app

import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants


class WanApplication() : TinkerApplication(
    ShareConstants.TINKER_ENABLE_ALL,
    "com.lv.wanandroid.app.SampleApplicationLike",
    "com.tencent.tinker.loader.TinkerLoader",
    false
)