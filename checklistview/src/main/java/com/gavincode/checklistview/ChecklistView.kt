package com.gavincode.checklistview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout

class ChecklistView: LinearLayout, OnChecklistItemEventListener {

    lateinit var parent: DragLinearLayout

    constructor(context: Context): super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet)
        : super(context, attrs) {
        init()
    }

    private fun init() {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.view_checklist, this,true)
        parent = view.findViewById(R.id.draggable_container)
        addItem(false, true)
    }

    private fun addItem(text: String, checked: Boolean,
                        draggable: Boolean, hasFocus: Boolean) {
        val newItem = ChecklistItem(context)
        newItem.editText.setText(text)
        newItem.checkbox.isChecked = checked
        newItem.setListener(this)
        newItem.checkbox.visibility = View.VISIBLE
        newItem.add.visibility = View.GONE
        when(draggable) {
            true -> { parent.addDragView(newItem, newItem.dragHandle) }
            else -> parent.addView(newItem)
        }
        if (hasFocus) newItem.requestFocus()
    }

    private fun addItem(dragable: Boolean, hasFocus: Boolean) {
        val newItem = ChecklistItem(context)

        newItem.setListener(this)
        when(dragable) {
            true -> { parent.addDragView(newItem, newItem.dragHandle) }
            false -> { parent.addView(newItem) }
        }
        if (hasFocus) newItem.requestFocus()
    }

    private fun addItem(index: Int) {
        val newItem = ChecklistItem(context)
        newItem.setListener(this)
        parent.addDragView(newItem, newItem.dragHandle, index)
        newItem.requestFocus()
    }

    override fun onChanged(event: ChecklistItemEvent) {
        when (event) {
            is ChecklistItemEvent.OnChecklistItemChecked -> { handleCheckListItemChecked(event.item, event.checked)}
            is ChecklistItemEvent.OnChecklistItemRemoved -> { handleCheckListItemRemoved(event.item) }
            is ChecklistItemEvent.OnEnterPressed -> { handleCheckListEnterPressed(event.item) }
            is ChecklistItemEvent.OnFocusChanged-> { handleFocusChanged(event.item, event.focused) }
            is ChecklistItemEvent.OnEdited -> { handleEdited(event.item) }
            is ChecklistItemEvent.OnChecklistItemRemovedByIME -> { handleRemovedByIME(event.item) }
        }
    }

    private fun handleRemovedByIME(item: ChecklistItem) {
        val childIndex = parent.indexOfChild(item)
        if (childIndex != 0 && item.isEmpty()) {
            parent.removeDragView(item)
            val previousView = parent.getChildAt(childIndex - 1)
            previousView.requestFocus()
        }
    }

    private fun handleEdited(item: ChecklistItem) {
    }

    private fun handleCheckListItemChecked(item: ChecklistItem, checked: Boolean) {

    }

    private fun handleCheckListEnterPressed(item: ChecklistItem) {
        parent.setViewDraggable(item, item.dragHandle)
        val childIndex = parent.indexOfChild(item)
        if (childIndex != (parent.childCount - 1)) {
            addItem(childIndex + 1)
        } else
            addItem(false, true)
    }

    private fun handleCheckListItemRemoved(item: ChecklistItem) {
        if (parent.childCount > 1) {
            val index = parent.indexOfChild(item)
            parent.removeDragView(item)
            if (index == parent.childCount) {
                parent.getChildAt(index - 1).requestFocus()
            } else {
                parent.getChildAt(index).requestFocus()
            }
        } else {
            item.editText.text.clear()
        }
    }

    private fun handleFocusChanged(item: ChecklistItem, focused: Boolean) {
//        if (!item.isEmpty()) {
//            item.checkbox.visibility = View.VISIBLE
//            item.add.visibility = View.GONE
//        }
        if (focused) {
            item.checkbox.visibility = View.VISIBLE
            item.add.visibility = View.GONE
            item.deleteView.visibility = View.VISIBLE
            item.dragHandle.visibility = View.GONE
        } else {
            item.deleteView.visibility = View.GONE
            item.dragHandle.visibility = View.VISIBLE
            parent.setViewDraggable(item, item.dragHandle)
        }
    }

    fun getChecklistData(): List<ChecklistData> {
        val list = mutableListOf<ChecklistData>()

        for (index in 0..(parent.childCount - 1)) {
            val child = parent.getChildAt(index)
            if (child is ChecklistItem) {
                val checklistItem = child as ChecklistItem
                if (!checklistItem.isEmpty()) {
                    list.add(ChecklistData(checklistItem.editText.text.toString(),
                            checklistItem.isChecked()
                            ))
                }
            }
        }
        return list
    }

    fun setChecklistData(list: List<ChecklistData>) {
        clear()
        for (data in list) {
            addItem(data.text, data.checked, true, false)
        }

    }

    fun clear() {
        parent.removeAllViews()
    }
}