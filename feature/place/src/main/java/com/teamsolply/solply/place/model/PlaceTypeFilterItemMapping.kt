import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceTypeFilterItem
import com.teamsolply.solply.place.model.TagEntity

fun TagEntity.toPlaceType(): PlaceType = runCatching { PlaceType.valueOf(this.name) }.getOrDefault(PlaceType.ALL)

fun PlaceType.toPlaceTypeFilterItem(): PlaceTypeFilterItem {
    return PlaceTypeFilterItem(
        type = this.name,
        label = this.displayName,
        iconRes = when (this) {
            PlaceType.CAFE -> com.teamsolply.solply.designsystem.R.drawable.ic_caffe
            PlaceType.FOOD -> com.teamsolply.solply.designsystem.R.drawable.ic_food
            PlaceType.WALKING -> com.teamsolply.solply.designsystem.R.drawable.ic_walk
            PlaceType.UNIQUE_SPACE -> com.teamsolply.solply.designsystem.R.drawable.ic_unique
            PlaceType.SHOPPING -> com.teamsolply.solply.designsystem.R.drawable.ic_shopping
            PlaceType.BOOKSTORE -> com.teamsolply.solply.designsystem.R.drawable.ic_book
            else -> com.teamsolply.solply.designsystem.R.drawable.ic_all
        }
    )
}