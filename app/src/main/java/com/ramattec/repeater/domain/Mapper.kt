package com.ramattec.repeater.domain

interface Mapper<I, O>{
    fun map(input: I): O
}