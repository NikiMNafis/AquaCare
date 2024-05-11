package com.capstone.aquacare.fuzzy

import android.content.Context
import com.capstone.aquacare.R
import com.capstone.aquacare.data.StatusParameter

class FuzzyDutchStyle(private val context: Context) {

    private val good = context.getString(R.string.good)
    private val medium = context.getString(R.string.medium)
    private val bad = context.getString(R.string.bad)

    fun calculateWaterQuality(temperature: Double, ph: Double, ammonia: Double, kh: Double, gh: Double): String {

        val goodTemperature = fungsiKeanggotaanSuhuBaik(temperature)
        val badTemperature = fungsiKeanggotaanSuhuBuruk(temperature)

        val goodPh = fungsiKeanggotaanPhBaik(ph)
        val mediumPh = fungsiKeanggotaanPhSedang(ph)
        val badPh = fungsiKeanggotaanPhBuruk(ph)

        val goodAmmonia = fungsiKeanggotaanAmmoniaBaik(ammonia)
        val badAmmonia = fungsiKeanggotaanAmmoniaBuruk(ammonia)

        val goodKh = fungsiKeanggotaanKhBaik(kh)
        val mediumKh = fungsiKeanggotaanKhSedang(kh)
        val badKh = fungsiKeanggotaanKhBuruk(kh)

        val goodGh = fungsiKeanggotaanGhBaik(gh)
        val badGh = fungsiKeanggotaanGhBuruk(gh)

        val inferensi1 = minOf(goodTemperature, goodPh, goodAmmonia, goodKh, goodGh)
        val inferensi2 = minOf(goodTemperature, mediumPh, goodAmmonia, goodKh, goodGh)
        val inferensi3 = minOf(goodTemperature, badPh, goodAmmonia, goodKh, goodGh)
        val inferensi4 = minOf(goodTemperature, goodPh, badAmmonia, goodKh, goodGh)
        val inferensi5 = minOf(goodTemperature, goodPh, goodAmmonia, mediumKh, goodGh)
        val inferensi6 = minOf(goodTemperature, goodPh, goodAmmonia, badKh, goodGh)
        val inferensi7 = minOf(goodTemperature, goodPh, goodAmmonia, goodKh, badGh)

        val inferensi8 = minOf(goodTemperature, mediumPh, badAmmonia, goodKh, goodGh)
        val inferensi9 = minOf(goodTemperature, mediumPh, badAmmonia, mediumKh, goodGh)
        val inferensi10 = minOf(goodTemperature, mediumPh, badAmmonia, badKh, goodGh)
        val inferensi11 = minOf(goodTemperature, mediumPh, badAmmonia, goodKh, badGh)
        val inferensi12 = minOf(goodTemperature, mediumPh, badAmmonia, mediumKh, badGh)
        val inferensi13 = minOf(goodTemperature, mediumPh, badAmmonia, badKh, badGh)
        val inferensi14 = minOf(goodTemperature, mediumPh, goodAmmonia, mediumKh, goodGh)
        val inferensi15 = minOf(goodTemperature, mediumPh, goodAmmonia, badKh, goodGh)
        val inferensi16 = minOf(goodTemperature, mediumPh, goodAmmonia, goodKh, badGh)
        val inferensi17 = minOf(goodTemperature, mediumPh, goodAmmonia, mediumKh, badGh)
        val inferensi18 = minOf(goodTemperature, mediumPh, goodAmmonia, badKh, badGh)

        val inferensi19 = minOf(goodTemperature, badPh, badAmmonia, goodKh, goodGh)
        val inferensi20 = minOf(goodTemperature, badPh, badAmmonia, mediumKh, goodGh)
        val inferensi21 = minOf(goodTemperature, badPh, badAmmonia, badKh, goodGh)
        val inferensi22 = minOf(goodTemperature, badPh, badAmmonia, goodKh, badGh)
        val inferensi23 = minOf(goodTemperature, badPh, badAmmonia, mediumKh, badGh)
        val inferensi24 = minOf(goodTemperature, badPh, badAmmonia, badKh, badGh)
        val inferensi25 = minOf(goodTemperature, badPh, goodAmmonia, mediumKh, goodGh)
        val inferensi26 = minOf(goodTemperature, badPh, goodAmmonia, badKh, goodGh)
        val inferensi27 = minOf(goodTemperature, badPh, goodAmmonia, goodKh, badGh)
        val inferensi28 = minOf(goodTemperature, badPh, goodAmmonia, mediumKh, badGh)
        val inferensi29 = minOf(goodTemperature, badPh, goodAmmonia, badKh, badGh)

        val inferensi30 = minOf(goodTemperature, goodPh, badAmmonia, mediumKh, goodGh)
        val inferensi31 = minOf(goodTemperature, goodPh, badAmmonia, badKh, goodGh)
        val inferensi32 = minOf(goodTemperature, goodPh, badAmmonia, goodKh, badGh)
        val inferensi33 = minOf(goodTemperature, goodPh, badAmmonia, mediumKh, badGh)
        val inferensi34 = minOf(goodTemperature, goodPh, badAmmonia, badKh, badGh)
        val inferensi35 = minOf(goodTemperature, goodPh, goodAmmonia, mediumKh, badGh)
        val inferensi36 = minOf(goodTemperature, goodPh, goodAmmonia, badKh, badGh)

        val inferensi37 = minOf(badTemperature, mediumPh, badAmmonia, goodKh, goodGh)
        val inferensi38 = minOf(badTemperature, mediumPh, badAmmonia, mediumKh, goodGh)
        val inferensi39 = minOf(badTemperature, mediumPh, badAmmonia, badKh, goodGh)
        val inferensi40 = minOf(badTemperature, mediumPh, badAmmonia, goodKh, badGh)
        val inferensi41 = minOf(badTemperature, mediumPh, badAmmonia, mediumKh, badGh)
        val inferensi42 = minOf(badTemperature, mediumPh, badAmmonia, badKh, badGh)
        val inferensi43 = minOf(badTemperature, mediumPh, goodAmmonia, goodKh, goodGh)
        val inferensi44 = minOf(badTemperature, mediumPh, goodAmmonia, mediumKh, goodGh)
        val inferensi45 = minOf(badTemperature, mediumPh, goodAmmonia, badKh, goodGh)
        val inferensi46 = minOf(badTemperature, mediumPh, goodAmmonia, goodKh, badGh)
        val inferensi47 = minOf(badTemperature, mediumPh, goodAmmonia, mediumKh, badGh)
        val inferensi48 = minOf(badTemperature, mediumPh, goodAmmonia, badKh, badGh)

        val inferensi49 = minOf(badTemperature, badPh, badAmmonia, goodKh, goodGh)
        val inferensi50 = minOf(badTemperature, badPh, badAmmonia, mediumKh, goodGh)
        val inferensi51 = minOf(badTemperature, badPh, badAmmonia, badKh, goodGh)
        val inferensi52 = minOf(badTemperature, badPh, badAmmonia, goodKh, badGh)
        val inferensi53 = minOf(badTemperature, badPh, badAmmonia, mediumKh, badGh)
        val inferensi54 = minOf(badTemperature, badPh, badAmmonia, badKh, badGh)
        val inferensi55 = minOf(badTemperature, badPh, goodAmmonia, goodKh, goodGh)
        val inferensi56 = minOf(badTemperature, badPh, goodAmmonia, mediumKh, goodGh)
        val inferensi57 = minOf(badTemperature, badPh, goodAmmonia, badKh, goodGh)
        val inferensi58 = minOf(badTemperature, badPh, goodAmmonia, goodKh, badGh)
        val inferensi59 = minOf(badTemperature, badPh, goodAmmonia, mediumKh, badGh)
        val inferensi60 = minOf(badTemperature, badPh, goodAmmonia, badKh, badGh)

        val inferensi61 = minOf(badTemperature, goodPh, badAmmonia, mediumKh, goodGh)
        val inferensi62 = minOf(badTemperature, goodPh, badAmmonia, badKh, goodGh)
        val inferensi63 = minOf(badTemperature, goodPh, badAmmonia, goodKh, goodGh)
        val inferensi64 = minOf(badTemperature, goodPh, badAmmonia, goodKh, badGh)
        val inferensi65 = minOf(badTemperature, goodPh, badAmmonia, mediumKh, badGh)
        val inferensi66 = minOf(badTemperature, goodPh, badAmmonia, badKh, badGh)
        val inferensi67 = minOf(badTemperature, goodPh, goodAmmonia, mediumKh, goodGh)
        val inferensi68 = minOf(badTemperature, goodPh, goodAmmonia, badKh, goodGh)
        val inferensi69 = minOf(badTemperature, goodPh, goodAmmonia, goodKh, badGh)
        val inferensi70 = minOf(badTemperature, goodPh, goodAmmonia, mediumKh, badGh)
        val inferensi71 = minOf(badTemperature, goodPh, goodAmmonia, badKh, badGh)

        val hasilInferensi1 = fungsiKeanggotaanStatusBaik(inferensi1)
        val hasilInferensi2 = fungsiKeanggotaanStatusBaik(inferensi2)
        val hasilInferensi3 = fungsiKeanggotaanStatusSedang(inferensi3)
        val hasilInferensi4 = fungsiKeanggotaanStatusBuruk(inferensi4)
        val hasilInferensi5 = fungsiKeanggotaanStatusSedang(inferensi5)
        val hasilInferensi6 = fungsiKeanggotaanStatusBuruk(inferensi6)
        val hasilInferensi7 = fungsiKeanggotaanStatusSedang(inferensi7)

        val hasilInferensi8 = fungsiKeanggotaanStatusBuruk(inferensi8)
        val hasilInferensi9 = fungsiKeanggotaanStatusBuruk(inferensi9)
        val hasilInferensi10 = fungsiKeanggotaanStatusBuruk(inferensi10)
        val hasilInferensi11 = fungsiKeanggotaanStatusBuruk(inferensi11)
        val hasilInferensi12 = fungsiKeanggotaanStatusBuruk(inferensi12)
        val hasilInferensi13 = fungsiKeanggotaanStatusBuruk(inferensi13)
        val hasilInferensi14 = fungsiKeanggotaanStatusSedang(inferensi14)
        val hasilInferensi15 = fungsiKeanggotaanStatusBuruk(inferensi15)
        val hasilInferensi16 = fungsiKeanggotaanStatusSedang(inferensi16)
        val hasilInferensi17 = fungsiKeanggotaanStatusBuruk(inferensi17)
        val hasilInferensi18 = fungsiKeanggotaanStatusBuruk(inferensi18)

        val hasilInferensi19 = fungsiKeanggotaanStatusBuruk(inferensi19)
        val hasilInferensi20 = fungsiKeanggotaanStatusBuruk(inferensi20)
        val hasilInferensi21 = fungsiKeanggotaanStatusBuruk(inferensi21)
        val hasilInferensi22 = fungsiKeanggotaanStatusBuruk(inferensi22)
        val hasilInferensi23 = fungsiKeanggotaanStatusBuruk(inferensi23)
        val hasilInferensi24 = fungsiKeanggotaanStatusBuruk(inferensi24)
        val hasilInferensi25 = fungsiKeanggotaanStatusSedang(inferensi25)
        val hasilInferensi26 = fungsiKeanggotaanStatusBuruk(inferensi26)
        val hasilInferensi27 = fungsiKeanggotaanStatusSedang(inferensi27)
        val hasilInferensi28 = fungsiKeanggotaanStatusSedang(inferensi28)
        val hasilInferensi29 = fungsiKeanggotaanStatusBuruk(inferensi29)

        val hasilInferensi30 = fungsiKeanggotaanStatusBuruk(inferensi30)
        val hasilInferensi31 = fungsiKeanggotaanStatusBuruk(inferensi31)
        val hasilInferensi32 = fungsiKeanggotaanStatusBuruk(inferensi32)
        val hasilInferensi33 = fungsiKeanggotaanStatusBuruk(inferensi33)
        val hasilInferensi34 = fungsiKeanggotaanStatusBuruk(inferensi34)
        val hasilInferensi35 = fungsiKeanggotaanStatusSedang(inferensi35)
        val hasilInferensi36 = fungsiKeanggotaanStatusBuruk(inferensi36)

        val hasilInferensi37 = fungsiKeanggotaanStatusBuruk(inferensi37)
        val hasilInferensi38 = fungsiKeanggotaanStatusBuruk(inferensi38)
        val hasilInferensi39 = fungsiKeanggotaanStatusBuruk(inferensi39)
        val hasilInferensi40 = fungsiKeanggotaanStatusBuruk(inferensi40)
        val hasilInferensi41 = fungsiKeanggotaanStatusBuruk(inferensi41)
        val hasilInferensi42 = fungsiKeanggotaanStatusBuruk(inferensi42)
        val hasilInferensi43 = fungsiKeanggotaanStatusSedang(inferensi43)
        val hasilInferensi44 = fungsiKeanggotaanStatusSedang(inferensi44)
        val hasilInferensi45 = fungsiKeanggotaanStatusBuruk(inferensi45)
        val hasilInferensi46 = fungsiKeanggotaanStatusSedang(inferensi46)
        val hasilInferensi47 = fungsiKeanggotaanStatusBuruk(inferensi47)
        val hasilInferensi48 = fungsiKeanggotaanStatusBuruk(inferensi48)

        val hasilInferensi49 = fungsiKeanggotaanStatusBuruk(inferensi49)
        val hasilInferensi50 = fungsiKeanggotaanStatusBuruk(inferensi50)
        val hasilInferensi51 = fungsiKeanggotaanStatusBuruk(inferensi51)
        val hasilInferensi52 = fungsiKeanggotaanStatusBuruk(inferensi52)
        val hasilInferensi53 = fungsiKeanggotaanStatusBuruk(inferensi53)
        val hasilInferensi54 = fungsiKeanggotaanStatusBuruk(inferensi54)
        val hasilInferensi55 = fungsiKeanggotaanStatusSedang(inferensi55)
        val hasilInferensi56 = fungsiKeanggotaanStatusBuruk(inferensi56)
        val hasilInferensi57 = fungsiKeanggotaanStatusBuruk(inferensi57)
        val hasilInferensi58 = fungsiKeanggotaanStatusBuruk(inferensi58)
        val hasilInferensi59 = fungsiKeanggotaanStatusBuruk(inferensi59)
        val hasilInferensi60 = fungsiKeanggotaanStatusBuruk(inferensi60)

        val hasilInferensi61 = fungsiKeanggotaanStatusBuruk(inferensi61)
        val hasilInferensi62 = fungsiKeanggotaanStatusBuruk(inferensi62)
        val hasilInferensi63 = fungsiKeanggotaanStatusBuruk(inferensi63)
        val hasilInferensi64 = fungsiKeanggotaanStatusBuruk(inferensi64)
        val hasilInferensi65 = fungsiKeanggotaanStatusBuruk(inferensi65)
        val hasilInferensi66 = fungsiKeanggotaanStatusBuruk(inferensi66)
        val hasilInferensi67 = fungsiKeanggotaanStatusSedang(inferensi67)
        val hasilInferensi68 = fungsiKeanggotaanStatusBuruk(inferensi68)
        val hasilInferensi69 = fungsiKeanggotaanStatusSedang(inferensi69)
        val hasilInferensi70 = fungsiKeanggotaanStatusSedang(inferensi70)
        val hasilInferensi71 = fungsiKeanggotaanStatusBuruk(inferensi71)

        val defuzzifikasi1 = ((inferensi1 * hasilInferensi1) + (inferensi2 * hasilInferensi2) + (inferensi3 * hasilInferensi3) + (inferensi4 * hasilInferensi4) + (inferensi5 * hasilInferensi5) + (inferensi6 * hasilInferensi6)  + (inferensi7 * hasilInferensi7)  + (inferensi8 * hasilInferensi8)  + (inferensi9 * hasilInferensi9)  + (inferensi10 * hasilInferensi10) + (inferensi11 * hasilInferensi11)  + (inferensi12 * hasilInferensi12) + (inferensi13 * hasilInferensi13) + (inferensi14 * hasilInferensi14) + (inferensi15 * hasilInferensi15))
        val defuzzifikasi2 = ((inferensi16 * hasilInferensi16) + (inferensi17 * hasilInferensi17) + (inferensi18 * hasilInferensi18) + (inferensi19 * hasilInferensi19) + (inferensi20 * hasilInferensi20) + (inferensi21 * hasilInferensi21) + (inferensi22 * hasilInferensi22) + (inferensi23 * hasilInferensi23) + (inferensi24 * hasilInferensi24) + (inferensi25 * hasilInferensi25) + (inferensi26 * hasilInferensi26) + (inferensi27 * hasilInferensi27) + (inferensi28 * hasilInferensi28) + (inferensi29 * hasilInferensi29) + (inferensi30 * hasilInferensi30))
        val defuzzifikasi3 = ((inferensi31 * hasilInferensi31) + (inferensi32 * hasilInferensi32) + (inferensi33 * hasilInferensi33) + (inferensi34 * hasilInferensi34) + (inferensi35 * hasilInferensi35) + (inferensi36 * hasilInferensi36)  + (inferensi37 * hasilInferensi37)  + (inferensi38 * hasilInferensi38)  + (inferensi39 * hasilInferensi39)  + (inferensi40 * hasilInferensi40) + (inferensi41 * hasilInferensi41)  + (inferensi42 * hasilInferensi42) + (inferensi43 * hasilInferensi43) + (inferensi44 * hasilInferensi44) + (inferensi45 * hasilInferensi45))
        val defuzzifikasi4 = ((inferensi46 * hasilInferensi46) + (inferensi47 * hasilInferensi47) + (inferensi48 * hasilInferensi48) + (inferensi49 * hasilInferensi49) + (inferensi50 * hasilInferensi50) + (inferensi51 * hasilInferensi51) + (inferensi52 * hasilInferensi52) + (inferensi53 * hasilInferensi53) + (inferensi54 * hasilInferensi54) + (inferensi55 * hasilInferensi55) + (inferensi56 * hasilInferensi56) + (inferensi57 * hasilInferensi57) + (inferensi58 * hasilInferensi58) + (inferensi59 * hasilInferensi59) + (inferensi60 * hasilInferensi60))
        val defuzzifikasi5 = ((inferensi61 * hasilInferensi61) + (inferensi62 * hasilInferensi62) + (inferensi63 * hasilInferensi63) + (inferensi64 * hasilInferensi64) + (inferensi65 * hasilInferensi65) + (inferensi66 * hasilInferensi66)  + (inferensi67 * hasilInferensi67)  + (inferensi68 * hasilInferensi68)  + (inferensi69 * hasilInferensi69)  + (inferensi70 * hasilInferensi70) + (inferensi71 * hasilInferensi71))

        val defuzzifikasiA = inferensi1 + inferensi2 + inferensi3 + inferensi4 + inferensi5 + inferensi6 + inferensi7 + inferensi8 + inferensi9 + inferensi10 + inferensi11 + inferensi12 + inferensi13 + inferensi14 + inferensi15 + inferensi16 + inferensi17 + inferensi18 + inferensi19 + inferensi20
        val defuzzifikasiB = inferensi21 + inferensi22 + inferensi23 + inferensi24 + inferensi25 + inferensi26 + inferensi27 + inferensi28 + inferensi29 + inferensi30 + inferensi31 + inferensi32 + inferensi33 + inferensi34 + inferensi35 + inferensi36 + inferensi37 + inferensi38 + inferensi39 + inferensi40
        val defuzzifikasiC = inferensi41 + inferensi42 + inferensi43 + inferensi44 + inferensi45 + inferensi46 + inferensi47 + inferensi48 + inferensi49 + inferensi50 + inferensi51 + inferensi52 + inferensi53 + inferensi54 + inferensi55 + inferensi56 + inferensi57 + inferensi58 + inferensi59 + inferensi60
        val defuzzifikasiD = inferensi61 + inferensi62 + inferensi63 + inferensi64 + inferensi65 + inferensi66 + inferensi67 + inferensi68 + inferensi69 + inferensi70 + inferensi71

        val totalDefuzzifikasi1 = defuzzifikasi1 + defuzzifikasi2 + defuzzifikasi3 + defuzzifikasi4 + defuzzifikasi5
        val totalDefuzzifikasi2 = defuzzifikasiA + defuzzifikasiB + defuzzifikasiC + defuzzifikasiD

        val defuzzifikasi = totalDefuzzifikasi1 / totalDefuzzifikasi2

        val resultDefuzzifikasi = if (defuzzifikasi >= 70) {
            good
        } else if (defuzzifikasi <= 30){
            bad
        } else {
            medium
        }

        return resultDefuzzifikasi
    }

    fun checkParameter(suhu: Double, ph: Double, ammonia: Double, kh: Double, gh: Double): StatusParameter {
        val suhuBaik = fungsiKeanggotaanSuhuBaik(suhu)
        val suhuBuruk = fungsiKeanggotaanSuhuBuruk(suhu)

        val phBaik = fungsiKeanggotaanPhBaik(ph)
        val phSedang = fungsiKeanggotaanPhSedang(ph)
        val phBuruk = fungsiKeanggotaanPhBuruk(ph)

        val ammoniaBaik = fungsiKeanggotaanAmmoniaBaik(ammonia)
        val ammoniaBuruk = fungsiKeanggotaanAmmoniaBuruk(ammonia)

        val khBaik = fungsiKeanggotaanKhBaik(kh)
        val khSedang = fungsiKeanggotaanKhSedang(kh)
        val khBuruk = fungsiKeanggotaanKhBuruk(kh)

        val ghBaik = fungsiKeanggotaanGhBaik(gh)
        val ghBuruk = fungsiKeanggotaanGhBuruk(gh)

        val _temperature = if (suhuBaik >= suhuBuruk) {
            good
        } else {
            bad
        }

        val _ph = if (phBaik >= phSedang && phBaik >= phBuruk) {
            good
        } else if (phSedang >= phBaik && phSedang >= phBuruk) {
            medium
        } else {
            bad
        }

        val _ammonia = if (ammoniaBaik >= ammoniaBuruk) {
            good
        } else {
            bad
        }

        val _kh = if (khBaik >= khSedang && khBaik >= khBuruk) {
            good
        } else if (khSedang >= khBaik && khSedang >= khBuruk) {
            medium
        } else {
            bad
        }

        val _gh = if (ghBaik >= ghBuruk) {
            good
        } else {
            bad
        }

        return StatusParameter(_temperature, _ph, _ammonia, _kh, _gh)
    }

    //========================================================================

    // Fungsi-fungsi keanggotaan
    private fun fungsiKeanggotaanSuhuBaik(suhu: Double): Double {
        return when {
            suhu in 25.0..28.0 -> 1.0
            suhu > 23 && suhu < 25 -> (suhu - 23) / (25 - 23)
            suhu > 28 && suhu < 31 -> (31 - suhu) / (31 - 28)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanSuhuBuruk(suhu: Double): Double {
        return when {
            suhu <= 23 || suhu >= 31 -> 1.0
            suhu > 23 && suhu < 25 -> (25 - suhu) / (25 - 23)
            suhu > 28 && suhu < 31 -> (suhu - 28) / (31 - 28)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhBaik(ph: Double): Double {
        return when {
            ph in 6.3..6.6 -> 1.0
            ph > 6 && ph < 6.3 -> (ph - 6) / (6.3 - 6)
            ph > 6.6 && ph < 6.8 -> (6.8 - ph) / (6.8 - 6.6)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhSedang(ph: Double): Double {
        return when {
            ph > 6.6 && ph <= 6.8 -> (ph - 6.6) / (6.8 - 6.6)
            ph >= 6.8 && ph < 7 -> (7 - ph) / (7 - 6.8)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhBuruk(ph: Double): Double {
        return when {
            ph <= 6 || ph >= 7 -> 1.0
            ph > 6 && ph < 6.3 -> (6.3 - ph) / (6.3 - 6)
            ph > 6.8 && ph < 7 -> (ph - 6.8) / (7 - 6.8)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanAmmoniaBaik(ammonia: Double): Double {
        return when {
            ammonia <= 0.1 -> 1.0
            ammonia > 0.1 && ammonia < 0.5 -> (0.5 - ammonia) / (0.5 - 0.1)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanAmmoniaBuruk(ammonia: Double): Double {
        return when {
            ammonia >= 0.5 -> 1.0
            ammonia > 0.1 && ammonia < 0.5 -> (ammonia - 0.1) / (0.5 - 0.1)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanKhBaik(kh: Double): Double {
        return when {
            kh <= 1 -> 1.0
            kh > 1 && kh < 2 -> (2 - kh) / (2 - 1)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanKhSedang(kh: Double): Double {
        return when {
            kh in 2.0..4.0 -> 1.0
            kh > 1 && kh < 2 -> (kh - 1) / (2 - 1)
            kh > 4 && kh < 6 -> (6 - kh) / (6 - 4)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanKhBuruk(kh: Double): Double {
        return when {
            kh >= 6 -> 1.0
            kh > 4 && kh < 6 -> (kh - 4) / (6 - 4)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanGhBaik(gh: Double): Double {
        return when {
            gh <= 8 -> 1.0
            gh > 8 && gh < 12 -> (12 - gh) / (12 - 8)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanGhBuruk(gh: Double): Double {
        return when {
            gh >= 12 -> 1.0
            gh > 8 && gh < 12 -> (gh - 8) / (12 - 8)
            else -> 0.0
        }
    }

    // ==============--------------------=================

    private fun fungsiKeanggotaanStatusBaik(inverensi: Double): Double {
        return when (inverensi) {
            1.0 -> 70.0
            in 0.1..0.9 -> (inverensi * 20) + 50
            else -> 50.0
        }
    }

    private fun fungsiKeanggotaanStatusSedang(inverensi: Double): Double {
        return when (inverensi) {
            1.0 -> 50.0
            in 0.1..0.9 -> (((inverensi * 20) + 30) + (70 - (inverensi * 20))) / 2
            else -> 70.0
        }
    }

    private fun fungsiKeanggotaanStatusBuruk(inverensi: Double): Double {
        return when (inverensi) {
            1.0 -> 30.0
            in 0.1..0.9 -> 50 - (inverensi * 20)
            else -> 50.0
        }
    }

}


