package com.gavincode.checklistview

import android.os.Parcel
import android.os.Parcelable


data class ChecklistData(val text: String,val checked: Boolean): Parcelable {

    companion object {
        @JvmField val CREATOR = object: Parcelable.Creator<ChecklistData> {
            override fun createFromParcel(source: Parcel) = ChecklistData(source)
            override fun newArray(size: Int) = arrayOfNulls<ChecklistData>(size)
        }
    }


    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(text)
        dest?.writeByte(if (checked) 1 else 0)
    }

    override fun describeContents(): Int = 0

    private constructor(parcel: Parcel): this (
            text = parcel.readString(),
            checked = (parcel.readByte() == 0.toByte())
    )

}