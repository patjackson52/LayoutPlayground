package io.jackson.layoutplayground

data class ItemViewModel(val title: String,
                         val subTitle: String,
                         val imageUrl: String)

val demoItemViewModel = ItemViewModel("Closer", "By Lemaitre - 1749", "https://upload.wikimedia.org/wikipedia/en/thumb/3/3e/Ne-Yo_-_Closer.jpg/220px-Ne-Yo_-_Closer.jpg" )