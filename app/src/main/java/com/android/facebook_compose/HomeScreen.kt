package com.android.facebook_compose

import android.text.format.DateUtils
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.facebook_compose.ui.theme.ButtonGray
import java.util.*


@Composable
fun HomeScreen(
    navigateToSignIn : () -> Unit
){

    val viewModel = viewModel<HomeScreenViewModel>()
    val state by viewModel.state.collectAsState()
    when (state) {
        is HomeScreenState.Loaded -> {
            val loaded = state as HomeScreenState.Loaded
            HomeScreenContents(
                posts = loaded.posts,
                loaded.avatarUrl,
                onTextChanged = {
                    viewModel.onTextChanged(it)
                },
                onSendClick = {
                    viewModel.onSendClick()
                }
            )
        }
        HomeScreenState.Loading -> LoadingScreen()
        HomeScreenState.SigninRequired -> LaunchedEffect(Unit){
            navigateToSignIn()
        }
    }


    
}

@Composable
fun LoadingScreen() {

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface),
        contentAlignment = Alignment.Center
    ) {

        CircularProgressIndicator()

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContents(
    posts: List<Post>,
    avatarUrl: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Box(
        Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
    ) {

        LazyColumn(
            contentPadding = PaddingValues(bottom = 40.dp)
        ) {

            item {
                TopAppBar()
            }

            stickyHeader {
                TabBar()
            }

            item {
                StatusUpdateBar(
                    avatarUrl = avatarUrl,
                    onTextChange = onTextChanged,
                    onSendClick = onSendClick
                )
            }

            item{
                Spacer(Modifier.height(16.dp))
            }

            item{
                StoriesSection(
                    avatarUrl = avatarUrl
                )
            }
            
            item{
                Spacer(Modifier.height(8.dp))
            }

            items(posts){ post ->
                Spacer(Modifier.height(8.dp))
                PostCard(post)
                Spacer(Modifier.height(8.dp))
            }

        }

    }
}

val friendsStories = listOf(

    FriendStory(
        friendName = "Josy Maleu",
        avatarUrl = "https://images.unsplash.com/photo-1645378999013-95abebf5f3c1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGJsYWNrJTIwYXZhdGFyc3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60",
        bgUrl = "https://plus.unsplash.com/premium_photo-1671730787538-d2132d4c1579?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8cHJldHR5fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=1200&q=60"
    ),
    FriendStory(
        friendName = "Julien Chontcha",
        avatarUrl = "https://images.unsplash.com/photo-1544197150-b99a580bb7a8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8bmV0d29ya2luZ3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60",
        bgUrl = "https://images.unsplash.com/photo-1630480329659-a588979675e8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTR8fHNoeXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60"
    ),
    FriendStory(
        friendName = "Brenda Deffo",
        avatarUrl = "https://images.unsplash.com/photo-1468818438311-4bab781ab9b8?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8VHJpcHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60",
        bgUrl = "https://images.unsplash.com/photo-1551847648-871b04de1de5?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MzJ8fHNpbmdsZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60"
    ),
    FriendStory(
        friendName = "Landry Kengmene",
        avatarUrl = "https://images.unsplash.com/photo-1584483456442-b0bfd23f20fb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTJ8fHNoeXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60",
        bgUrl = "https://images.unsplash.com/photo-1535957998253-26ae1ef29506?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mzl8fHdvcmslMjBzaHl8ZW58MHx8MHx8&auto=format&fit=crop&w=1200&q=60"
    ),
    FriendStory(
        friendName = "Oceane Letcheu",
        avatarUrl = "https://images.unsplash.com/photo-1619144340863-6326e20327de?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8c2h5fGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=1200&q=60",
        bgUrl = "https://images.unsplash.com/photo-1467164616789-ce7ae65ab4c9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTd8fHByZXR0eXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=1200&q=60"
    )

)

@Composable
fun StoriesSection(
    avatarUrl: String
) {
    Surface {
        LazyRow(
            Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 12.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item{
                CreateAStoryCard(
                    avatarUrl = avatarUrl
                )
            }
            
            items(friendsStories){ story ->
                StoryCard(story)
            }
        }
    }
}

@Composable
fun StoryCard(story: FriendStory) {
    Card(
        Modifier
            .size(148.dp, 220.dp)
    ) {
        Box(Modifier.fillMaxSize()){
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(story.bgUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(story.avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(36.dp)
                    .align(Alignment.TopStart)
                    .clip(CircleShape)
                    .border(
                        4.dp,
                        MaterialTheme.colors.primary,
                        CircleShape
                    )
            )
            Scrin(modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter)
            )
            Text(
                story.friendName,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            )
        }
    }
}

@Composable
fun Scrin(modifier: Modifier) {
    Box(modifier = modifier.background(Brush.verticalGradient(
        listOf(Color.Transparent, Color(0x40000000))
    )))
}

@Composable
fun CreateAStoryCard(
    avatarUrl: String
) {
    Card(
        Modifier
            .size(148.dp, 220.dp)
    ) {
        Box(
            Modifier
                .fillMaxSize()
        ){
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(avatarUrl)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
            )

            var bgHeight by remember {
                mutableStateOf(0.dp)
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.surface)
                    .height(bgHeight - 12.dp)
                    .align(Alignment.BottomCenter)
            )

            val density = LocalDensity.current

            Column(
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(
                        vertical = 8.dp,
                        horizontal = 24.dp
                    )
                    .onGloballyPositioned {
                        bgHeight = with(density) {
                            it.size.height.toDp()
                        }
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    Modifier
                        .size(36.dp)
                        .border(
                            2.dp,
                            MaterialTheme.colors.surface,
                            CircleShape
                        )
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = stringResource(id = R.string.add),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
                Spacer(Modifier.height(4.dp))
                Text(
                    stringResource(id = R.string.create_a_story),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PostCard(post: Post) {
    Surface {

        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(post.authorAvatarUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )
                Column(
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = post.authorName,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Medium)
                    )
                    val today = remember {
                        Date()
                    }
                    Text(
                        dateLabel(
                            timestamp = post.timestamp,
                            today = today
                        ),
                        color = MaterialTheme.colors.onSurface.copy(
                            alpha = 0.44f
                        )
                    )
                }
                IconButton(onClick = { }) {
                    Icon(
                        Icons.Rounded.MoreHoriz,
                        contentDescription = stringResource(R.string.menu)
                    )
                }
            }
            Text(
                text = post.text,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Medium
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Divider(thickness = Dp.Hairline)

            Row (Modifier.fillMaxWidth()) {

                StatusAction(
                    Icons.Rounded.ThumbUp,
                    stringResource(id = R.string.like),
                    Modifier.weight(1f)
                )

                VerticalDevider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )

                StatusAction(
                    Icons.Rounded.Comment,
                    stringResource(id = R.string.comment),
                    Modifier.weight(1f)
                )

                VerticalDevider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )

                StatusAction(
                    Icons.Rounded.Share,
                    stringResource(id = R.string.share),
                    Modifier.weight(1f)
                )

            }
        }
    }
}

@Composable
private fun dateLabel(
    timestamp: Date,
    today: Date
) : String {
    return if (today.time - timestamp.time < 2 * DateUtils.MINUTE_IN_MILLIS){
        stringResource(id = R.string.just_now)
    } else {
        DateUtils.getRelativeTimeSpanString(
            timestamp.time,
            today.time,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_SHOW_DATE
        ).toString()
    }
}

@Composable
private fun TopAppBar() {

    Surface {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.h6
            )

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = { },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ButtonGray)
            ) {

                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )

            }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ButtonGray)
            ) {

                Icon(
                    Icons.Default.ChatBubble,
                    contentDescription = stringResource(R.string.search)
                )

            }

        }
    }

}

data class TabItem(
    val icon : ImageVector,
    val contentDescription: String
)

@Composable
fun TabBar() {

    Surface {
        var tabIndex by remember {
            mutableStateOf(0)
        }

        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary
        ) {

            val tabs = listOf(
                TabItem(Icons.Rounded.Home, stringResource(R.string.home)),
                TabItem(Icons.Rounded.Tv, stringResource(R.string.reels)),
                TabItem(Icons.Rounded.Store, stringResource(R.string.marketplace)),
                TabItem(Icons.Rounded.Newspaper, stringResource(R.string.news)),
                TabItem(Icons.Rounded.Notifications, stringResource(R.string.notifications)),
                TabItem(Icons.Rounded.Menu, stringResource(R.string.menu)),
            )

            tabs.forEachIndexed{ i, item ->
                Tab(
                    selected = tabIndex == i,
                    onClick = { tabIndex = i },
                    selectedContentColor = MaterialTheme.colors.primary,
                    modifier = Modifier.height(48.dp)
                ) {

                    Icon(
                        item.icon,
                        contentDescription = item.contentDescription,
                        tint = if (i == tabIndex) {
                            MaterialTheme.colors.primary
                        }else{
                            MaterialTheme.colors.onSurface.copy(alpha = 0.44f)
                        }
                    )

                }
            }
        }
    }

}

@Composable
fun StatusUpdateBar(
    avatarUrl: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit
) {

    Surface{

        Column {

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(avatarUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.ic_placeholder)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(8.dp))

                var text by remember {
                    mutableStateOf("")
                }

                TextField(
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = {
                        text = it
                        onTextChange(it)
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.whats_on_your_mind))
                    },
                    keyboardActions = KeyboardActions(
                        onSend = {
                            onSendClick()
                            text = ""
                        }
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    )
                )

            }

            Divider(thickness = Dp.Hairline)

            Row (Modifier.fillMaxWidth()) {

                StatusAction(
                    Icons.Rounded.VideoCall,
                    stringResource(id = R.string.live),
                    Modifier.weight(1f)
                )

                VerticalDevider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )

                StatusAction(
                    Icons.Rounded.PhotoAlbum,
                    stringResource(id = R.string.photo),
                    Modifier.weight(1f)
                )

                VerticalDevider(
                    Modifier.height(48.dp),
                    thickness = Dp.Hairline
                )

                StatusAction(
                    Icons.Rounded.ChatBubble,
                    stringResource(id = R.string.discuss),
                    Modifier.weight(1f)
                )

            }

        }

    }
    
}

@Composable
private fun StatusAction(
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier,
) {

    TextButton(
        modifier = modifier,
        onClick = { },
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colors.onSurface
        )
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {

            Icon(
                icon,
                contentDescription = text
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(text)

        }

    }

}

@Composable
fun VerticalDevider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
    topIndent: Dp = 0.dp
) {

    val indentMod = if (topIndent.value != 0f) {
        Modifier.padding(top = topIndent)
    } else {
        Modifier
    }

    val targetThickness = if (thickness == Dp.Hairline) {
        (1f / LocalDensity.current.density).dp
    } else {
        thickness
    }

    // TODO see what it does not work without specifying height()

    Box(
        modifier
            .then(indentMod)
            .fillMaxHeight()
            .width(targetThickness)
            .background(color = color)

    )
}