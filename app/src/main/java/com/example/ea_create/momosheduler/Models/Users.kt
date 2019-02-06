package com.example.ea_create.momosheduler.Models

import android.text.method.DateTimeKeyListener
import java.sql.Time
import java.util.*

class UserCompte(
    var Id: Int = 0,
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var role: String = "",
    var connected: Boolean = false,
    var activate: Boolean = false
) {}

class Operator(
    var Id: Int = 0,
    var operator: String = ""
) {}

class Customer(
    var Id: Int = 0,
    var name: String = "",
    var phone: String = ""

) {}

class ActionMode(
    var Id: Int = 0,
    var mode: String = ""
) {}

class Operation(
    var Id: Int = 0,
    var operator: String = "",
    var actionMode: String = "",
    var customer: String = "",
    var solde: String = "",
    var referencecode: String = "",
    var datetime: String=""

) {}
