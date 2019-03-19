package com.zf.mart.net


import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.zf.mart.utils.LogUtils
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

class JsonLoginFailConverterFactorykt constructor(private val gson: Gson) : Converter.Factory() {

    init {
        if (gson == null) {
            throw NullPointerException("gson == null")
        }
    }

    companion object {

        @JvmOverloads
        fun create(gson: Gson = Gson()): JsonLoginFailConverterFactorykt {
            return JsonLoginFailConverterFactorykt(gson)
        }
    }

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val adapter = gson.getAdapter(TypeToken.get(type)) as TypeAdapter<*>
        return JsonLoginFailBodyConverterKt(gson, adapter)
    }

    inner class JsonLoginFailBodyConverterKt<T> constructor(val gSon: Gson, adapter: TypeAdapter<T>) :
        Converter<ResponseBody, T> {
        private val adapter: TypeAdapter<T> = adapter

        @Throws(IOException::class)
        override fun convert(responseBody: ResponseBody): T {
            val response = responseBody.string()

            LogUtils.e(">>>>>>::解析：" + response)

//            val gson = Gson()
//            val bean = gson.fromJson(response, BaseBean::class.java)
//            if (bean.code == 12) {
            //有其他设备登录，强制登出

            /** 在主线程弹出提示 */
//                val disposable = Observable.create<String> {
//                    it.onNext("")
//                }.compose(SchedulerUtils.ioToMain())
//                        .subscribe {
////                            ToastUtils.showToast(DeliveryApplication.context.resources.getString(R.string.login_another))
//                        }

//                Preference.clearPreference(UriConstant.TOKEN)
//                Preference.clearPreference(UriConstant.USERINFO)
//                val intent = Intent(DeliveryApplication.context, LoginActivity::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                DeliveryApplication.context.startActivity(intent)

//            }
            return adapter.fromJson(response)
        }

    }


}