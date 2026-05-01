package com.uoc.pr1.data.model

import com.uoc.pr1.R


/* Returns initial list of Items. */
fun ItemsHardcodeSeminary1(): List<Item> {
    return listOf(
        Item(
            type = ItemType.BASIC,
            id = 1,
            question = "Select the incorrect feature of mobile devices",
            link = null,
            correct_answer = 1,
            answer1 = "They are bulky devices",
            answer2 = "They have permanent or intermittent network connectivity",
            answer3 = "They have processing capabilities",
            answer4 = "They offer high levels of interaction through screen, keyboard, vibrations, or sounds",
        ),
        Item(
            type = ItemType.BASIC,
            id = 2,
            question = "A wireless device is one that can communicate...",
            link = null,
            correct_answer = 2,
            answer1 = "via fiber optic cable",
            answer2 = "via a wireless network",
            answer3 = "via ethernet cables",
            answer4 = "None of the above is correct",
        ),
        Item(
            id = 3,
            type = ItemType.BASIC,
            question = "Which category of device is not considered 'mobile'?",
            link = "https://mobile-app-dev-uoc.github.io",
            correct_answer = 4,
            answer1 = "smartphone",
            answer2 = "tablet",
            answer3 = "wearable device",
            answer4 = "laptop computer",
        )
    )
}

fun ItemsHardcodeSeminary2(): List<Item> {
    return listOf(
        Item(
            type = ItemType.BASIC,
            id = 4,
            question = "Which operating system does Samsung use for some of its 'wearable' devices?",
            link = "https://mobile-app-dev-uoc.github.io",
            correct_answer = 3,
            answer1 = "MacOS",
            answer2 = "Windows",
            answer3 = "Tizen",
            answer4 = "Linux",
        ),
        Item(
            type = ItemType.BASIC,
            id = 5,
            question = "What is the latest available version of the Android operating system?",
            link = null,
            correct_answer = 2,
            answer1 = "10",
            answer2 = "16",
            answer3 = "13",
            answer4 = "15",
        ),
        Item(
            type = ItemType.BASIC,
            id = 6,
            question = "A web application is...",
            link = null,
            correct_answer = 1,
            answer1 = "a website specifically optimized for a mobile device",
            answer2 = "an application installed on a mobile device with access to hardware",
            answer3 = "Not used for anything in communications",
            answer4 = "The transmission of data via Ethernet",
        )
    )
}

fun ItemsHardcodeSeminary3(): List<Item> {
    return listOf(
        Item(
            type = ItemType.BASIC,
            id = 7,
            question = "A neural network is a method in artificial intelligence that teaches computers to process data in a way that is inspired by the human brain. It is a type of machine learning process, called deep learning, that uses interconnected nodes or neurons in a layered structure that resembles the human brain.",
            link = null,
            correct_answer = null,
            answer1 = null,
            answer2 = null,
            answer3 = null,
            answer4 = null,
        ),
        Item(
            type = ItemType.BASIC,
            id = 8,
            question = "Big Data is a term that describes the large volume of data, both structured and unstructured, that floods businesses every day. But it is not the amount of data that is important. What matters with Big Data is what organizations do with the data. Big Data can be analyzed to gain insights that lead to better decisions and strategic business moves.",
            link = null,
            correct_answer = null,
            answer1 = null,
            answer2 = null,
            answer3 = null,
            answer4 = null,
        ),
        Item(
            type = ItemType.BASIC,
            id = 9,
            question = "Artificial intelligence is one of the disciplines that has generated the most interest in recent years, not only in fields related to technology, but also in others such as health, finance or sports. Increasingly present in our daily lives",
            link = null,
            correct_answer = null,
            answer1 = null,
            answer2 = null,
            answer3 = null,
            answer4 = null,
        )
    )
}



