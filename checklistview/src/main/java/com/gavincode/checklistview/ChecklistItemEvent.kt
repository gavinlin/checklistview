package com.gavincode.checklistview

sealed class ChecklistItemEvent(val item: ChecklistItem) {
    class OnChecklistItemChecked(item: ChecklistItem, val checked: Boolean)
        : ChecklistItemEvent(item)
    class OnChecklistItemRemoved(item: ChecklistItem): ChecklistItemEvent(item)
    class OnEnterPressed(item: ChecklistItem): ChecklistItemEvent(item)
    class OnFocusChanged(item: ChecklistItem, val focused: Boolean): ChecklistItemEvent(item)
    class OnEdited(item: ChecklistItem): ChecklistItemEvent(item)
    class OnChecklistItemRemovedByIME(item: ChecklistItem): ChecklistItemEvent(item)
}