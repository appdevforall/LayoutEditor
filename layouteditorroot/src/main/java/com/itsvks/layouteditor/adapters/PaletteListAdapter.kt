package com.itsvks.layouteditor.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.itsvks.layouteditor.R
import com.itsvks.layouteditor.databinding.LayoutPaletteItemBinding
import com.itsvks.layouteditor.utils.InvokeUtil.getMipmapId
import com.itsvks.layouteditor.utils.InvokeUtil.getSuperClassName

class PaletteListAdapter private constructor(
  private val drawerLayout: DrawerLayout?,
  private val behavior: BottomSheetBehavior<*>?
) : RecyclerView.Adapter<PaletteListAdapter.ViewHolder>() {

  constructor(drawerLayout: DrawerLayout) : this(drawerLayout, null)
  constructor(behavior: BottomSheetBehavior<*>) : this(null, behavior)

  private lateinit var tab: List<HashMap<String, Any>>

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutPaletteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val widgetItem = tab[position]

    val binding = holder.binding

    binding.icon.setImageResource(getMipmapId(widgetItem["iconName"].toString()))
    binding.name.text = widgetItem["name"].toString()
    binding.className.text = getSuperClassName(widgetItem["className"].toString())

    binding.root.setOnLongClickListener {
      if (it.startDragAndDrop(null, View.DragShadowBuilder(it), widgetItem, 0)) {
        drawerLayout?.closeDrawers()
        behavior?.state = BottomSheetBehavior.STATE_HIDDEN
      }
      true
    }

    binding.root.animation = AnimationUtils.loadAnimation(
      holder.itemView.context, R.anim.project_list_animation
    )
  }

  override fun getItemCount(): Int {
    return tab.size
  }

  fun submitPaletteList(tab: List<HashMap<String, Any>>) {
    this.tab = tab
    notifyDataSetChanged()
  }

  class ViewHolder(var binding: LayoutPaletteItemBinding) : RecyclerView.ViewHolder(
    binding.root
  )
}
