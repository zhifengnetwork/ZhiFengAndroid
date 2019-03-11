package com.zf.mart.api

import androidx.annotation.IntDef

@IntDef(ItemType.BIG_SORT, ItemType.SMALL_SORT)
annotation class ItemType {
    companion object {
        const val BIG_SORT = 0
        const val SMALL_SORT = 1
    }
}
