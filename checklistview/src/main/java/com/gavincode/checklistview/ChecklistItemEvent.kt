package com.gavincode.checklistview

sealed class ChecklistItemEvent(val item: ChecklistItem) {
    class OnChecklistItemChecked(item: ChecklistItem, val checked: Boolean)
        : ChecklistItemEvent(item)
    class OnChecklistItemRemoved(item: ChecklistItem): ChecklistItemEvent(item)
    class OnEnterPressed(item: ChecklistItem): ChecklistItemEvent(item)
    class OnLostFocus(item: ChecklistItem): ChecklistItemEvent(item)
    class OnEdited(item: ChecklistItem): ChecklistItemEvent(item)
}