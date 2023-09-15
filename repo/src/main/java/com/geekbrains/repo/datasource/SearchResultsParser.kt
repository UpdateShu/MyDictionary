package com.geekbrains.repo.datasource

import com.geekbrains.entities.SearchDTOItem
import com.geekbrains.entities.Word
import com.geekbrains.entities.room.WordEntity

fun convertSearchDtoToWords(searchItems: List<SearchDTOItem>)
    = searchItems.map { searchItem ->
        var meanings: List<Word.Meanings> = listOf()
        searchItem.meanings?.let {
            meanings = it.map { meaningDTO ->
                Word.Meanings(
                    Word.Meanings.Translation(meaningDTO?.translation?.text ?: ""),
                    meaningDTO?.imageUrl ?: ""
                )
            }
        }
        Word(searchItem.id.toString(), searchItem.text ?: "", meanings.first())
    }

fun convertWordEntityToWords(wordEntities: List<WordEntity>)
        = wordEntities.map { wordEntity ->
    val meaning = wordEntity.meanings?.let {
        Word.Meanings(Word.Meanings.Translation(it.translation?.translationText ?: ""),
            it.imageUrl ?: "") } ?: null
    Word(wordEntity.wId.toString(), wordEntity.text ?: "", meaning)
}