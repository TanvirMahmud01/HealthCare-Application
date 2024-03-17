package com.tanvir.tanvirmahmud_aymanyasincomp304lab3_ex1

class Patient {
    var patientId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var department: String? = null
    var nurseId: String? = null
    var room: String? = null

    constructor(
        patientId: String?,
        firstName: String?,
        lastName: String?,
        department: String?,
        nurseId: String?,
        room: String?
    ) {
        this.patientId = patientId
        this.firstName = firstName
        this.lastName = lastName
        this.department = department
        this.nurseId = nurseId
        this.room = room
    }

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(Patient.class)

    }
    override fun toString(): String {
        return "($patientId:$firstName:$lastName:$department:$nurseId:$room)"
    }
}