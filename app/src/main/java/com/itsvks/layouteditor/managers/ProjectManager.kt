package com.itsvks.layouteditor.managers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.itsvks.layouteditor.LayoutEditor
import com.itsvks.layouteditor.ProjectFile
import com.itsvks.layouteditor.utils.Constants
import com.itsvks.layouteditor.utils.FileUtil
import java.lang.reflect.Type
import java.util.Locale
import java.util.Objects
import java.util.concurrent.CompletableFuture

class ProjectManager private constructor() {
    private lateinit var context: Context
    private val paletteList: MutableList<List<HashMap<String, Any>>> = ArrayList()
    var openedProject: ProjectFile? = null
        private set

    fun initManger(context: Context) {
        this.context = context
        CompletableFuture.runAsync { initPalette(context) }
    }

    fun openProject(project: ProjectFile?) {
        openedProject = project
        openedProject!!.drawables?.let { DrawableManager.loadFromFiles(it) }
        openedProject!!.layoutDesigns // just for the sake of creating folder
        openedProject!!.fonts?.let {
            FontManager.loadFromFiles(it)
        }
    }

    fun closeProject() {
        openedProject = null
        DrawableManager.clear()
        FontManager.clear()
    }

    val colorsXml: String
        get() = FileUtil.readFile(openedProject!!.colorsPath)
    val stringsXml: String
        get() = FileUtil.readFile(openedProject!!.stringsPath)
    val formattedProjectName: String
        get() {
            var projectName = openedProject!!.name.lowercase(Locale.getDefault()).trim { it <= ' ' }
            if (projectName.contains(" ")) {
                projectName = projectName.replace(" ".toRegex(), "_")
            }
            if (!projectName.endsWith(".xml")) {
                projectName = "$projectName.xml"
            }
            return projectName
        }

    fun getPalette(position: Int): List<HashMap<String, Any>> {
        return paletteList[position]
    }

    private fun initPalette(context: Context) {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<HashMap<String?, Any?>?>?>() {}.type
        paletteList.clear()
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_COMMON, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_TEXT, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_BUTTONS, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_WIDGETS, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_LAYOUTS, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_CONTAINERS, context))
        //paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_GOOGLE, context))
        paletteList.add(convertJsonToJavaObject(gson, type, Constants.PALETTE_LEGACY, context))
    }

    private fun convertJsonToJavaObject(
        gson: Gson, type: Type, filePath: String, context: Context
    ): ArrayList<HashMap<String, Any>> {
        return gson.fromJson(
            FileUtil.readFromAsset(filePath, context), type
        )
    }

    companion object {

        @JvmStatic
        @get:Synchronized
        val instance: ProjectManager by lazy { ProjectManager() }
    }
}
