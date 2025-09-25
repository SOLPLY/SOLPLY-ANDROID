import com.teamsolply.solply.designsystem.mapper.iconRes
import com.teamsolply.solply.model.PlaceType
import com.teamsolply.solply.place.model.PlaceTypeFilterItem
import com.teamsolply.solply.place.model.TagEntity

fun TagEntity.toPlaceType(): PlaceType =
    runCatching { PlaceType.valueOf(this.name) }.getOrDefault(PlaceType.ALL)

fun PlaceType.toPlaceTypeFilterItem(): PlaceTypeFilterItem {
    return PlaceTypeFilterItem(
        type = this.name,
        label = this.displayName,
        iconRes = this.iconRes()
    )
}
