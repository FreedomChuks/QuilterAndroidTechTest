package com.freedom.book_list.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.freedom.book_list.model.BookItemUi
import com.freedom.book_list.model.BooksUi
import com.freedom.designsystem.theme.QuilterAndroidTechTestTheme

@Composable
fun BookItem(
    bookItemUi: BookItemUi,
    onBookClick: (BookItemUi) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { onBookClick(bookItemUi) },
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            BookCover(
                coverUrl = bookItemUi.coverUrl,
                contentDescription = bookItemUi.title,
            )
            Spacer(Modifier.width(12.dp))
            BookDetails(
                title = bookItemUi.title,
                author = bookItemUi.authorsName,
                year = bookItemUi.firstPublishYear,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun BookCover(
    coverUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp
) {
    AsyncImage(
        model = coverUrl,
        contentDescription = contentDescription,
        placeholder = painterResource(android.R.drawable.ic_menu_gallery),
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun BookDetails(
    modifier: Modifier = Modifier,
    title: String?,
    author: String?,
    year: Int? = null,
) {
    Column(modifier = modifier) {
        Text(
            text = title ?: "-",
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = author ?: "-",
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (year != null && year > 0) {
            Spacer(Modifier.height(4.dp))
            Text(
                text = year.toString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun BookItemPreview() {
    QuilterAndroidTechTestTheme {
        BookItem(
            bookItemUi = previewBooksData.books[0],
            onBookClick = {})
    }
}

@Composable
internal fun DetailsBottomSheetContent(
    book: BookItemUi,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BookCover(
            coverUrl = book.coverUrl,
            contentDescription = book.title,
            size = 220.dp
        )
        Spacer(Modifier.height(16.dp))
        BookDetails(
            title = book.title,
            author = book.authorsName,
            year = book.firstPublishYear,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
val previewBooksData: BooksUi = BooksUi(books = listOf(
    BookItemUi(
        title = "Analytic geometry and calculus",
        authorsName = "Thurman S. Peterson",
        firstPublishYear = 1955,
        coverUrl = "https://covers.openlibrary.org/b/OLID/OL58556693M-L.jpg"
    )
))
