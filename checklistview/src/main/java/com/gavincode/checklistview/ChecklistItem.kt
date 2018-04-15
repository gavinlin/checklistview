package com.gavincode.checklistview

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout


class ChecklistItem: LinearLayout {

    lateinit var rootView: LinearLayout
    lateinit var checkbox: CheckBox
    lateinit var editText: ChecklistEditText
    lateinit var dragHandle: ImageView
    lateinit var deleteView: ImageButton
    lateinit var add: ImageButton

    private var listener: OnChecklistItemEventListener? = null

    constructor(context: Context): super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet)
            : super(context, attrs) {
        init()
    }

    private fun init() {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.view_checklist_item, this, true)
        rootView = view.findViewById(R.id.rootview)
        checkbox = view.findViewById(R.id.checklist_item_checkbox)
        editText = view.findViewById(R.id.checklist_item_edittext)
        deleteView = view.findViewById(R.id.checklist_item_delete)
        dragHandle = view.findViewById(R.id.checklist_item_drag_handle)
        add = view.findViewById(R.id.checklist_item_add_icon)

        checkbox.setOnCheckedChangeListener {
            _, isChecked ->
            listener?.onChanged(ChecklistItemEvent.OnChecklistItemChecked(this@ChecklistItem, isChecked))
        }

        editText.apply {
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setRawInputType(InputType.TYPE_CLASS_TEXT)
            setOnFocusChangeListener { v, hasFocus ->
                listener?.onChanged(ChecklistItemEvent.OnFocusChanged(this@ChecklistItem, hasFocus))
            }
        }

        editText.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (isEmpty()) listener?.onChanged(ChecklistItemEvent.OnChecklistItemRemovedByIME(this))
            }
            false
        }

        editText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                listener?.onChanged(ChecklistItemEvent.OnEnterPressed(this))
                true
            }
            else false
        }

        editText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listener?.onChanged(ChecklistItemEvent.OnEdited(this@ChecklistItem))
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        deleteView.setOnClickListener { listener?.onChanged(ChecklistItemEvent.OnChecklistItemRemoved(this)) }
    }

    fun setListener(listener: OnChecklistItemEventListener) {
        this.listener = listener
    }

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean {
        return editText.requestFocus(direction, previouslyFocusedRect)
    }

    fun isEmpty(): Boolean = editText.text.toString().isEmpty()
    fun isChecked(): Boolean = checkbox.isChecked

}