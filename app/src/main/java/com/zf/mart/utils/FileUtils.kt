package com.zf.mart.utils

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore


object FileUtils {

    fun pathToUri(context: Activity, picPath: String): Uri {
        /**
         *   path 转uri
         *   从这个格式       /storage/emulated/0/Android/data/com.takephoto_android70/1533109354195.jpg
         *   转换为这个格式（输出结果）   content://com.android.providers.media.documents/document/image%3A624
         *   val path = Environment.getExternalStorageDirectory().absolutePath + File.separator + "Android" + File.separator + "data" + File.separator + "com.takephoto_android70" + File.separator + System.currentTimeMillis() + ".png"
         *
         */
        val mUri = Uri.parse("content://media/external/images/media")
        var mImageUri: Uri? = null
        val cursor = context.managedQuery(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER)
        cursor.moveToFirst()

        while (!cursor.isAfterLast) {
            val data = cursor.getString(cursor
                    .getColumnIndex(MediaStore.MediaColumns.DATA))
            if (picPath == data) {
                val ringtoneID = cursor.getInt(cursor
                        .getColumnIndex(MediaStore.MediaColumns._ID))
                mImageUri = Uri.withAppendedPath(mUri, "" + ringtoneID)
                return mImageUri
            }
            cursor.moveToNext()
        }
        return mImageUri!!
    }
}