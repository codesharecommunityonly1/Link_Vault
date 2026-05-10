package com.example.linkvault.`data`

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import javax.`annotation`.processing.Generated
import kotlin.Boolean
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class LinkDao_Impl(
  __db: RoomDatabase,
) : LinkDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfLinkEntity: EntityInsertAdapter<LinkEntity>

  private val __deleteAdapterOfLinkEntity: EntityDeleteOrUpdateAdapter<LinkEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfLinkEntity = object : EntityInsertAdapter<LinkEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `links` (`id`,`url`,`title`,`description`,`imageUrl`,`collection_name`,`is_dead`,`is_duplicate`,`is_private`,`reading_time`,`is_video`,`content`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: LinkEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.url)
        statement.bindText(3, entity.title)
        val _tmpDescription: String? = entity.description
        if (_tmpDescription == null) {
          statement.bindNull(4)
        } else {
          statement.bindText(4, _tmpDescription)
        }
        val _tmpImageUrl: String? = entity.imageUrl
        if (_tmpImageUrl == null) {
          statement.bindNull(5)
        } else {
          statement.bindText(5, _tmpImageUrl)
        }
        val _tmpCollectionName: String? = entity.collectionName
        if (_tmpCollectionName == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpCollectionName)
        }
        val _tmp: Int = if (entity.isDead) 1 else 0
        statement.bindLong(7, _tmp.toLong())
        val _tmp_1: Int = if (entity.isDuplicate) 1 else 0
        statement.bindLong(8, _tmp_1.toLong())
        val _tmp_2: Int = if (entity.isPrivate) 1 else 0
        statement.bindLong(9, _tmp_2.toLong())
        val _tmpReadingTime: Int? = entity.readingTime
        if (_tmpReadingTime == null) {
          statement.bindNull(10)
        } else {
          statement.bindLong(10, _tmpReadingTime.toLong())
        }
        val _tmp_3: Int = if (entity.isVideo) 1 else 0
        statement.bindLong(11, _tmp_3.toLong())
        val _tmpContent: String? = entity.content
        if (_tmpContent == null) {
          statement.bindNull(12)
        } else {
          statement.bindText(12, _tmpContent)
        }
        statement.bindLong(13, entity.timestamp)
      }
    }
    this.__deleteAdapterOfLinkEntity = object : EntityDeleteOrUpdateAdapter<LinkEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `links` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: LinkEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertLink(link: LinkEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfLinkEntity.insertAndReturnId(_connection, link)
    _result
  }

  public override suspend fun deleteLink(link: LinkEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfLinkEntity.handle(_connection, link)
  }

  public override fun getAllLinks(): Flow<List<LinkEntity>> {
    val _sql: String = "SELECT * FROM links ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("links")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _cursorIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _cursorIndexOfCollectionName: Int = getColumnIndexOrThrow(_stmt, "collection_name")
        val _cursorIndexOfIsDead: Int = getColumnIndexOrThrow(_stmt, "is_dead")
        val _cursorIndexOfIsDuplicate: Int = getColumnIndexOrThrow(_stmt, "is_duplicate")
        val _cursorIndexOfIsPrivate: Int = getColumnIndexOrThrow(_stmt, "is_private")
        val _cursorIndexOfReadingTime: Int = getColumnIndexOrThrow(_stmt, "reading_time")
        val _cursorIndexOfIsVideo: Int = getColumnIndexOrThrow(_stmt, "is_video")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LinkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LinkEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_cursorIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_cursorIndexOfDescription)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_cursorIndexOfImageUrl)
          }
          val _tmpCollectionName: String?
          if (_stmt.isNull(_cursorIndexOfCollectionName)) {
            _tmpCollectionName = null
          } else {
            _tmpCollectionName = _stmt.getText(_cursorIndexOfCollectionName)
          }
          val _tmpIsDead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsDead).toInt()
          _tmpIsDead = _tmp != 0
          val _tmpIsDuplicate: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_cursorIndexOfIsDuplicate).toInt()
          _tmpIsDuplicate = _tmp_1 != 0
          val _tmpIsPrivate: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_cursorIndexOfIsPrivate).toInt()
          _tmpIsPrivate = _tmp_2 != 0
          val _tmpReadingTime: Int?
          if (_stmt.isNull(_cursorIndexOfReadingTime)) {
            _tmpReadingTime = null
          } else {
            _tmpReadingTime = _stmt.getLong(_cursorIndexOfReadingTime).toInt()
          }
          val _tmpIsVideo: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_cursorIndexOfIsVideo).toInt()
          _tmpIsVideo = _tmp_3 != 0
          val _tmpContent: String?
          if (_stmt.isNull(_cursorIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_cursorIndexOfContent)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpImageUrl,_tmpCollectionName,_tmpIsDead,_tmpIsDuplicate,_tmpIsPrivate,_tmpReadingTime,_tmpIsVideo,_tmpContent,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getAllLinksOnce(): List<LinkEntity> {
    val _sql: String = "SELECT * FROM links ORDER BY timestamp DESC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _cursorIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _cursorIndexOfCollectionName: Int = getColumnIndexOrThrow(_stmt, "collection_name")
        val _cursorIndexOfIsDead: Int = getColumnIndexOrThrow(_stmt, "is_dead")
        val _cursorIndexOfIsDuplicate: Int = getColumnIndexOrThrow(_stmt, "is_duplicate")
        val _cursorIndexOfIsPrivate: Int = getColumnIndexOrThrow(_stmt, "is_private")
        val _cursorIndexOfReadingTime: Int = getColumnIndexOrThrow(_stmt, "reading_time")
        val _cursorIndexOfIsVideo: Int = getColumnIndexOrThrow(_stmt, "is_video")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LinkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LinkEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_cursorIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_cursorIndexOfDescription)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_cursorIndexOfImageUrl)
          }
          val _tmpCollectionName: String?
          if (_stmt.isNull(_cursorIndexOfCollectionName)) {
            _tmpCollectionName = null
          } else {
            _tmpCollectionName = _stmt.getText(_cursorIndexOfCollectionName)
          }
          val _tmpIsDead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsDead).toInt()
          _tmpIsDead = _tmp != 0
          val _tmpIsDuplicate: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_cursorIndexOfIsDuplicate).toInt()
          _tmpIsDuplicate = _tmp_1 != 0
          val _tmpIsPrivate: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_cursorIndexOfIsPrivate).toInt()
          _tmpIsPrivate = _tmp_2 != 0
          val _tmpReadingTime: Int?
          if (_stmt.isNull(_cursorIndexOfReadingTime)) {
            _tmpReadingTime = null
          } else {
            _tmpReadingTime = _stmt.getLong(_cursorIndexOfReadingTime).toInt()
          }
          val _tmpIsVideo: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_cursorIndexOfIsVideo).toInt()
          _tmpIsVideo = _tmp_3 != 0
          val _tmpContent: String?
          if (_stmt.isNull(_cursorIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_cursorIndexOfContent)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpImageUrl,_tmpCollectionName,_tmpIsDead,_tmpIsDuplicate,_tmpIsPrivate,_tmpReadingTime,_tmpIsVideo,_tmpContent,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllCollections(): Flow<List<String>> {
    val _sql: String =
        "SELECT DISTINCT collection_name FROM links WHERE collection_name IS NOT NULL"
    return createFlow(__db, false, arrayOf("links")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getLinksByCollection(collectionName: String): Flow<List<LinkEntity>> {
    val _sql: String = "SELECT * FROM links WHERE collection_name = ? ORDER BY timestamp DESC"
    return createFlow(__db, false, arrayOf("links")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, collectionName)
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _cursorIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _cursorIndexOfCollectionName: Int = getColumnIndexOrThrow(_stmt, "collection_name")
        val _cursorIndexOfIsDead: Int = getColumnIndexOrThrow(_stmt, "is_dead")
        val _cursorIndexOfIsDuplicate: Int = getColumnIndexOrThrow(_stmt, "is_duplicate")
        val _cursorIndexOfIsPrivate: Int = getColumnIndexOrThrow(_stmt, "is_private")
        val _cursorIndexOfReadingTime: Int = getColumnIndexOrThrow(_stmt, "reading_time")
        val _cursorIndexOfIsVideo: Int = getColumnIndexOrThrow(_stmt, "is_video")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LinkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LinkEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_cursorIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_cursorIndexOfDescription)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_cursorIndexOfImageUrl)
          }
          val _tmpCollectionName: String?
          if (_stmt.isNull(_cursorIndexOfCollectionName)) {
            _tmpCollectionName = null
          } else {
            _tmpCollectionName = _stmt.getText(_cursorIndexOfCollectionName)
          }
          val _tmpIsDead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsDead).toInt()
          _tmpIsDead = _tmp != 0
          val _tmpIsDuplicate: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_cursorIndexOfIsDuplicate).toInt()
          _tmpIsDuplicate = _tmp_1 != 0
          val _tmpIsPrivate: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_cursorIndexOfIsPrivate).toInt()
          _tmpIsPrivate = _tmp_2 != 0
          val _tmpReadingTime: Int?
          if (_stmt.isNull(_cursorIndexOfReadingTime)) {
            _tmpReadingTime = null
          } else {
            _tmpReadingTime = _stmt.getLong(_cursorIndexOfReadingTime).toInt()
          }
          val _tmpIsVideo: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_cursorIndexOfIsVideo).toInt()
          _tmpIsVideo = _tmp_3 != 0
          val _tmpContent: String?
          if (_stmt.isNull(_cursorIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_cursorIndexOfContent)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpImageUrl,_tmpCollectionName,_tmpIsDead,_tmpIsDuplicate,_tmpIsPrivate,_tmpReadingTime,_tmpIsVideo,_tmpContent,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchLinks(query: String): Flow<List<LinkEntity>> {
    val _sql: String = """
        |
        |        SELECT * FROM links 
        |        JOIN links_fts ON links.id = links_fts.rowid 
        |        WHERE links_fts MATCH ?
        |    
        """.trimMargin()
    return createFlow(__db, false, arrayOf("links", "links_fts")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _cursorIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _cursorIndexOfCollectionName: Int = getColumnIndexOrThrow(_stmt, "collection_name")
        val _cursorIndexOfIsDead: Int = getColumnIndexOrThrow(_stmt, "is_dead")
        val _cursorIndexOfIsDuplicate: Int = getColumnIndexOrThrow(_stmt, "is_duplicate")
        val _cursorIndexOfIsPrivate: Int = getColumnIndexOrThrow(_stmt, "is_private")
        val _cursorIndexOfReadingTime: Int = getColumnIndexOrThrow(_stmt, "reading_time")
        val _cursorIndexOfIsVideo: Int = getColumnIndexOrThrow(_stmt, "is_video")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<LinkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: LinkEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_cursorIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_cursorIndexOfDescription)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_cursorIndexOfImageUrl)
          }
          val _tmpCollectionName: String?
          if (_stmt.isNull(_cursorIndexOfCollectionName)) {
            _tmpCollectionName = null
          } else {
            _tmpCollectionName = _stmt.getText(_cursorIndexOfCollectionName)
          }
          val _tmpIsDead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsDead).toInt()
          _tmpIsDead = _tmp != 0
          val _tmpIsDuplicate: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_cursorIndexOfIsDuplicate).toInt()
          _tmpIsDuplicate = _tmp_1 != 0
          val _tmpIsPrivate: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_cursorIndexOfIsPrivate).toInt()
          _tmpIsPrivate = _tmp_2 != 0
          val _tmpReadingTime: Int?
          if (_stmt.isNull(_cursorIndexOfReadingTime)) {
            _tmpReadingTime = null
          } else {
            _tmpReadingTime = _stmt.getLong(_cursorIndexOfReadingTime).toInt()
          }
          val _tmpIsVideo: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_cursorIndexOfIsVideo).toInt()
          _tmpIsVideo = _tmp_3 != 0
          val _tmpContent: String?
          if (_stmt.isNull(_cursorIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_cursorIndexOfContent)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpImageUrl,_tmpCollectionName,_tmpIsDead,_tmpIsDuplicate,_tmpIsPrivate,_tmpReadingTime,_tmpIsVideo,_tmpContent,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getLinkById(linkId: Long): LinkEntity? {
    val _sql: String = "SELECT * FROM links WHERE id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, linkId)
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfUrl: Int = getColumnIndexOrThrow(_stmt, "url")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _cursorIndexOfImageUrl: Int = getColumnIndexOrThrow(_stmt, "imageUrl")
        val _cursorIndexOfCollectionName: Int = getColumnIndexOrThrow(_stmt, "collection_name")
        val _cursorIndexOfIsDead: Int = getColumnIndexOrThrow(_stmt, "is_dead")
        val _cursorIndexOfIsDuplicate: Int = getColumnIndexOrThrow(_stmt, "is_duplicate")
        val _cursorIndexOfIsPrivate: Int = getColumnIndexOrThrow(_stmt, "is_private")
        val _cursorIndexOfReadingTime: Int = getColumnIndexOrThrow(_stmt, "reading_time")
        val _cursorIndexOfIsVideo: Int = getColumnIndexOrThrow(_stmt, "is_video")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: LinkEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpUrl: String
          _tmpUrl = _stmt.getText(_cursorIndexOfUrl)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpDescription: String?
          if (_stmt.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null
          } else {
            _tmpDescription = _stmt.getText(_cursorIndexOfDescription)
          }
          val _tmpImageUrl: String?
          if (_stmt.isNull(_cursorIndexOfImageUrl)) {
            _tmpImageUrl = null
          } else {
            _tmpImageUrl = _stmt.getText(_cursorIndexOfImageUrl)
          }
          val _tmpCollectionName: String?
          if (_stmt.isNull(_cursorIndexOfCollectionName)) {
            _tmpCollectionName = null
          } else {
            _tmpCollectionName = _stmt.getText(_cursorIndexOfCollectionName)
          }
          val _tmpIsDead: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsDead).toInt()
          _tmpIsDead = _tmp != 0
          val _tmpIsDuplicate: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_cursorIndexOfIsDuplicate).toInt()
          _tmpIsDuplicate = _tmp_1 != 0
          val _tmpIsPrivate: Boolean
          val _tmp_2: Int
          _tmp_2 = _stmt.getLong(_cursorIndexOfIsPrivate).toInt()
          _tmpIsPrivate = _tmp_2 != 0
          val _tmpReadingTime: Int?
          if (_stmt.isNull(_cursorIndexOfReadingTime)) {
            _tmpReadingTime = null
          } else {
            _tmpReadingTime = _stmt.getLong(_cursorIndexOfReadingTime).toInt()
          }
          val _tmpIsVideo: Boolean
          val _tmp_3: Int
          _tmp_3 = _stmt.getLong(_cursorIndexOfIsVideo).toInt()
          _tmpIsVideo = _tmp_3 != 0
          val _tmpContent: String?
          if (_stmt.isNull(_cursorIndexOfContent)) {
            _tmpContent = null
          } else {
            _tmpContent = _stmt.getText(_cursorIndexOfContent)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _result =
              LinkEntity(_tmpId,_tmpUrl,_tmpTitle,_tmpDescription,_tmpImageUrl,_tmpCollectionName,_tmpIsDead,_tmpIsDuplicate,_tmpIsPrivate,_tmpReadingTime,_tmpIsVideo,_tmpContent,_tmpTimestamp)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateIsDead(id: Long, isDead: Boolean) {
    val _sql: String = "UPDATE links SET is_dead = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isDead) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateIsDuplicate(id: Long, isDuplicate: Boolean) {
    val _sql: String = "UPDATE links SET is_duplicate = ? WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: Int = if (isDuplicate) 1 else 0
        _stmt.bindLong(_argIndex, _tmp.toLong())
        _argIndex = 2
        _stmt.bindLong(_argIndex, id)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteLinkById(linkId: Long) {
    val _sql: String = "DELETE FROM links WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, linkId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
