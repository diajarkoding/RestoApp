import com.example.restoapp.model.Menu
import com.example.restoapp.model.Menus
import com.example.restoapp.model.Resto
import org.json.JSONObject

object RestoData {
    fun loadRestoData(jsonString: String): List<Resto> {
        val jsonObject = JSONObject(jsonString)
        val jsonArray = jsonObject.getJSONArray("restaurants")

        val restoList = mutableListOf<Resto>()

        for (i in 0 until jsonArray.length()) {
            val jsonResto = jsonArray.getJSONObject(i)
            val menus = jsonResto.getJSONObject("menus")
            val foods = menus.getJSONArray("foods").let { foodArray ->
                List(foodArray.length()) { Menu(foodArray.getJSONObject(it).getString("name")) }
            }
            val drinks = menus.getJSONArray("drinks").let { drinkArray ->
                List(drinkArray.length()) { Menu(drinkArray.getJSONObject(it).getString("name")) }
            }
            val resto = Resto(
                id = jsonResto.getString("id"),
                name = jsonResto.getString("name"),
                description = jsonResto.getString("description"),
                pictureId = jsonResto.getString("pictureId"),
                city = jsonResto.getString("city"),
                rating = jsonResto.getDouble("rating").toFloat(),
                menus = Menus(foods, drinks)
            )
            restoList.add(resto)
        }
        return restoList
    }
}
