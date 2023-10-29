package com.geekbrains.repo

import com.geekbrains.entities.Word

class FakeRepository() : RepositoryInterface<List<Word>> {
    override suspend fun getDataRepository(word: String): List<Word> {
        val words: MutableList<Word> = mutableListOf()
        for (index in 1..20) {
            val wordIndex = index.toString()
            words.add(
                Word(wordIndex,
                    "Word:${wordIndex}",
                    Word.Meanings(
                        Word.Meanings.Translation("Translation:${wordIndex}"))
                )
            )
        }
        return words.toList()
    }

    override suspend fun setDataLocal(words: List<Word>) {
        TODO("Not yet implemented")
    }

    override suspend fun setDataFavorite(words: Word) {
        TODO("Not yet implemented")
    }

    override suspend fun getDataFavorite(): List<Word> {
        TODO("Not yet implemented")
    }
}