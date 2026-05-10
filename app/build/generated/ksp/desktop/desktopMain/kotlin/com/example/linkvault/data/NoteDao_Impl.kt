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
public class NoteDao_Impl(
  __db: RoomDatabase,
) : NoteDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfNoteEntity: EntityInsertAdapter<NoteEntity>

  private val __deleteAdapterOfNoteEntity: EntityDeleteOrUpdateAdapter<NoteEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfNoteEntity = object : EntityInsertAdapter<NoteEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `notes` (`id`,`title`,`content`,`is_pinned`,`color`,`tags`,`timestamp`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: NoteEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.content)
        val _tmp: Int = if (entity.isPinned) 1 else 0
        statement.bindLong(4, _tmp.toLong())
        val _tmpColor: Long? = entity.color
        if (_tmpColor == null) {
          statement.bindNull(5)
        } else {
          statement.bindLong(5, _tmpColor)
        }
        val _tmpTags: String? = entity.tags
        if (_tmpTags == null) {
          statement.bindNull(6)
        } else {
          statement.bindText(6, _tmpTags)
        }
        statement.bindLong(7, entity.timestamp)
      }
    }
    this.__deleteAdapterOfNoteEntity = object : EntityDeleteOrUpdateAdapter<NoteEntity>() {
      protected override fun createQuery(): String = "DELETE FROM `notes` WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: NoteEntity) {
        statement.bindLong(1, entity.id)
      }
    }
  }

  public override suspend fun insertNote(note: NoteEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfNoteEntity.insert(_connection, note)
  }

  public override suspend fun deleteNote(note: NoteEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __deleteAdapterOfNoteEntity.handle(_connection, note)
  }

  public override fun getAllNotes(): Flow<List<NoteEntity>> {
    val _sql: String = "SELECT * FROM notes ORDER BY is_pinned DESC, timestamp DESC"
    return createFlow(__db, false, arrayOf("notes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfIsPinned: Int = getColumnIndexOrThrow(_stmt, "is_pinned")
        val _cursorIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _cursorIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<NoteEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NoteEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_cursorIndexOfContent)
          val _tmpIsPinned: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsPinned).toInt()
          _tmpIsPinned = _tmp != 0
          val _tmpColor: Long?
          if (_stmt.isNull(_cursorIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getLong(_cursorIndexOfColor)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_cursorIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_cursorIndexOfTags)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpIsPinned,_tmpColor,_tmpTags,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun searchNotes(query: String): Flow<List<NoteEntity>> {
    val _sql: String =
        "SELECT * FROM notes WHERE title LIKE '%' || ? || '%' OR content LIKE '%' || ? || '%' ORDER BY is_pinned DESC, timestamp DESC"
    return createFlow(__db, false, arrayOf("notes")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, query)
        _argIndex = 2
        _stmt.bindText(_argIndex, query)
        val _cursorIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _cursorIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _cursorIndexOfContent: Int = getColumnIndexOrThrow(_stmt, "content")
        val _cursorIndexOfIsPinned: Int = getColumnIndexOrThrow(_stmt, "is_pinned")
        val _cursorIndexOfColor: Int = getColumnIndexOrThrow(_stmt, "color")
        val _cursorIndexOfTags: Int = getColumnIndexOrThrow(_stmt, "tags")
        val _cursorIndexOfTimestamp: Int = getColumnIndexOrThrow(_stmt, "timestamp")
        val _result: MutableList<NoteEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: NoteEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_cursorIndexOfId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_cursorIndexOfTitle)
          val _tmpContent: String
          _tmpContent = _stmt.getText(_cursorIndexOfContent)
          val _tmpIsPinned: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_cursorIndexOfIsPinned).toInt()
          _tmpIsPinned = _tmp != 0
          val _tmpColor: Long?
          if (_stmt.isNull(_cursorIndexOfColor)) {
            _tmpColor = null
          } else {
            _tmpColor = _stmt.getLong(_cursorIndexOfColor)
          }
          val _tmpTags: String?
          if (_stmt.isNull(_cursorIndexOfTags)) {
            _tmpTags = null
          } else {
            _tmpTags = _stmt.getText(_cursorIndexOfTags)
          }
          val _tmpTimestamp: Long
          _tmpTimestamp = _stmt.getLong(_cursorIndexOfTimestamp)
          _item =
              NoteEntity(_tmpId,_tmpTitle,_tmpContent,_tmpIsPinned,_tmpColor,_tmpTags,_tmpTimestamp)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
