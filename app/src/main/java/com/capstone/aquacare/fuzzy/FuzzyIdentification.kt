package com.capstone.aquacare.fuzzy

import android.content.Context
import android.util.Log
import com.capstone.aquacare.R
import com.capstone.aquacare.data.StatusParameter

class FuzzyIdentification(private val context: Context, private val style: String) {

    private val good = context.getString(R.string.good)
    private val medium = context.getString(R.string.medium)
    private val bad = context.getString(R.string.bad)

    fun calculateWaterQuality(temperature: Double, ph: Double, ammonia: Double, kh: Double, gh: Double): String {

        val dinginTemperature = fungsiKeanggotaanSuhuDingin(temperature)
        val normalTemperature = fungsiKeanggotaanSuhuNormal(temperature)
        val panasTemperature = fungsiKeanggotaanSuhuPanas(temperature)

        val asamPh = fungsiKeanggotaanPhAsam(ph)
        val sedikitAsamPh = fungsiKeanggotaanPhSedikitAsam(ph)
        val netralPh = fungsiKeanggotaanPhNetral(ph)
        val basaPh = fungsiKeanggotaanPhBasa(ph)

        val rendahAmmonia = fungsiKeanggotaanAmmoniaRendah(ammonia)
        val tinggiAmmonia = fungsiKeanggotaanAmmoniaTinggi(ammonia)

        val rendahKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhRendah(kh)
        } else {
            fungsiKeanggotaanDutchKhRendah(kh)
        }
        val sedangKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhSedang(kh)
        } else {
            fungsiKeanggotaanDutchKhSedang(kh)
        }
        val tinggiKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhTinggi(kh)
        } else {
            fungsiKeanggotaanDutchKhTinggi(kh)
        }

        val rendahGh = fungsiKeanggotaanGhRendah(gh)
        val tinggiGh = fungsiKeanggotaanGhTinggi(gh)

        val inferensi1 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi2 = minOf(normalTemperature, netralPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi3 = minOf(normalTemperature, asamPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi4 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi5 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi6 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi7 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, rendahKh, tinggiGh)

        val inferensi8 = minOf(normalTemperature, netralPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi9 = minOf(normalTemperature, netralPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi10 = minOf(normalTemperature, netralPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi11 = minOf(normalTemperature, netralPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi12 = minOf(normalTemperature, netralPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi13 = minOf(normalTemperature, netralPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi14 = minOf(normalTemperature, netralPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi15 = minOf(normalTemperature, netralPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi16 = minOf(normalTemperature, netralPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi17 = minOf(normalTemperature, netralPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi18 = minOf(normalTemperature, netralPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi19 = minOf(normalTemperature, asamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi20 = minOf(normalTemperature, asamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi21 = minOf(normalTemperature, asamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi22 = minOf(normalTemperature, asamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi23 = minOf(normalTemperature, asamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi24 = minOf(normalTemperature, asamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi25 = minOf(normalTemperature, asamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi26 = minOf(normalTemperature, asamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi27 = minOf(normalTemperature, asamPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi28 = minOf(normalTemperature, asamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi29 = minOf(normalTemperature, asamPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi30 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi31 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi32 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi33 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi34 = minOf(normalTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi35 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi36 = minOf(normalTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi37 = minOf(panasTemperature, netralPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi38 = minOf(panasTemperature, netralPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi39 = minOf(panasTemperature, netralPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi40 = minOf(panasTemperature, netralPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi41 = minOf(panasTemperature, netralPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi42 = minOf(panasTemperature, netralPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi43 = minOf(panasTemperature, netralPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi44 = minOf(panasTemperature, netralPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi45 = minOf(panasTemperature, netralPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi46 = minOf(panasTemperature, netralPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi47 = minOf(panasTemperature, netralPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi48 = minOf(panasTemperature, netralPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi49 = minOf(panasTemperature, asamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi50 = minOf(panasTemperature, asamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi51 = minOf(panasTemperature, asamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi52 = minOf(panasTemperature, asamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi53 = minOf(panasTemperature, asamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi54 = minOf(panasTemperature, asamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi55 = minOf(panasTemperature, asamPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi56 = minOf(panasTemperature, asamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi57 = minOf(panasTemperature, asamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi58 = minOf(panasTemperature, asamPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi59 = minOf(panasTemperature, asamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi60 = minOf(panasTemperature, asamPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi61 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi62 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi63 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi64 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi65 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi66 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi67 = minOf(panasTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi68 = minOf(panasTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi69 = minOf(panasTemperature, sedikitAsamPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi70 = minOf(panasTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi71 = minOf(panasTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, tinggiGh)
        val inferensi72 = minOf(panasTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, tinggiGh)

        val inferensi73 = minOf(dinginTemperature, netralPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi74 = minOf(dinginTemperature, netralPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi75 = minOf(dinginTemperature, netralPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi76 = minOf(dinginTemperature, netralPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi77 = minOf(dinginTemperature, netralPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi78 = minOf(dinginTemperature, netralPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi79 = minOf(dinginTemperature, netralPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi80 = minOf(dinginTemperature, netralPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi81 = minOf(dinginTemperature, netralPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi82 = minOf(dinginTemperature, netralPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi83 = minOf(dinginTemperature, netralPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi84 = minOf(dinginTemperature, netralPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi85 = minOf(dinginTemperature, asamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi86 = minOf(dinginTemperature, asamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi87 = minOf(dinginTemperature, asamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi88 = minOf(dinginTemperature, asamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi89 = minOf(dinginTemperature, asamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi90 = minOf(dinginTemperature, asamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi91 = minOf(dinginTemperature, asamPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi92 = minOf(dinginTemperature, asamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi93 = minOf(dinginTemperature, asamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi94 = minOf(dinginTemperature, asamPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi95 = minOf(dinginTemperature, asamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi96 = minOf(dinginTemperature, asamPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi97 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi98 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi99 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi100 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi101 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi102 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi103 = minOf(dinginTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi104 = minOf(dinginTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi105 = minOf(dinginTemperature, sedikitAsamPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi106 = minOf(dinginTemperature, sedikitAsamPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi107 = minOf(dinginTemperature, sedikitAsamPh, rendahAmmonia, tinggiKh, tinggiGh)
        val inferensi108 = minOf(dinginTemperature, sedikitAsamPh, tinggiAmmonia, rendahKh, tinggiGh)

        val inferensi109 = minOf(normalTemperature, basaPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi110 = minOf(normalTemperature, basaPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi111 = minOf(normalTemperature, basaPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi112 = minOf(normalTemperature, basaPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi113 = minOf(normalTemperature, basaPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi114 = minOf(normalTemperature, basaPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi115 = minOf(normalTemperature, basaPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi116 = minOf(normalTemperature, basaPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi117 = minOf(normalTemperature, basaPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi118 = minOf(normalTemperature, basaPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi119 = minOf(normalTemperature, basaPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi120 = minOf(panasTemperature, basaPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi121 = minOf(panasTemperature, basaPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi122 = minOf(panasTemperature, basaPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi123 = minOf(panasTemperature, basaPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi124 = minOf(panasTemperature, basaPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi125 = minOf(panasTemperature, basaPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi126 = minOf(panasTemperature, basaPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi127 = minOf(panasTemperature, basaPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi128 = minOf(panasTemperature, basaPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi129 = minOf(panasTemperature, basaPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi130 = minOf(panasTemperature, basaPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi131 = minOf(panasTemperature, basaPh, rendahAmmonia, tinggiKh, tinggiGh)

        val inferensi132 = minOf(dinginTemperature, basaPh, tinggiAmmonia, rendahKh, rendahGh)
        val inferensi133 = minOf(dinginTemperature, basaPh, tinggiAmmonia, sedangKh, rendahGh)
        val inferensi134 = minOf(dinginTemperature, basaPh, tinggiAmmonia, tinggiKh, rendahGh)
        val inferensi135 = minOf(dinginTemperature, basaPh, tinggiAmmonia, rendahKh, tinggiGh)
        val inferensi136 = minOf(dinginTemperature, basaPh, tinggiAmmonia, sedangKh, tinggiGh)
        val inferensi137 = minOf(dinginTemperature, basaPh, tinggiAmmonia, tinggiKh, tinggiGh)
        val inferensi138 = minOf(dinginTemperature, basaPh, rendahAmmonia, rendahKh, rendahGh)
        val inferensi139 = minOf(dinginTemperature, basaPh, rendahAmmonia, sedangKh, rendahGh)
        val inferensi140 = minOf(dinginTemperature, basaPh, rendahAmmonia, tinggiKh, rendahGh)
        val inferensi141 = minOf(dinginTemperature, basaPh, rendahAmmonia, rendahKh, tinggiGh)
        val inferensi142 = minOf(dinginTemperature, basaPh, rendahAmmonia, sedangKh, tinggiGh)
        val inferensi143 = minOf(dinginTemperature, basaPh, rendahAmmonia, tinggiKh, tinggiGh)


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
        val hasilInferensi72 = fungsiKeanggotaanStatusBuruk(inferensi72)

        val hasilInferensi73 = fungsiKeanggotaanStatusBuruk(inferensi73)
        val hasilInferensi74 = fungsiKeanggotaanStatusBuruk(inferensi74)
        val hasilInferensi75 = fungsiKeanggotaanStatusBuruk(inferensi75)
        val hasilInferensi76 = fungsiKeanggotaanStatusBuruk(inferensi76)
        val hasilInferensi77 = fungsiKeanggotaanStatusBuruk(inferensi77)
        val hasilInferensi78 = fungsiKeanggotaanStatusBuruk(inferensi78)
        val hasilInferensi79 = fungsiKeanggotaanStatusSedang(inferensi79)
        val hasilInferensi80 = fungsiKeanggotaanStatusSedang(inferensi80)
        val hasilInferensi81 = fungsiKeanggotaanStatusBuruk(inferensi81)
        val hasilInferensi82 = fungsiKeanggotaanStatusSedang(inferensi82)
        val hasilInferensi83 = fungsiKeanggotaanStatusBuruk(inferensi83)
        val hasilInferensi84 = fungsiKeanggotaanStatusBuruk(inferensi84)

        val hasilInferensi85 = fungsiKeanggotaanStatusBuruk(inferensi85)
        val hasilInferensi86 = fungsiKeanggotaanStatusBuruk(inferensi86)
        val hasilInferensi87 = fungsiKeanggotaanStatusBuruk(inferensi87)
        val hasilInferensi88 = fungsiKeanggotaanStatusBuruk(inferensi88)
        val hasilInferensi89 = fungsiKeanggotaanStatusBuruk(inferensi89)
        val hasilInferensi90 = fungsiKeanggotaanStatusBuruk(inferensi90)
        val hasilInferensi91 = fungsiKeanggotaanStatusSedang(inferensi91)
        val hasilInferensi92 = fungsiKeanggotaanStatusBuruk(inferensi92)
        val hasilInferensi93 = fungsiKeanggotaanStatusBuruk(inferensi93)
        val hasilInferensi94 = fungsiKeanggotaanStatusBuruk(inferensi94)
        val hasilInferensi95 = fungsiKeanggotaanStatusBuruk(inferensi95)
        val hasilInferensi96 = fungsiKeanggotaanStatusBuruk(inferensi96)

        val hasilInferensi97 = fungsiKeanggotaanStatusBuruk(inferensi97)
        val hasilInferensi98 = fungsiKeanggotaanStatusBuruk(inferensi98)
        val hasilInferensi99 = fungsiKeanggotaanStatusBuruk(inferensi99)
        val hasilInferensi100 = fungsiKeanggotaanStatusBuruk(inferensi100)
        val hasilInferensi101 = fungsiKeanggotaanStatusBuruk(inferensi101)
        val hasilInferensi102 = fungsiKeanggotaanStatusBuruk(inferensi102)
        val hasilInferensi103 = fungsiKeanggotaanStatusSedang(inferensi103)
        val hasilInferensi104 = fungsiKeanggotaanStatusBuruk(inferensi104)
        val hasilInferensi105 = fungsiKeanggotaanStatusSedang(inferensi105)
        val hasilInferensi106 = fungsiKeanggotaanStatusSedang(inferensi106)
        val hasilInferensi107 = fungsiKeanggotaanStatusBuruk(inferensi107)
        val hasilInferensi108 = fungsiKeanggotaanStatusBuruk(inferensi108)

        val hasilInferensi109 = fungsiKeanggotaanStatusBuruk(inferensi19)
        val hasilInferensi110 = fungsiKeanggotaanStatusBuruk(inferensi20)
        val hasilInferensi111 = fungsiKeanggotaanStatusBuruk(inferensi21)
        val hasilInferensi112 = fungsiKeanggotaanStatusBuruk(inferensi22)
        val hasilInferensi113 = fungsiKeanggotaanStatusBuruk(inferensi23)
        val hasilInferensi114 = fungsiKeanggotaanStatusBuruk(inferensi24)
        val hasilInferensi115 = fungsiKeanggotaanStatusSedang(inferensi25)
        val hasilInferensi116 = fungsiKeanggotaanStatusBuruk(inferensi26)
        val hasilInferensi117 = fungsiKeanggotaanStatusSedang(inferensi27)
        val hasilInferensi118 = fungsiKeanggotaanStatusSedang(inferensi28)
        val hasilInferensi119 = fungsiKeanggotaanStatusBuruk(inferensi29)

        val hasilInferensi120 = fungsiKeanggotaanStatusBuruk(inferensi49)
        val hasilInferensi121 = fungsiKeanggotaanStatusBuruk(inferensi50)
        val hasilInferensi122 = fungsiKeanggotaanStatusBuruk(inferensi51)
        val hasilInferensi123 = fungsiKeanggotaanStatusBuruk(inferensi52)
        val hasilInferensi124 = fungsiKeanggotaanStatusBuruk(inferensi53)
        val hasilInferensi125 = fungsiKeanggotaanStatusBuruk(inferensi54)
        val hasilInferensi126 = fungsiKeanggotaanStatusSedang(inferensi55)
        val hasilInferensi127 = fungsiKeanggotaanStatusBuruk(inferensi56)
        val hasilInferensi128 = fungsiKeanggotaanStatusBuruk(inferensi57)
        val hasilInferensi129 = fungsiKeanggotaanStatusBuruk(inferensi58)
        val hasilInferensi130 = fungsiKeanggotaanStatusBuruk(inferensi59)
        val hasilInferensi131 = fungsiKeanggotaanStatusBuruk(inferensi60)

        val hasilInferensi132 = fungsiKeanggotaanStatusBuruk(inferensi85)
        val hasilInferensi133 = fungsiKeanggotaanStatusBuruk(inferensi86)
        val hasilInferensi134 = fungsiKeanggotaanStatusBuruk(inferensi87)
        val hasilInferensi135 = fungsiKeanggotaanStatusBuruk(inferensi88)
        val hasilInferensi136 = fungsiKeanggotaanStatusBuruk(inferensi89)
        val hasilInferensi137 = fungsiKeanggotaanStatusBuruk(inferensi90)
        val hasilInferensi138 = fungsiKeanggotaanStatusSedang(inferensi91)
        val hasilInferensi139 = fungsiKeanggotaanStatusBuruk(inferensi92)
        val hasilInferensi140 = fungsiKeanggotaanStatusBuruk(inferensi93)
        val hasilInferensi141 = fungsiKeanggotaanStatusBuruk(inferensi94)
        val hasilInferensi142 = fungsiKeanggotaanStatusBuruk(inferensi95)
        val hasilInferensi143 = fungsiKeanggotaanStatusBuruk(inferensi96)

//        val defuzzifikasi1 = ((inferensi1 * hasilInferensi1) + (inferensi2 * hasilInferensi2) + (inferensi3 * hasilInferensi3) + (inferensi4 * hasilInferensi4) + (inferensi5 * hasilInferensi5) + (inferensi6 * hasilInferensi6)  + (inferensi7 * hasilInferensi7)  + (inferensi8 * hasilInferensi8)  + (inferensi9 * hasilInferensi9)  + (inferensi10 * hasilInferensi10) + (inferensi11 * hasilInferensi11)  + (inferensi12 * hasilInferensi12) + (inferensi13 * hasilInferensi13) + (inferensi14 * hasilInferensi14) + (inferensi15 * hasilInferensi15))
//        val defuzzifikasi2 = ((inferensi16 * hasilInferensi16) + (inferensi17 * hasilInferensi17) + (inferensi18 * hasilInferensi18) + (inferensi19 * hasilInferensi19) + (inferensi20 * hasilInferensi20) + (inferensi21 * hasilInferensi21) + (inferensi22 * hasilInferensi22) + (inferensi23 * hasilInferensi23) + (inferensi24 * hasilInferensi24) + (inferensi25 * hasilInferensi25) + (inferensi26 * hasilInferensi26) + (inferensi27 * hasilInferensi27) + (inferensi28 * hasilInferensi28) + (inferensi29 * hasilInferensi29) + (inferensi30 * hasilInferensi30))
//        val defuzzifikasi3 = ((inferensi31 * hasilInferensi31) + (inferensi32 * hasilInferensi32) + (inferensi33 * hasilInferensi33) + (inferensi34 * hasilInferensi34) + (inferensi35 * hasilInferensi35) + (inferensi36 * hasilInferensi36)  + (inferensi37 * hasilInferensi37)  + (inferensi38 * hasilInferensi38)  + (inferensi39 * hasilInferensi39)  + (inferensi40 * hasilInferensi40) + (inferensi41 * hasilInferensi41)  + (inferensi42 * hasilInferensi42) + (inferensi43 * hasilInferensi43) + (inferensi44 * hasilInferensi44) + (inferensi45 * hasilInferensi45))
//        val defuzzifikasi4 = ((inferensi46 * hasilInferensi46) + (inferensi47 * hasilInferensi47) + (inferensi48 * hasilInferensi48) + (inferensi49 * hasilInferensi49) + (inferensi50 * hasilInferensi50) + (inferensi51 * hasilInferensi51) + (inferensi52 * hasilInferensi52) + (inferensi53 * hasilInferensi53) + (inferensi54 * hasilInferensi54) + (inferensi55 * hasilInferensi55) + (inferensi56 * hasilInferensi56) + (inferensi57 * hasilInferensi57) + (inferensi58 * hasilInferensi58) + (inferensi59 * hasilInferensi59) + (inferensi60 * hasilInferensi60))
//        val defuzzifikasi5 = ((inferensi61 * hasilInferensi61) + (inferensi62 * hasilInferensi62) + (inferensi63 * hasilInferensi63) + (inferensi64 * hasilInferensi64) + (inferensi65 * hasilInferensi65) + (inferensi66 * hasilInferensi66)  + (inferensi67 * hasilInferensi67)  + (inferensi68 * hasilInferensi68)  + (inferensi69 * hasilInferensi69)  + (inferensi70 * hasilInferensi70) + (inferensi71 * hasilInferensi71) + (inferensi72 * hasilInferensi72))

//        val defuzzifikasiA = inferensi1 + inferensi2 + inferensi3 + inferensi4 + inferensi5 + inferensi6 + inferensi7 + inferensi8 + inferensi9 + inferensi10 + inferensi11 + inferensi12 + inferensi13 + inferensi14 + inferensi15 + inferensi16 + inferensi17 + inferensi18 + inferensi19 + inferensi20
//        val defuzzifikasiB = inferensi21 + inferensi22 + inferensi23 + inferensi24 + inferensi25 + inferensi26 + inferensi27 + inferensi28 + inferensi29 + inferensi30 + inferensi31 + inferensi32 + inferensi33 + inferensi34 + inferensi35 + inferensi36 + inferensi37 + inferensi38 + inferensi39 + inferensi40
//        val defuzzifikasiC = inferensi41 + inferensi42 + inferensi43 + inferensi44 + inferensi45 + inferensi46 + inferensi47 + inferensi48 + inferensi49 + inferensi50 + inferensi51 + inferensi52 + inferensi53 + inferensi54 + inferensi55 + inferensi56 + inferensi57 + inferensi58 + inferensi59 + inferensi60
//        val defuzzifikasiD = inferensi61 + inferensi62 + inferensi63 + inferensi64 + inferensi65 + inferensi66 + inferensi67 + inferensi68 + inferensi69 + inferensi70 + inferensi71 + inferensi72

        val totalA = listOf(
            inferensi1 * hasilInferensi1, inferensi2 * hasilInferensi2, inferensi3 * hasilInferensi3, inferensi4 * hasilInferensi4, inferensi5 * hasilInferensi5, inferensi6 * hasilInferensi6, inferensi7 * hasilInferensi7, inferensi8 * hasilInferensi8,
            inferensi9 * hasilInferensi9, inferensi10 * hasilInferensi10, inferensi11 * hasilInferensi11, inferensi12 * hasilInferensi12, inferensi13 * hasilInferensi13, inferensi14 * hasilInferensi14, inferensi15 * hasilInferensi15, inferensi16 * hasilInferensi16,
            inferensi17 * hasilInferensi17, inferensi18 * hasilInferensi18, inferensi19 * hasilInferensi19, inferensi20 * hasilInferensi20, inferensi21 * hasilInferensi21, inferensi22 * hasilInferensi22, inferensi23 * hasilInferensi23, inferensi24 * hasilInferensi24,
            inferensi25 * hasilInferensi25, inferensi26 * hasilInferensi26, inferensi27 * hasilInferensi27, inferensi28 * hasilInferensi28, inferensi29 * hasilInferensi29, inferensi30 * hasilInferensi30, inferensi31 * hasilInferensi31, inferensi32 * hasilInferensi32,
            inferensi33 * hasilInferensi33, inferensi34 * hasilInferensi34, inferensi35 * hasilInferensi35, inferensi36 * hasilInferensi36, inferensi37 * hasilInferensi37, inferensi38 * hasilInferensi38, inferensi39 * hasilInferensi39, inferensi40 * hasilInferensi40,
            inferensi41 * hasilInferensi41, inferensi42 * hasilInferensi42, inferensi43 * hasilInferensi43, inferensi44 * hasilInferensi44, inferensi45 * hasilInferensi45, inferensi46 * hasilInferensi46, inferensi47 * hasilInferensi47, inferensi48 * hasilInferensi48,
            inferensi49 * hasilInferensi49, inferensi50 * hasilInferensi50, inferensi51 * hasilInferensi51, inferensi52 * hasilInferensi52, inferensi53 * hasilInferensi53, inferensi54 * hasilInferensi54, inferensi55 * hasilInferensi55, inferensi56 * hasilInferensi56,
            inferensi57 * hasilInferensi57, inferensi58 * hasilInferensi58, inferensi59 * hasilInferensi59, inferensi60 * hasilInferensi60, inferensi61 * hasilInferensi61, inferensi62 * hasilInferensi62, inferensi63 * hasilInferensi63, inferensi64 * hasilInferensi64,
            inferensi65 * hasilInferensi65, inferensi66 * hasilInferensi66, inferensi67 * hasilInferensi67, inferensi68 * hasilInferensi68, inferensi69 * hasilInferensi69, inferensi70 * hasilInferensi70, inferensi71 * hasilInferensi71, inferensi72 * hasilInferensi72,
            inferensi73 * hasilInferensi73, inferensi74 * hasilInferensi74, inferensi75 * hasilInferensi75, inferensi76 * hasilInferensi76, inferensi77 * hasilInferensi77, inferensi78 * hasilInferensi78, inferensi79 * hasilInferensi79, inferensi80 * hasilInferensi80,
            inferensi81 * hasilInferensi81, inferensi82 * hasilInferensi82, inferensi83 * hasilInferensi83, inferensi84 * hasilInferensi84, inferensi85 * hasilInferensi85, inferensi86 * hasilInferensi86, inferensi87 * hasilInferensi87, inferensi88 * hasilInferensi88,
            inferensi89 * hasilInferensi89, inferensi90 * hasilInferensi90, inferensi91 * hasilInferensi91, inferensi92 * hasilInferensi92, inferensi93 * hasilInferensi93, inferensi94 * hasilInferensi94, inferensi95 * hasilInferensi95, inferensi96 * hasilInferensi96,
            inferensi97 * hasilInferensi97, inferensi98 * hasilInferensi98, inferensi99 * hasilInferensi99, inferensi100 * hasilInferensi100, inferensi101 * hasilInferensi101, inferensi102 * hasilInferensi102, inferensi103 * hasilInferensi103, inferensi104 * hasilInferensi104,
            inferensi105 * hasilInferensi105, inferensi106 * hasilInferensi106, inferensi107 * hasilInferensi107, inferensi108 * hasilInferensi108, inferensi109 * hasilInferensi109, inferensi110 * hasilInferensi110, inferensi111 * hasilInferensi111, inferensi112 * hasilInferensi112,
            inferensi113 * hasilInferensi113, inferensi114 * hasilInferensi114, inferensi115 * hasilInferensi115, inferensi116 * hasilInferensi116, inferensi117 * hasilInferensi117, inferensi118 * hasilInferensi118, inferensi119 * hasilInferensi119, inferensi120 * hasilInferensi120,
            inferensi121 * hasilInferensi121, inferensi122 * hasilInferensi122, inferensi123 * hasilInferensi123, inferensi124 * hasilInferensi124, inferensi125 * hasilInferensi125, inferensi126 * hasilInferensi126, inferensi127 * hasilInferensi127, inferensi128 * hasilInferensi128,
            inferensi129 * hasilInferensi129, inferensi130 * hasilInferensi130, inferensi131 * hasilInferensi131, inferensi132 * hasilInferensi132, inferensi133 * hasilInferensi133, inferensi134 * hasilInferensi134, inferensi135 * hasilInferensi135, inferensi136 * hasilInferensi136,
            inferensi137 * hasilInferensi137, inferensi138 * hasilInferensi138, inferensi139 * hasilInferensi139, inferensi140 * hasilInferensi140, inferensi141 * hasilInferensi141, inferensi142 * hasilInferensi142, inferensi143 * hasilInferensi143
        ).sumOf { it }

        val totalB = listOf(
            inferensi1, inferensi2, inferensi3, inferensi4, inferensi5, inferensi6, inferensi7, inferensi8, inferensi9, inferensi10, inferensi11, inferensi12, inferensi13, inferensi14, inferensi15, inferensi16, inferensi17, inferensi18, inferensi19, inferensi20,
            inferensi21, inferensi22, inferensi23, inferensi24, inferensi25, inferensi26, inferensi27, inferensi28, inferensi29, inferensi30, inferensi31, inferensi32, inferensi33, inferensi34, inferensi35, inferensi36, inferensi37, inferensi38, inferensi39, inferensi40,
            inferensi41, inferensi42, inferensi43, inferensi44, inferensi45, inferensi46, inferensi47, inferensi48, inferensi49, inferensi50, inferensi51, inferensi52, inferensi53, inferensi54, inferensi55, inferensi56, inferensi57, inferensi58, inferensi59, inferensi60,
            inferensi61, inferensi62, inferensi63, inferensi64, inferensi65, inferensi66, inferensi67, inferensi68, inferensi69, inferensi70, inferensi71, inferensi72, inferensi73, inferensi74, inferensi75, inferensi76, inferensi77, inferensi78, inferensi79, inferensi80,
            inferensi81, inferensi82, inferensi83, inferensi84, inferensi85, inferensi86, inferensi87, inferensi88, inferensi89, inferensi90, inferensi91, inferensi92, inferensi93, inferensi94, inferensi95, inferensi96, inferensi97, inferensi98, inferensi99, inferensi100,
            inferensi101, inferensi102, inferensi103, inferensi104, inferensi105, inferensi106, inferensi107, inferensi108, inferensi109, inferensi110, inferensi111, inferensi112, inferensi113, inferensi114, inferensi115, inferensi116, inferensi117, inferensi118,
            inferensi119, inferensi120, inferensi121, inferensi122, inferensi123, inferensi124, inferensi125, inferensi126, inferensi127, inferensi128, inferensi129, inferensi130, inferensi131, inferensi132, inferensi133, inferensi134, inferensi135, inferensi136,
            inferensi137, inferensi138, inferensi139, inferensi140, inferensi141, inferensi142, inferensi143
        ).sumOf { it }

//        val totalDefuzzifikasi1 = defuzzifikasi1 + defuzzifikasi2 + defuzzifikasi3 + defuzzifikasi4 + defuzzifikasi5
//        val totalDefuzzifikasi2 = defuzzifikasiA + defuzzifikasiB + defuzzifikasiC + defuzzifikasiD

//        val defuzzifikasi = totalDefuzzifikasi1 / totalDefuzzifikasi2
        val defuzzifikasi = totalA / totalB

        val resultDefuzzifikasi = if (defuzzifikasi >= 70) {
            good
        } else if (defuzzifikasi <= 30){
            bad
        } else {
            medium
        }

//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 3: α = $inferensi3, Z3 = $hasilInferensi3")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 13: α = $inferensi13, Z13 = $hasilInferensi13")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 25: α = $inferensi25, Z25 = $hasilInferensi25")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 32: α = $inferensi32, Z32 = $hasilInferensi32")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 44: α = $inferensi44, Z44 = $hasilInferensi44")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 58: α = $inferensi58, Z58 = $hasilInferensi58")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 63: α = $inferensi63, Z63 = $hasilInferensi63")
//        Log.d("Fuzzy Natural", "Hasil Inferensi Aturan 70: α = $inferensi70, Z70 = $hasilInferensi70")
//
        Log.d("Fuzzy Natural", "Defuzzyfikasi: Jumlah α * Z = $totalA, Jumlah α = $totalB, Grade = $resultDefuzzifikasi")

        return resultDefuzzifikasi
    }

    fun checkParameter(suhu: Double, ph: Double, ammonia: Double, kh: Double, gh: Double): StatusParameter {
        val dinginTemperature = fungsiKeanggotaanSuhuDingin(suhu)
        val normalTemperature = fungsiKeanggotaanSuhuNormal(suhu)
        val panasTemperature = fungsiKeanggotaanSuhuPanas(suhu)

        val asamPh = fungsiKeanggotaanPhAsam(ph)
        val sedikitAsamPh = fungsiKeanggotaanPhSedikitAsam(ph)
        val netralPh = fungsiKeanggotaanPhNetral(ph)
        val basaPh = fungsiKeanggotaanPhBasa(ph)

        val rendahAmmonia = fungsiKeanggotaanAmmoniaRendah(ammonia)
        val tinggiAmmonia = fungsiKeanggotaanAmmoniaTinggi(ammonia)

        val rendahKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhRendah(kh)
        } else {
            fungsiKeanggotaanDutchKhRendah(kh)
        }
        val sedangKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhSedang(kh)
        } else {
            fungsiKeanggotaanDutchKhSedang(kh)
        }
        val tinggiKh = if (style == "Natural Style") {
            fungsiKeanggotaanNaturalKhTinggi(kh)
        } else {
            fungsiKeanggotaanDutchKhTinggi(kh)
        }

        val rendahGh = fungsiKeanggotaanGhRendah(gh)
        val tinggiGh = fungsiKeanggotaanGhTinggi(gh)

        val _temperature = if (normalTemperature >= dinginTemperature && normalTemperature >= panasTemperature) {
            good
        } else {
            bad
        }

        val _ph = if (sedikitAsamPh >= netralPh && sedikitAsamPh >= asamPh && sedikitAsamPh >= basaPh) {
            good
        } else if (netralPh >= sedikitAsamPh && netralPh >= asamPh && netralPh >= basaPh) {
            medium
        } else {
            bad
        }

        val _ammonia = if (rendahAmmonia >= tinggiAmmonia) {
            good
        } else {
            bad
        }

        val _kh = if (rendahKh >= sedangKh && rendahKh >= tinggiKh) {
            good
        } else if (sedangKh >= rendahKh && sedangKh >= tinggiKh) {
            medium
        } else {
            bad
        }

        val _gh = if (rendahGh >= tinggiGh) {
            good
        } else {
            bad
        }

        return StatusParameter(_temperature, _ph, _ammonia, _kh, _gh)
    }

    //========================================================================

    // Fungsi Keanggotaan
    private fun fungsiKeanggotaanSuhuDingin(suhu: Double): Double {
        return when {
            suhu <= 22 -> 1.0
            suhu > 22 && suhu < 25 -> (25 - suhu) / (25 - 22)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanSuhuNormal(suhu: Double): Double {
        return when {
            suhu in 25.0..28.0 -> 1.0
            suhu > 22 && suhu < 25 -> (suhu - 22) / (25 - 22)
            suhu > 28 && suhu < 31 -> (31 - suhu) / (31 - 28)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanSuhuPanas(suhu: Double): Double {
        return when {
            suhu >= 31 -> 1.0
            suhu > 28 && suhu < 31 -> (suhu - 28) / (31 - 28)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhAsam(ph: Double): Double {
        return when {
            ph <= 6 -> 1.0
            ph > 6 && ph < 6.3 -> (6.3 - ph) / (6.3 - 6)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhSedikitAsam(ph: Double): Double {
        return when {
            ph in 6.3..6.6 -> 1.0
            ph > 6 && ph < 6.3 -> (ph - 6) / (6.3 - 6)
            ph > 6.6 && ph < 7 -> (7 - ph) / (7 - 6.6)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhNetral(ph: Double): Double {
        return when {
            ph > 6.6 && ph <= 7 -> (ph - 6.6) / (7 - 6.6)
            ph >= 7 && ph < 7.4 -> (7.4 - ph) / (7.4 - 7)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanPhBasa(ph: Double): Double {
        return when {
            ph >= 7.4 -> 1.0
            ph > 7 && ph < 7.4 -> (ph - 7) / (7.4 - 7)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanAmmoniaRendah(ammonia: Double): Double {
        return when {
            ammonia <= 0.1 -> 1.0
            ammonia > 0.1 && ammonia < 0.5 -> (0.5 - ammonia) / (0.5 - 0.1)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanAmmoniaTinggi(ammonia: Double): Double {
        return when {
            ammonia >= 0.5 -> 1.0
            ammonia > 0.1 && ammonia < 0.5 -> (ammonia - 0.1) / (0.5 - 0.1)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanNaturalKhRendah(kh: Double): Double {
        return when {
            kh <= 5 -> 1.0
            kh > 5 && kh < 8 -> (8 - kh) / (8 - 5)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanNaturalKhSedang(kh: Double): Double {
        return when {
            kh in 8.0..10.0 -> 1.0
            kh > 5 && kh < 8 -> (kh - 5) / (8 - 5)
            kh > 10 && kh < 13 -> (13 - kh) / (13 - 10)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanNaturalKhTinggi(kh: Double): Double {
        return when {
            kh >= 13 -> 1.0
            kh > 10 && kh < 13 -> (kh - 10) / (13 - 10)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanDutchKhRendah(kh: Double): Double {
        return when {
            kh <= 2 -> 1.0
            kh > 2 && kh < 5 -> (5 - kh) / (5 - 2)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanDutchKhSedang(kh: Double): Double {
        return when {
            kh in 5.0..6.0 -> 1.0
            kh > 2 && kh < 5 -> (kh - 2) / (5 - 2)
            kh > 6 && kh < 9 -> (9 - kh) / (9 - 6)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanDutchKhTinggi(kh: Double): Double {
        return when {
            kh >= 9 -> 1.0
            kh > 6 && kh < 9 -> (kh - 6) / (9 - 6)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanGhRendah(gh: Double): Double {
        return when {
            gh <= 8 -> 1.0
            gh > 8 && gh < 12 -> (12 - gh) / (12 - 8)
            else -> 0.0
        }
    }

    private fun fungsiKeanggotaanGhTinggi(gh: Double): Double {
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
            in 0.1..1.0 -> (inverensi * 20) + 30
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


