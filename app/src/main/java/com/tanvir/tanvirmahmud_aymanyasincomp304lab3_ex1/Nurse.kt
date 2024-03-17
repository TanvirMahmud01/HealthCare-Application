package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

class Nurse(
    val nurseId: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val department: String = "",
    val password: String = ""
) {
    override fun toString(): String {
        return "($nurseId:$firstName:$lastName:$department:$password)"
    }
}