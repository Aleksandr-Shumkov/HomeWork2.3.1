package ru.netology

fun main() {
    //проверка лайков
    val post = Post(authorId = 1, authorName = "Alex", text = "dsgssdgedgbdbed", comments = null, reposts = null, copyHistory = null)
    WallService.add(post)
    var post1 = Post(authorId = 35, authorName = "Petrovych", text = "sldg elrbjl trjbn khtkj gskul", comments = null, reposts = null, copyHistory = null)
    val post2 = Post(authorId = 12, authorName = "Kolya Usypov", text = "Привет", comments = null, reposts = null, copyHistory = null)
    WallService.add(post1)

    WallService.likeById(post1.id)
    WallService.likeById(post1.id)
    WallService.likeById(post2.id)
    WallService.likeById(post1.id)
    WallService.likeById(post1.id)

    println(WallService.getPostString())

    post1 = post1.copy(text = "sdhve kjhbkdfb")
    WallService.update(post1)
    println("----------------------")
    println(WallService.getPostString())

    WallService.add(post2)
    WallService.likeById(post2.id)
    WallService.likeById(post1.id)
    println("----------------------")
    println(WallService.getPostString())
}

object CorrectId {
    private var lastId = 0
    //смотрим в то место где у нас хранятся id постов и выдаём свободный

    fun getNewId(id: Int): Int {
        if (id == 0) {
            lastId += 1
            return lastId
        }

        return 0
    }

    fun clearId() {
        lastId = 0
    }
}

data class Post(
    val id: Int = CorrectId.getNewId(0),
    val ownerId: Int = 0,       //Идентификатор владельца стены, на которой размещена запись.
    val fromId: Int = 0,        //Идентификатор автора записи (от чьего имени опубликована запись).
    val date: Int = 0,   //Время публикации записи в формате unixtime.
    val replyOwnerId: Int = 0, //Идентификатор владельца записи, в ответ на которую была оставлена текущая.
    val replyPostId: Int = 0,  //Идентификатор записи, в ответ на которую была оставлена текущая.
    val canDelete: Boolean = false, // Информация о том, может ли текущий пользователь удалить запись (1 — может, 0 — не может).
    val isPinned: Boolean = false, // Информация о том, что запись закреплена.
    val markedAsAds: Boolean = false, // Информация о том, содержит ли запись отметку "реклама" (1 — да, 0 — нет).
    val isFavorite: Boolean = false, // true, если объект добавлен в закладки у текущего пользователя.
    val postponedId: Int = 0, // Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
    val postType: String = "post", //String  Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    val signerId: Int = 0, //Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем;
    val copyHistory: CopyHistory?,
    val views: Views = Views(),
    val comments: Comments?,
    val copyright: Copyright = Copyright(),
    val reposts: Reposts?,
    val geo: Geo = Geo(),
    val donut: Donut = Donut(),
    val authorId: Int,
    val authorName: String,
    val text: String,
    val likes: Likes = Likes(),
    val friendsOnly: Boolean = false,
    val canPin: Boolean = false,
    val canEdit: Boolean = true
)

data class Donut(
    var isDonut: Boolean = false,
    var paidDuration: Int = 100,
    var canPublishFreeCopy: Boolean = true,
    val placeholder: Placeholder = Placeholder(),
    var editMode: String = "duration"
)

data class Placeholder(
    var name: String = "Смотрим на меня"
)

data class Reposts(
    var count: Int = 0,
    var userReposted: Boolean = false
)

data class Geo(
    val type: String = "", //Фонтан
    val coordinates: String = "", //"56.008772, 92.870401"
    val place: Places = Places()
)

data class Places(
    val name: String = ""//"Главный фонтан на Театральной площади г. Красноярска"
)

data class Comments(
    var count: Int = 0,
    var canPost: Boolean = true,
    var groupsCanPost: Boolean = true,
    var canClose: Boolean = false,
    var canOpen: Boolean = true
)

data class Copyright(
    val id: Int = 0, //тупо 0 потому что мы всего-лишьи имитируем ссылку извне
    val link: String = "",
    val name: String = "",
    val type: String = ""
)


data class Views(var count: Int = 0)

data class CopyHistory(
    var posts: Array<Post> = emptyArray()
)

data class Likes(
    var count: Int = 0,
    var userLikes: Boolean = false,
    var canLike: Boolean = true
)

object WallService {
    private var posts = emptyArray<Post>()

    fun getPostString() {
        for (i in posts) {
            println(i.toString())
        }
    }

    fun clear() {
        CorrectId.clearId()
        posts = emptyArray()
    }

    fun add(post: Post): Post {
        posts += post
        return posts.last()
    }

    fun update(post: Post): Boolean {
        for ((index, i) in posts.withIndex()) {
            if (i.id == post.id) {
                posts[index] = post.copy(authorName = post.authorName, text = post.text, likes = i.likes, friendsOnly = post.friendsOnly,
                    canPin = post.canPin, canEdit = post.canEdit)
                return true
            }
        }
        return false
    }

    fun likeById(id: Int) {
        for ((index, post) in posts.withIndex()) {
            if (post.id == id) {
                posts[index] = post.copy(likes = Likes(post.likes.count + 1, post.likes.userLikes, post.likes.canLike))
            }
        }
    }

}