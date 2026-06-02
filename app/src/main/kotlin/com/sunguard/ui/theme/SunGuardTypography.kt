package com.sunguard.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp

private val provider = androidx.compose.ui.text.googlefonts.GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = com.sunguard.R.array.com_google_android_gms_fonts_certs,
)

private fun googleFontFamily(name: String): FontFamily = FontFamily(
    Font(googleFont = GoogleFont(name), fontProvider = provider),
)

val PlusJakartaSans = googleFontFamily("Plus Jakarta Sans")
val WorkSans = googleFontFamily("Work Sans")
val JetBrainsMono = googleFontFamily("JetBrains Mono")
val DancingScript = googleFontFamily("Dancing Script")

val SunGuardTypography = Typography(
    headlineLarge = TextStyle(
        fontFamily = PlusJakartaSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        color = SunGuardColors.SunOrange,
    ),
    bodyLarge = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = SunGuardColors.SunOrange,
    ),
    labelSmall = TextStyle(
        fontFamily = WorkSans,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        color = SunGuardColors.SunOrange,
    ),
)

val ApplyButtonTextStyle = TextStyle(
    fontFamily = WorkSans,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    color = SunGuardColors.SunOrange,
)

val StatsValueTextStyle = TextStyle(
    fontFamily = WorkSans,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    lineHeight = 18.sp,
    color = SunGuardColors.SunOrange,
)

val StatsLabelTextStyle = TextStyle(
    fontFamily = WorkSans,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    color = SunGuardColors.SunOrange,
)

val LogEntryTextStyle = TextStyle(
    fontFamily = JetBrainsMono,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 28.sp,
    color = SunGuardColors.SunOrange,
)

val SectionHeaderScriptTextStyle = TextStyle(
    fontFamily = DancingScript,
    fontWeight = FontWeight.Normal,
    fontSize = 18.sp,
    lineHeight = 24.sp,
    color = SunGuardColors.SunOrange,
)

val FooterTaglineTextStyle = TextStyle(
    fontFamily = WorkSans,
    fontWeight = FontWeight.Normal,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    color = SunGuardColors.SunOrange,
)
