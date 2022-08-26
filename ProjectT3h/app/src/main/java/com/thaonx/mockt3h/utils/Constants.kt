package com.thaonx.mockt3h.utils

import android.annotation.SuppressLint
import org.ocpsoft.prettytime.PrettyTime
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val MIN_SCALE = 0.75f

    const val BASE_URL = "https://newsapi.org/v2/"
    const val BASE_URL_COVID = "https://akashraj.tech/"
    const val BASE_URL_WEATHER = "https://api.openweathermap.org/data/2.5/"
    const val BASE_URL_IMAGE = "https://openweathermap.org/img/w/"

    const val Q_HOME = "dịch covid"
    const val Q_LATEST = "mới nhất"
    const val Q_VIETNAM = "việt nam"
    const val Q_WORLD = "thế giới"
    const val Q_ENTERTAINMENT = "giải trí"
    const val Q_SPORT = "thể thao"

    const val SORT_BY = "publishedAt"
    const val UNITS = "metric"
    const val TIMES = 18
    const val API_KEY = "eb4ad6db55664fed859956dd12c3e9df"
    const val API_KEY_WEATHER = "8aa948f39a95d3ad675cde059b15d016"

    const val LATEST_NEWS = "Mới nhất"
    const val VIETNAM = "Việt Nam"
    const val WORLD = "Thế Giới"
    const val ENTERTAINMENT = "Giải trí"
    const val SPORT = "Thể thao"

    const val TABLE_NAME = "table_news"
    const val DATABASE_NAME = "news_database"
    const val ID_COLUMN = "id"
    const val NAME_COLUMN = "name"
    const val TITLE_COLUMN = "title"
    const val IMAGE_COLUMN = "image"
    const val DATE_COLUMN = "date"
    const val URL_COLUMN = "url"

    const val NOTIFY_DIALOG = "Xóa thành công"
    const val NOTIFY = "Đã lưu tin vào mục yêu thích"
    const val NOTIFY_LOGIN = "Bạn chưa nhập đủ thông tin"
    const val TITLE_FRAGMENT_HOME = "Tóm tắt thông tin cho "
    const val NOTIFY_LOCATION = "Cho phép xác định vị trí của bạn"
    const val NOTIFY_LOCATION_NOT_GRANTED = "Quyền vị trí chưa được cấp"

   private var newDate = ""

    @SuppressLint("SimpleDateFormat")
    fun dateFormat(stringDate: String): String {
        val sdf = SimpleDateFormat("E, d MMM yyy", Locale(getCountry()))
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(stringDate)

        if (date != null) {
            newDate = sdf.format(date)
        }
        return newDate
    }

    fun dateToTimeFormat(oldStringDate: String): String {
        val p = PrettyTime(Locale(getCountry()))
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.ENGLISH)
        val date = sdf.parse(oldStringDate)
        return "• ${p.format(date)}"
    }

    private fun getCountry(): String {
        val locale = Locale.getDefault()
        val country = locale.country.toString()
        return country.lowercase(Locale.getDefault())
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringDay(dt: Int): String {
        val time = dt.toString()

        val l: Long = (time.toLong())

        val date = Date(l * 1000L)

        val simpleDateFormat = SimpleDateFormat("EE\nHH-mm")
        val days = simpleDateFormat.format(date)
        return days.toString()
    }
}