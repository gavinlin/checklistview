package com.gavincode.checklistview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
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

    fun addItem(dragable: Boolean, hasFocus: Boolean) {
        val newItem = ChecklistItem(context)

        newItem.setListener(this)
        when(dragable) {
            true -> { parent.addDragView(newItem, newItem.dragHandle) }
            false -> { parent.addView(newItem) }
        }
        if (hasFocus) newItem.requestFocus()
    }

    override fun onChanged(event: ChecklistItemEvent) {
        when (event) {
            is ChecklistItemEvent.OnChecklistItemChecked -> { handleCheckListItemChecked(event.item, event.checked)}
            is ChecklistItemEvent.OnChecklistItemRemoved -> { handleCheckListItemRemoved(event.item) }
            is ChecklistItemEvent.OnEnterPressed -> { handleCheckListEnterPressed(event.item) }
        }
    }

    private fun handleCheckListItemChecked(item: ChecklistItem, checked: Boolean) {

    }

    private fun handleCheckListEnterPressed(item: ChecklistItem) {
        parent.setViewDraggable(item, item.dragHandle)
        addItem(false, true)
    }

    private fun handleCheckListItemRemoved(item: ChecklistItem) {
        parent.requestFocus()
        parent.removeDragView(item)
    }
}