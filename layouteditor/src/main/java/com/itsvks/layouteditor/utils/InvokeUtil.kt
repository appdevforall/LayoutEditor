package com.itsvks.layouteditor.utils

import android.content.Context
import android.view.View
import com.itsvks.layouteditor.R.mipmap
import com.itsvks.layouteditor.managers.ProjectManager
import org.json.JSONObject
import java.lang.reflect.InvocationTargetException

object InvokeUtil {
    @JvmStatic
    fun createView(className: String, context: Context): Any? {
        try {
            /*******
             * Emulate the drag action that would normally invoke this
             *******/
            var clazzName : String
//            val clazzName = "com.itsvks.layouteditor.editor.palette.layouts.${className}"
            if(!className.endsWith("Design")) {
                val classes =
                    JSONObject(FileUtil.readFromAsset("widgetclasses.json", context))
                clazzName = classes.getString(className).toString()
            } else {
                clazzName = className
            }
            val clazz = Class.forName(clazzName)
            val constructor = clazz.getConstructor(Context::class.java)
            return constructor.newInstance(context)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return e
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            return e
        } catch (e: InstantiationException) {
            e.printStackTrace()
            return e
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            return e
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            return e
        }
    }

    @JvmStatic
    fun invokeMethod(
        methodName: String, className: String, target: View, value: String, context: Context
    ) {
        try {
            val clazz = Class.forName("com.itsvks.layouteditor.editor.callers.$className")
            val method =
                clazz.getMethod(
                    methodName,
                    View::class.java,
                    String::class.java,
                    Context::class.java
                )
            method.invoke(clazz, target, value, context)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun getMipmapId(name: String): Int {
        try {
            val cls = mipmap::class.java
            val field = cls.getField(name)
            return field.getInt(cls)
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }

        return 0
    }

    @JvmStatic
    fun getSuperClassName(clazz: String): String? {
        return try {
            Class.forName(clazz).superclass.name
        } catch (e: ClassNotFoundException) {
            null
        }
    }
}
