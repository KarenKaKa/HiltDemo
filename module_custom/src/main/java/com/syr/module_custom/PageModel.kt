package com.syr.module_custom

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes

/**
 * Created by songyaru on 2020/9/27.
 */
class PageModel constructor(
    @LayoutRes var sampleLayoutRes: Int = 0,
    @StringRes var titleRes: Int = 0,
    @LayoutRes var practiceLayoutRes: Int = 0
) {
}