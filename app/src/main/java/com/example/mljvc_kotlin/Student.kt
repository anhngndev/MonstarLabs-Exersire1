package com.example.mljvc_kotlin

class Student(
    var name: String,
    var born: String,
    var phone: String,
    var major: String,
    var tS: String) {

    var link = "$name $born $major $tS".toLowerCase()

}