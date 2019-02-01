package com.example.ea_create.momosheduler.Models

import android.text.method.DateTimeKeyListener
import java.sql.Time
import java.util.*

class Users(
    var Id: Int = 0,
    var name: String = "",
    var surname: String = "",
    var email: String = "",
    var phone: String = ""
) {}

class UserCompte(
    var Id: Int = 0,
    var userId: String = "",
    var password: String = "",
    var login: String = "",
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
    var operatorId: String = "",
    var actionModeId: String = "",
    var customerId: String = "",
    var solde: String = "",
    var referencecode: String = "",
    var datetime: Date = Date(),
    var operationClosed: Boolean = false
) {}
