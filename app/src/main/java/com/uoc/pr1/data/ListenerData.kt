package com.uoc.pr1.data

import com.uoc.pr1.data.model.User

public class ListenerData {
    var onLogin: (user:Result<User>) -> Unit = {}
    var onSeminarsUser: () -> Unit = {}
    var onSeminarsUserIds:(List<Long>) -> Unit = {}
    var onItemsSeminar: () -> Unit = {}
    var onNewRequestId: (new_id:Int) -> Unit = {}
    var onNewSeminarId: (new_id:Int) -> Unit = {}
    var onNewSeminar: () -> Unit = {}

}