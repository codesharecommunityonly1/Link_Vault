package com.example.linkvault.`data`

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.FtsTableInfo
import androidx.room.util.TableInfo
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass
import androidx.room.util.FtsTableInfo.Companion.read as ftsTableInfoRead
import androidx.room.util.TableInfo.Companion.read as tableInfoRead

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _linkDao: Lazy<LinkDao> = lazy {
    LinkDao_Impl(this)
  }


  private val _noteDao: Lazy<NoteDao> = lazy {
    NoteDao_Impl(this)
  }


  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(7,
        "92b3d7abb9dbf9fa43f35a8f004d6131", "8cc8193aac524dd31c604e95eca37fa9") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `links` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `url` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT, `imageUrl` TEXT, `collection_name` TEXT, `is_dead` INTEGER NOT NULL, `is_duplicate` INTEGER NOT NULL, `is_private` INTEGER NOT NULL, `reading_time` INTEGER, `is_video` INTEGER NOT NULL, `content` TEXT, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `links_fts` USING FTS4(`title` TEXT NOT NULL, `description` TEXT, `url` TEXT NOT NULL, content=`links`)")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_BEFORE_UPDATE BEFORE UPDATE ON `links` BEGIN DELETE FROM `links_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_BEFORE_DELETE BEFORE DELETE ON `links` BEGIN DELETE FROM `links_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_AFTER_UPDATE AFTER UPDATE ON `links` BEGIN INSERT INTO `links_fts`(`docid`, `title`, `description`, `url`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`description`, NEW.`url`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_AFTER_INSERT AFTER INSERT ON `links` BEGIN INSERT INTO `links_fts`(`docid`, `title`, `description`, `url`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`description`, NEW.`url`); END")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `notes` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `is_pinned` INTEGER NOT NULL, `color` INTEGER, `tags` TEXT, `timestamp` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '92b3d7abb9dbf9fa43f35a8f004d6131')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `links`")
        connection.execSQL("DROP TABLE IF EXISTS `links_fts`")
        connection.execSQL("DROP TABLE IF EXISTS `notes`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_BEFORE_UPDATE BEFORE UPDATE ON `links` BEGIN DELETE FROM `links_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_BEFORE_DELETE BEFORE DELETE ON `links` BEGIN DELETE FROM `links_fts` WHERE `docid`=OLD.`rowid`; END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_AFTER_UPDATE AFTER UPDATE ON `links` BEGIN INSERT INTO `links_fts`(`docid`, `title`, `description`, `url`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`description`, NEW.`url`); END")
        connection.execSQL("CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_links_fts_AFTER_INSERT AFTER INSERT ON `links` BEGIN INSERT INTO `links_fts`(`docid`, `title`, `description`, `url`) VALUES (NEW.`rowid`, NEW.`title`, NEW.`description`, NEW.`url`); END")
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsLinks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsLinks.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("url", TableInfo.Column("url", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("description", TableInfo.Column("description", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("imageUrl", TableInfo.Column("imageUrl", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("collection_name", TableInfo.Column("collection_name", "TEXT", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("is_dead", TableInfo.Column("is_dead", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("is_duplicate", TableInfo.Column("is_duplicate", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("is_private", TableInfo.Column("is_private", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("reading_time", TableInfo.Column("reading_time", "INTEGER", false, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("is_video", TableInfo.Column("is_video", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("content", TableInfo.Column("content", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsLinks.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysLinks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesLinks: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoLinks: TableInfo = TableInfo("links", _columnsLinks, _foreignKeysLinks,
            _indicesLinks)
        val _existingLinks: TableInfo = tableInfoRead(connection, "links")
        if (!_infoLinks.equals(_existingLinks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |links(com.example.linkvault.data.LinkEntity).
              | Expected:
              |""".trimMargin() + _infoLinks + """
              |
              | Found:
              |""".trimMargin() + _existingLinks)
        }
        val _columnsLinksFts: MutableSet<String> = mutableSetOf()
        _columnsLinksFts.add("title")
        _columnsLinksFts.add("description")
        _columnsLinksFts.add("url")
        val _infoLinksFts: FtsTableInfo = FtsTableInfo("links_fts", _columnsLinksFts,
            "CREATE VIRTUAL TABLE IF NOT EXISTS `links_fts` USING FTS4(`title` TEXT NOT NULL, `description` TEXT, `url` TEXT NOT NULL, content=`links`)")
        val _existingLinksFts: FtsTableInfo = ftsTableInfoRead(connection, "links_fts")
        if (!_infoLinksFts.equals(_existingLinksFts)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |links_fts(com.example.linkvault.data.LinkFtsEntity).
              | Expected:
              |""".trimMargin() + _infoLinksFts + """
              |
              | Found:
              |""".trimMargin() + _existingLinksFts)
        }
        val _columnsNotes: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsNotes.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("content", TableInfo.Column("content", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("is_pinned", TableInfo.Column("is_pinned", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("color", TableInfo.Column("color", "INTEGER", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("tags", TableInfo.Column("tags", "TEXT", false, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsNotes.put("timestamp", TableInfo.Column("timestamp", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysNotes: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesNotes: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoNotes: TableInfo = TableInfo("notes", _columnsNotes, _foreignKeysNotes,
            _indicesNotes)
        val _existingNotes: TableInfo = tableInfoRead(connection, "notes")
        if (!_infoNotes.equals(_existingNotes)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |notes(com.example.linkvault.data.NoteEntity).
              | Expected:
              |""".trimMargin() + _infoNotes + """
              |
              | Found:
              |""".trimMargin() + _existingNotes)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    _shadowTablesMap.put("links_fts", "links")
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "links", "links_fts", "notes")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(LinkDao::class, LinkDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(NoteDao::class, NoteDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun linkDao(): LinkDao = _linkDao.value

  public override fun noteDao(): NoteDao = _noteDao.value
}
