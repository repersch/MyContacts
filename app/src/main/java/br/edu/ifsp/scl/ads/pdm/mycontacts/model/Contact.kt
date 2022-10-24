package br.edu.ifsp.scl.ads.pdm.mycontacts.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Contact(
    val id: Int,
    var name: String,
    var address: String,
    var phone: String,
    var email: String
): Parcelable
