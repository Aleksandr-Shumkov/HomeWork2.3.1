package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class WallServiceTest {


    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add(){
        val post = WallService.add(Post(authorId = 35, authorName = "Petrovych", text = "sldg elrbjl trjbn khtkj gskul", comments = null, reposts = null, copyHistory = null))
        val post1 = WallService.add(Post(authorId = 1, authorName = "Alex", text = "dsgssdgedgbdbed", comments = null, reposts = null, copyHistory = null))
        assertEquals(1, post.id)
        assertEquals(2, post1.id)
    }

    @Test
    fun update() {
        val post = WallService.add(Post(authorId = 35, authorName = "Petrovych", text = "sldg elrbjl trjbn khtkj gskul", comments = null, reposts = null, copyHistory = null))
        //изменили ли мы уже существующую запись P.S. запись добавлена
        assertTrue(WallService.update(post))
        // Проверка на изменение несуществующей записи
        assertFalse(WallService.update(Post(authorId = 12, authorName = "Kolya Usypov", text = "Привет", comments = null, reposts = null, copyHistory = null)))
    }
}