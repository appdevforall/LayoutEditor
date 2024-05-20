package com.itsvks.layouteditor.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.itsvks.layouteditor.R
import com.itsvks.layouteditor.adapters.PaletteListAdapter
import com.itsvks.layouteditor.databinding.FragmentEditorBottomSheetBinding
import com.itsvks.layouteditor.editor.DesignEditor
import com.itsvks.layouteditor.managers.ProjectManager
import com.itsvks.layouteditor.utils.Constants

const val ARG_EDITOR = "editor"

class EditorBottomSheetFragment : BottomSheetDialogFragment() {

  private var _binding: FragmentEditorBottomSheetBinding? = null
  private val binding get() = requireNotNull(_binding) { "Fragment has been destroyed." }

  private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>
  private lateinit var editor: DesignEditor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      editor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        it.getSerializable(ARG_EDITOR, DesignEditor::class.java)!!
      } else {
        it.getSerializable(ARG_EDITOR) as DesignEditor
      }
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentEditorBottomSheetBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    BottomSheetBehavior.from(dialog!!.findViewById(com.google.android.material.R.id.design_bottom_sheet))
      .also { bottomSheetBehavior = it }

    bottomSheetBehavior.apply {
      peekHeight = 650
      saveFlags = BottomSheetBehavior.SAVE_ALL
      isDraggable = true
      isFitToContents = false
      addBottomSheetCallback(object : BottomSheetCallback() {
        override fun onStateChanged(view: View, state: Int) {
          if (state == BottomSheetBehavior.STATE_HIDDEN) dismiss()
        }

        override fun onSlide(view: View, v: Float) {}
      })
    }

    addTab(Constants.TAB_TITLE_COMMON, R.drawable.android)
    addTab(Constants.TAB_TITLE_TEXT, R.mipmap.ic_palette_text_view)
    addTab(Constants.TAB_TITLE_BUTTONS, R.mipmap.ic_palette_button)
    addTab(Constants.TAB_TITLE_WIDGETS, R.mipmap.ic_palette_view)
    addTab(Constants.TAB_TITLE_LAYOUTS, R.mipmap.ic_palette_relative_layout)
    addTab(Constants.TAB_TITLE_CONTAINERS, R.mipmap.ic_palette_view_pager)
    addTab(Constants.TAB_TITLE_GOOGLE, R.drawable.google)
    addTab(Constants.TAB_TITLE_LEGACY, R.mipmap.ic_palette_grid_layout)

    val adapter = PaletteListAdapter(bottomSheetBehavior)
    adapter.submitPaletteList(ProjectManager.instance.getPalette(0))

    binding.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
      override fun onTabSelected(tab: TabLayout.Tab) {
        adapter.submitPaletteList(ProjectManager.instance.getPalette(tab.position))
      }

      override fun onTabUnselected(tab: TabLayout.Tab) {}
      override fun onTabReselected(tab: TabLayout.Tab) {}
    })

    binding.list.apply {
      layoutManager = GridLayoutManager(context, 2)
      this.adapter = adapter
    }
  }

  private fun addTab(title: String, icon: Int) {
    binding.tabLayout.apply {
      addTab(newTab().setText(title).setIcon(icon))
    }
  }

  companion object {
    fun newInstance(editor: DesignEditor): EditorBottomSheetFragment =
      EditorBottomSheetFragment().apply {
        arguments = Bundle().apply {
          putSerializable(ARG_EDITOR, editor)
        }
      }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}