package it.saimao.bamardictionary.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "dictionary",
        indices = {
                @Index(value = {"stripword"}),
                @Index(value = {"word"})
        })

public class DictionaryEntity {
    @PrimaryKey
    @ColumnInfo(name = "_id")
    private int id;
    private String word;
    @ColumnInfo(name = "stripword")
    private String stripWord;
    private String title;
    private String definition;
    private String keywords;
    @ColumnInfo(name = "synonym")
    private String synonyms;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getStripWord() {
        return stripWord;
    }

    public void setStripWord(String stripWord) {
        this.stripWord = stripWord;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DictionaryEntity)) return false;
        DictionaryEntity that = (DictionaryEntity) o;
        return id == that.id && Objects.equals(word, that.word) && Objects.equals(stripWord, that.stripWord) && Objects.equals(title, that.title) && Objects.equals(definition, that.definition) && Objects.equals(keywords, that.keywords) && Objects.equals(synonyms, that.synonyms) && Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, word, stripWord, title, definition, keywords, synonyms, picture);
    }

    @Override
    public String toString() {
        return "DictionaryEntity{" +
                "id=" + id +
                ", word='" + word + '\'' +
                ", stripWord='" + stripWord + '\'' +
                ", title='" + title + '\'' +
                ", definition='" + definition + '\'' +
                ", keywords='" + keywords + '\'' +
                ", synonyms='" + synonyms + '\'' +
                ", picture='" + picture + '\'' +
                '}';
    }
}
