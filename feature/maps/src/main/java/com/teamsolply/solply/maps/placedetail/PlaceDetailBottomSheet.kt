package com.teamsolply.solply.maps.placedetail

import ClickableAnnotatedText
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.teamsolply.solply.designsystem.component.card.SolplyCourseCard
import com.teamsolply.solply.designsystem.component.chip.PlaceTag
import com.teamsolply.solply.designsystem.theme.SolplyTheme
import com.teamsolply.solply.maps.model.CourseInfo
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.ui.extension.customClickable

@Composable
fun PlaceDetailBottomSheet(
    addPlace: Boolean,
    placeType: PlaceType,
    title: String,
    description: String,
    placeImageUrls: List<Int>,
    placeAddress: String,
    placeContactNumber: String,
    placeOpeningHours: String,
    placeSnsLick: String,
    courses: List<CourseInfo>,
    addMyCourseSelectedCount: List<Int>,
    addMyCourseBackClick: () -> Unit,
    selectedCourseForPlace: (Int) -> Unit,
    showMaxSizeCourseSnackBar: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
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
                Image(
                    painter = painterResource(placeImageUrls[page]),
                    contentDescription = "place-image-url",
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
                        ClickableAnnotatedText(
                            originalText = "$placeAddress $copyText",
                            originalTextStyle = SolplyTheme.typography.caption14M.copy(lineHeight = 15.sp),
                            targetText = copyText,
                            spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                            onClick = {
                                clipboardManager.setText(AnnotatedString(placeAddress))
                            }
                        )
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
                        ClickableAnnotatedText(
                            originalText = "$placeContactNumber $copyText",
                            originalTextStyle = SolplyTheme.typography.caption14M.copy(lineHeight = 15.sp),
                            targetText = copyText,
                            spanStyle = SpanStyle(textDecoration = TextDecoration.Underline),
                            onClick = {
                                clipboardManager.setText(AnnotatedString(placeAddress))
                            }
                        )
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
                            text = placeOpeningHours,
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
                        Text(
                            text = placeSnsLick,
                            color = SolplyTheme.colors.black,
                            style = SolplyTheme.typography.caption14M.copy(lineHeight = 15.sp)
                        )
                    }
                }
            }
        }
    }
}
