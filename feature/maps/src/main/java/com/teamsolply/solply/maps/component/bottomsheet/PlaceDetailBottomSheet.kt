package com.teamsolply.solply.maps.component.bottomsheet

import ClickableAnnotatedText
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.teamsolply.solply.designsystem.component.button.SolplyBasicButton
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.model.CourseInfoEntity
import com.teamsolply.solply.maps.model.ImageInfo
import com.teamsolply.solply.maps.model.SnsLink
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable
import com.teamsolply.solply.ui.image.AdaptationImage
import kotlinx.collections.immutable.PersistentList

@Composable
fun PlaceDetailBottomSheet(
    addPlace: Boolean,
    placeType: PlaceType,
    title: String,
    description: String,
    placeImageUrls: PersistentList<ImageInfo>,
    placeAddress: String,
    placeContactNumber: String,
    placeOpeningHours: String,
    placeSnsLink: PersistentList<SnsLink>,
    courses: PersistentList<CourseInfoEntity>,
    addMyCourseSelectedCount: PersistentList<Int>,
    addMyCourseBackClick: () -> Unit,
    selectedCourseForPlace: (Int) -> Unit,
    showMaxSizeCourseSnackBar: () -> Unit,
    emptyCourseClick: () -> Unit
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = {
        placeImageUrls.size
    })
    val copyText = "복사"
    val clipboardManager = LocalClipboardManager.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        if (addPlace) {
            Row(
                modifier = Modifier.padding(bottom = 21.dp, start = 20.dp, end = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(com.teamsolply.solply.designsystem.R.drawable.ic_back_long_arrow),
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
                            placeType = courseInfo.mainTag,
                            backgroundColor = SolplyTheme.colors.green300,
                            iconColor = SolplyTheme.colors.green400,
                            iconBackGroundColor = SolplyTheme.colors.green200,
                            title = courseInfo.title,
                            savedPlace = courseInfo.placeCount < 6,
                            selected = addMyCourseSelectedCount.contains(courseInfo.courseId),
                            onClick = {
                                if (courseInfo.placeCount < 6) {
                                    selectedCourseForPlace(courseInfo.courseId)
                                } else {
                                    showMaxSizeCourseSnackBar()
                                }
                            }
                        )
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier.padding(bottom = 6.dp, start = 20.dp, end = 20.dp),
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
            }
            Text(
                text = description,
                modifier = Modifier.padding(bottom = 16.dp, start = 20.dp, end = 20.dp),
                color = SolplyTheme.colors.gray900,
                maxLines = 2,
                style = SolplyTheme.typography.caption14R
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
                contentPadding = PaddingValues(horizontal = 20.dp),
                pageSpacing = 10.dp
            ) { page ->
                AdaptationImage(
                    imageUrl = placeImageUrls[page].url,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                )
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
                                originalTextStyle = SolplyTheme.typography.caption14M.copy(
                                    lineHeight = 15.sp
                                ),
                                targetText = copyText,
                                spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(placeAddress))
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
                                originalTextStyle = SolplyTheme.typography.caption14M.copy(
                                    lineHeight = 15.sp
                                ),
                                targetText = copyText,
                                spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                                onClick = {
                                    clipboardManager.setText(AnnotatedString(placeContactNumber))
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
                            style = SolplyTheme.typography.caption14M.copy(lineHeight = 15.sp)
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
                                    color = SolplyTheme.colors.black,
                                    style = SolplyTheme.typography.caption14M.copy(lineHeight = 15.sp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
