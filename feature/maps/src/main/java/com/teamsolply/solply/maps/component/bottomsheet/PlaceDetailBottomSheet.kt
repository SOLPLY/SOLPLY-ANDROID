package com.teamsolply.solply.maps.component.bottomsheet

import ClickableAnnotatedText
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.teamsolply.solply.designsystem.R
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.ImageInfo
import com.teamsolply.solply.maps.model.PlaceDetailEntity
import com.teamsolply.solply.maps.model.SnsLink
import com.teamsolply.solply.maps.util.navigateToNaverMapDirections
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage
import kotlinx.collections.immutable.PersistentList

@Composable
fun PlaceDetailBottomSheet(
    context: Context,
    placeBookmarked: Boolean,
    addPlace: Boolean,
    placeDetailEntity: PlaceDetailEntity,
    placeType: PlaceType,
    title: String,
    description: String,
    placeImageUrls: PersistentList<ImageInfo>,
    placeAddress: String,
    placeContactNumber: String,
    placeOpeningHours: String,
    placeSnsLink: PersistentList<SnsLink>,
    courses: PersistentList<CourseInfoEntity>,
    addMyCourseSelectedCount: Long?,
    addMyCourseBackClick: () -> Unit,
    selectedCourseForPlace: (Long) -> Unit,
    showMaxSizeCourseSnackBar: () -> Unit,
    showDuplicateSnackBar: () -> Unit,
    emptyCourseClick: () -> Unit,
    saveMyCourse: () -> Unit,
    changeAddPlaceState: (Boolean) -> Unit,
    placeBookMarkClick: () -> Unit
) {
    val copyText = "복사"
    val clipboardManager = LocalClipboardManager.current

    Box {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            if (addPlace) {
                Row(
                    modifier = Modifier.padding(bottom = 22.dp, start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back_long_arrow),
                        contentDescription = "back_arrow",
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .customClickable(rippleEnabled = false) {
                                addMyCourseBackClick()
                            }
                    )
                    Text(
                        text = "내 코스에 추가",
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.head16M
                    )
                }
                if (courses.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "저장한 코스가 없어요.",
                            modifier = Modifier.padding(bottom = 28.dp, top = 100.dp),
                            color = SolplyTheme.colors.gray800,
                            style = SolplyTheme.typography.body16M
                        )
                        SolplyBasicButton(
                            text = "나만의 코스 수집하러 가기",
                            onClick = {
                                emptyCourseClick()
                            },
                            modifier = Modifier.padding(horizontal = 64.dp),
                            textColor = SolplyTheme.colors.gray800,
                            textStyle = SolplyTheme.typography.button16M,
                            enabledBackgroundColor = SolplyTheme.colors.green300
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.spacedBy(11.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        contentPadding = PaddingValues(bottom = 200.dp)
                    ) {
                        items(courses) { courseInfo ->
                            SolplyCourseCard(
                                imgRes = courseInfo.thumbnailImage,
                                placeType = courseInfo.mainTags,
                                title = courseInfo.courseName,
                                savedPlace = !courseInfo.isActive,
                                savedCourse = courseInfo.isBookmarked,
                                selected = if (!courseInfo.isActive) {
                                    false
                                } else {
                                    addMyCourseSelectedCount == courseInfo.courseId
                                },
                                onClick = {
                                    if (courseInfo.isDuplicated) {
                                        showDuplicateSnackBar()
                                    } else if (courseInfo.isPlaceCountLimited) {
                                        showMaxSizeCourseSnackBar()
                                    } else {
                                        selectedCourseForPlace(courseInfo.courseId)
                                    }
                                }
                            )
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.padding(bottom = 8.dp, start = 20.dp, end = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PlaceTag(
                        type = placeType,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = title,
                        color = SolplyTheme.colors.black,
                        style = SolplyTheme.typography.title18Sb
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(if (placeBookmarked) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark_empty),
                        contentDescription = "place_bookmarked",
                        modifier = Modifier.customClickable(rippleEnabled = false) {
                            placeBookMarkClick()
                        },
                        tint = SolplyTheme.colors.gray900
                    )
                }
                Text(
                    text = description,
                    modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp),
                    color = SolplyTheme.colors.gray900,
                    maxLines = 2,
                    style = SolplyTheme.typography.caption14R
                )
                LazyColumn {
                    item {
                        Row(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 20.dp
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .background(
                                        color = SolplyTheme.colors.gray100,
                                        shape = CircleShape
                                    )
                                    .border(
                                        width = 1.dp,
                                        shape = CircleShape,
                                        color = SolplyTheme.colors.gray300
                                    )
                                    .padding(
                                        start = 16.dp,
                                        end = 12.dp,
                                        top = 11.dp,
                                        bottom = 11.dp
                                    )
                                    .customClickable(rippleEnabled = false) {
                                        navigateToNaverMapDirections(
                                            context = context,
                                            destName = placeDetailEntity.placeName,
                                            destId = placeDetailEntity.placeDefaultId.toString(),
                                            destLongitude = placeDetailEntity.longitude,
                                            destLatitude = placeDetailEntity.latitude,
                                            destType = placeDetailEntity.placeType
                                        )
                                    }
                            ) {
                                Text(
                                    text = "길찾기",
                                    color = SolplyTheme.colors.gray900,
                                    style = SolplyTheme.typography.button14M
                                )
                                Icon(
                                    painter = painterResource(R.drawable.ic_place_navigation),
                                    contentDescription = "place_navigation"
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .background(
                                        color = SolplyTheme.colors.gray900,
                                        shape = CircleShape
                                    )
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 11.dp
                                    )
                                    .customClickable(rippleEnabled = false) {
                                        changeAddPlaceState(true)
                                    }
                            ) {
                                Text(
                                    text = "내 코스에 추가",
                                    color = SolplyTheme.colors.white,
                                    style = SolplyTheme.typography.button14M
                                )
                            }
                        }
                        LazyRow(
                            modifier = Modifier.padding(
                                start = 16.dp,
                                bottom = 16.dp
                            ),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(end = 16.dp)
                        ) {
                            items(items = placeImageUrls) { item ->
                                AdaptationImage(
                                    imageUrl = item.url,
                                    modifier = Modifier
                                        .height(196.dp)
                                        .width(295.dp)
                                        .clip(RoundedCornerShape(12.dp)),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .border(
                                    width = 1.dp,
                                    color = SolplyTheme.colors.gray200,
                                    shape = RoundedCornerShape(20.dp)
                                )
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)
                            ) {
                                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = "주소",
                                        modifier = Modifier
                                            .width(80.dp)
                                            .padding(end = 10.dp),
                                        color = SolplyTheme.colors.black,
                                        style = SolplyTheme.typography.caption14M
                                    )
                                    if (placeAddress.isNotEmpty()) {
                                        ClickableAnnotatedText(
                                            originalText = "$placeAddress $copyText",
                                            originalTextStyle = SolplyTheme.typography.caption14R.copy(
                                                lineHeight = 15.sp
                                            ),
                                            targetText = copyText,
                                            spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                                            onClick = {
                                                clipboardManager.setText(
                                                    AnnotatedString(
                                                        placeAddress
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = "전화번호",
                                        modifier = Modifier
                                            .width(80.dp)
                                            .padding(end = 10.dp),
                                        color = SolplyTheme.colors.black,
                                        style = SolplyTheme.typography.caption14M
                                    )
                                    if (placeContactNumber.isNotEmpty()) {
                                        ClickableAnnotatedText(
                                            originalText = "$placeContactNumber $copyText",
                                            originalTextStyle = SolplyTheme.typography.caption14R.copy(
                                                lineHeight = 15.sp
                                            ),
                                            targetText = copyText,
                                            spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                                            onClick = {
                                                clipboardManager.setText(
                                                    AnnotatedString(
                                                        placeContactNumber
                                                    )
                                                )
                                            }
                                        )
                                    }
                                }
                                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = "운영시간",
                                        modifier = Modifier
                                            .width(80.dp)
                                            .padding(end = 10.dp),
                                        color = SolplyTheme.colors.black,
                                        style = SolplyTheme.typography.caption14M
                                    )
                                    Text(
                                        text = placeOpeningHours.replace("\\n", "\n"),
                                        color = SolplyTheme.colors.black,
                                        style = SolplyTheme.typography.caption14R.copy(lineHeight = 15.sp)
                                    )
                                }
                                Row(modifier = Modifier.padding(bottom = 8.dp)) {
                                    Text(
                                        text = "바로가기",
                                        modifier = Modifier
                                            .width(80.dp)
                                            .padding(end = 16.dp),
                                        color = SolplyTheme.colors.black,
                                        style = SolplyTheme.typography.caption14M
                                    )
                                    Column {
                                        placeSnsLink.forEach {
                                            Text(
                                                text = it.snsPlatform,
                                                modifier = Modifier.customClickable(rippleEnabled = false) {
                                                    val intent = Intent(
                                                        Intent.ACTION_VIEW,
                                                        it.url.toUri()
                                                    )
                                                    context.startActivity(intent)
                                                },
                                                color = SolplyTheme.colors.black,
                                                style = SolplyTheme.typography.caption14R.copy(
                                                    lineHeight = 15.sp,
                                                    textDecoration = TextDecoration.Underline
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }

        if (addMyCourseSelectedCount != null) {
            SolplyBasicButton(
                text = "이 코스에 추가할래요",
                onClick = {
                    saveMyCourse()
                    changeAddPlaceState(false)
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(start = 20.dp, end = 20.dp, bottom = 24.dp),
                textPadding = PaddingValues(vertical = 21.dp),
                enabledBackgroundColor = SolplyTheme.colors.gray900
            )
        }
    }
}
