import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    @SerialName("reading_log_entries")
    val data: List<ReadingLogEntriesItem>,

)
@Serializable
data class ReadingLogEntriesItem(
    val work: Work? = null,
)
@Serializable
data class Work(
    @SerialName("title")
    val title: String? = null,
    @SerialName("key")
    val key: String? = null,

    @SerialName("author_keys")
    val authorKeys: List<String> = emptyList(),
    @SerialName("author_names")
    val authorNames: List<String> = emptyList(),

    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null,

    @SerialName("lending_edition_s")
    val lendingEditionS: String? = null,

    @SerialName("edition_key")
    val editionKey: List<String> = emptyList(),

    @SerialName("cover_id")
    val coverId: Long? = null,

    @SerialName("cover_edition_key")
    val coverEditionKey: String? = null
)


